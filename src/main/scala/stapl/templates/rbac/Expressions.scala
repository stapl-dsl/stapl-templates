package stapl.templates.rbac

import stapl.core.Value
import stapl.core.Expression
import stapl.core.pdp.EvaluationCtx
import stapl.core.ConcreteValue


/**
 * 
 */
case class RoleIn(role: Value, roles: Value) extends Expression {
  
  override def evaluate(implicit ctx: EvaluationCtx): Boolean = {
    val ConcreteValue(concreteRole) = role
    val ConcreteValue(concreteRoles) = roles
    // FIXME possible casting exceptions here
    val realRole: Role = concreteRole.representation.asInstanceOf[Role]
    val realRoles: Seq[Role] = concreteRoles.representation.asInstanceOf[Seq[Role]]
    // now iterate over the roles to check whether the given role is in there
    for(r <- realRoles) {
      if(r.containsRole(realRole)) return true // TODO why does this have to be an explicit "return" 
    }
    false
  }
}

/**
 * 
 */
case class PermissionIn(permission: Value, roles: Value) extends Expression {
  
  override def evaluate(implicit ctx: EvaluationCtx): Boolean = {
    val ConcreteValue(concretePermission) = permission
    val ConcreteValue(concreteRoles) = roles
    // FIXME possible casting exceptions here
    val realPermission: Permission = concretePermission.representation.asInstanceOf[Permission]
    val realRoles: Seq[Role] = concreteRoles.representation.asInstanceOf[Seq[Role]]
    // now iterate over the roles to check whether the given role is in there
    for(r <- realRoles) {
      if(r.hasPermission(realPermission)) return true // TODO why does this have to be an explicit "return"
    }
    false
  }
}