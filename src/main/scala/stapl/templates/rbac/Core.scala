package stapl.templates.rbac


/**
 * Class used for representing a permission that can be assigned to
 * a role.
 */
class Permission(val name: String) {  
  
  override def toString() = name
}
object Permission {
  
  def apply(name: String) = new Permission(name)  
}

/**
 * Class used for representing a role in a hierarchical RBAC scheme.
 * Every role has a number of assigned permissions and an optional
 * parent (tree-structured hierarchy). Each role inherits the permissions
 * of its ancestors.
 * 
 * TODO extend this to graph-structured role hierarchy?
 * 
 * Note that the name of a role will be redundant in most policies since
 * the role will be used as a variable. However, the name of a role is necessary
 * for debugging purposes. Moreover, the roles should be defined only once by
 * a security specialist, so this should not bother too much. 
 */
class Role(val name: String, val parent: Option[Role], val permissions: Permission*) {
  
  /**
   * Returns whether this role directly has the given permission.
   */
  def hasPermissionDirectly(permission: Permission) = permissions.contains(permission)
  
  /**
   * Returns whether this role or any of its ancestors has the
   * given permission.
   */
  def hasPermission(permission: Permission): Boolean = {
    if(hasPermissionDirectly(permission)) {
      true // XXX why does this have to be an explicit "return"
    }
    // look in the ancestors
    else parent match {
      case Some(p) => p.hasPermission(permission)
      case None => false
    }
  }
  
  /**
   * Returns whether the ancestors of this role contain the given role.
   * This role itself is included in the list of ancestors of this role.
   */
  def containsRole(role: Role): Boolean = {
    if(this == role) true // XXX why does this have to be an explicit "return"  
    else parent match {
      case Some(p) => p.containsRole(role)
      case None => false
    }
  }
  
  override def toString() = name
}
object Role {
  
  def apply(name: String, parent: Role, permissions: Permission*) = new Role(name, Some(parent), permissions: _*)
  
  def apply(name: String, permissions: Permission*) = new Role(name, None, permissions: _*)
}