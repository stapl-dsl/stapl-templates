package stapl.templates.rbac

import stapl.core.SubjectAttributeContainer
import stapl.core.Expression


/**
 * Class used for representing a subject with additional RBAC methods.
 */
class RBACSubject(subject: SubjectAttributeContainer) {
  
  /**
   * Return whether this subject has the given permissions.
   * A subject has a permission if one of its direct or indirect
   * roles have it.
   */
  def hasPermission(permission: Permission): Expression = PermissionIn(permission, subject.roles)
  
  /**
   * Returns whether this subject has the given role
   * A subject has a role if it is in the values of its subject.roles attribute
   * or in one of the ancestors of these values.
   */
  def hasRole(role: Role): Expression = new RoleIn(role, subject.roles)
}