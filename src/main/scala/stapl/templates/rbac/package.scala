package stapl.templates

/**
 * The implicit conversions for this template.
 */
import stapl.core.SubjectAttributeContainer
import stapl.core.ConcreteValue

package object rbac {
  
  implicit def role2Value(role: Role): ConcreteValue = new RoleImpl(role)
  
  implicit def permission2Value(permission: Permission): ConcreteValue = new PermissionImpl(permission)
  
  implicit def roleSeq2Value(seq: Seq[Role]): ConcreteValue = new RoleSeqImpl(seq)
  
  implicit def permissionSeq2Value(seq: Seq[Permission]): ConcreteValue = new PermissionSeqImpl(seq)
  
  /**
   * Add methods to subject for searching for permissions and roles.
   */
  implicit def subject2RbacSubject(subject: SubjectAttributeContainer): RBACSubject = 
    new RBACSubject(subject)
}