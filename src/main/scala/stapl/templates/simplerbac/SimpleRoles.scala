package stapl.templates.simplerbac

import stapl.core.ListAttribute
import stapl.core.String
import stapl.core.BasicPolicy

/**
 * Defines the subject.roles attribute as a flat list of strings for 
 * easy testing of roles.
 */
trait SimpleRoles extends BasicPolicy {

  subject.roles = ListAttribute(String)
}