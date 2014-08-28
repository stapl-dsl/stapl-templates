package stapl.templates.simplerbac

import stapl.templates.Template
import stapl.core.ListAttribute
import stapl.core.String

/**
 * Defines the subject.roles attribute as a flat list of strings for 
 * easy testing of roles.
 */
trait SimpleRoles extends Template {

  subject.roles = ListAttribute(String)
}