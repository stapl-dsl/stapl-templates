/**
 *    Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 */
package stapl.templates.rbac.tests

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import stapl.core.pdp.PDP
import stapl.core.pdp.AttributeFinder
import stapl.core.pdp.RequestCtx
import org.junit.Assert._
import stapl.core._
import org.scalatest.junit.AssertionsForJUnit
import stapl.templates.rbac._
import stapl.core.templates._

object RBACTest {

  @BeforeClass def setup() {
    // nothing to do
  }
}
/**
 *
 */
class RBACTest extends AssertionsForJUnit with BasicPolicy with RBACTemplate {

  // construct the Role hierarchy
  val read = Permission("read")
  val write = Permission("write")
  val student = Role("student")
  val phd = Role("phd", student, read)
  val prof = Role("prof", phd, write)

  val permissionPolicy: AbstractPolicy = Policy("permit only") := apply PermitOverrides to(
    Rule("rbac test") := permit iff (subject.hasPermission(read)),
    defaultDeny)

  val rolePolicy: AbstractPolicy = Policy("permit only") := apply PermitOverrides to(
    Rule("rbac test") := permit iff (subject.hasRole(phd)),
    defaultDeny)

  @Before def setup() {
    // nothing to do
  }

  @Test def testPermission1() {
    val pdp = new PDP(permissionPolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(student)) === Result(Deny, List()))
  }

  @Test def testPermission2() {
    val pdp = new PDP(permissionPolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(phd)) === Result(Permit, List()))
  }

  @Test def testPermission3() {
    val pdp = new PDP(permissionPolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(prof)) === Result(Permit, List()))
  }

  @Test def testRole1() {
    val pdp = new PDP(rolePolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(student)) === Result(Deny, List()))
  }

  @Test def testRole2() {
    val pdp = new PDP(rolePolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(phd)) === Result(Permit, List()))
  }

  @Test def testRole3() {
    val pdp = new PDP(rolePolicy)
    assert(pdp.evaluate("", "", "",
      subject.roles -> List(prof)) === Result(Permit, List()))
  }

}