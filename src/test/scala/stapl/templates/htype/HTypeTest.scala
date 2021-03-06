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
package stapl.templates.htype

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import stapl.core.pdp.PDP
import stapl.core.pdp.AttributeFinder
import stapl.core.pdp.RequestCtx
import org.junit.Assert._
import stapl.core._
import org.scalatest.junit.AssertionsForJUnit

object HTypeTest {

  @BeforeClass def setup() {
    // nothing to do
  }
}
/**
 *
 */
class HTypeTest extends AssertionsForJUnit with BasicPolicy with HTypes {

  import stapl.core.dsl._

  // construct the type hierarchy:
  //
  //              administrativeDetails
  //                   /        \
  //   professionalDetails   personalDetails
  //                              \ 
  //                            contactDetails
  //
  val administrativeDetails = HType("administrative")
  val professionalDetails = HType("professional details", administrativeDetails)
  val personalDetails = HType("personal details", administrativeDetails)
  val contactDetails = HType("contact details", personalDetails)

  val policy = Policy("permit only") := apply PermitOverrides to(
    Rule("htype test") := permit iff (resource.hasType(personalDetails)),
    Rule("default deny") := deny)

  // for the policies below
  subject.role = SimpleAttribute(String)

  // Medical Secretaries can access all administrative details except personal details
  val policy2 = Policy("medical-secratary") := when(subject.role === "medical-secretary") apply DenyOverrides to (
    Rule("") := permit iff (resource.hasType(administrativeDetails)),
    Rule("") := deny iff (resource.hasType(professionalDetails)))

  val pdp = new PDP(policy)

  @Before def setup() {
    // nothing to do
  }

  @Test def testHType1() {
    val Result(decision, obligations, employedAttributes) = pdp.evaluate("", "", "",
      resource.htype -> administrativeDetails)
    assert(decision === Deny)
    assert(obligations === List())
  }

  @Test def testHType2() {
    val Result(decision, obligations, employedAttributes) = pdp.evaluate("", "", "",
      resource.htype -> personalDetails)
    assert(decision === Permit)
    assert(obligations === List())
  }

  @Test def testHType3() {
    val Result(decision, obligations, employedAttributes) = pdp.evaluate("", "", "",
      resource.htype -> contactDetails)
    assert(decision === Permit)
    assert(obligations === List())
  }

  @Test def testHType4() {
    val Result(decision, obligations, employedAttributes) = pdp.evaluate("", "", "",
      resource.htype -> professionalDetails)
    assert(decision === Deny)
    assert(obligations === List())
  }

}