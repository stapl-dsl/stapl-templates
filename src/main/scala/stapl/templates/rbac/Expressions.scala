package stapl.templates.rbac

import stapl.core.Value
import stapl.core.Expression
import stapl.core.pdp.EvaluationCtx
import stapl.core.ConcreteValue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Success, Failure}

/**
 *
 */
case class RoleIn(role: Value, roles: Value) extends Expression {

  override def evaluate(implicit ctx: EvaluationCtx): Boolean = {
    val concreteRole = role.getConcreteValue(ctx)
    val concreteRoles = roles.getConcreteValue(ctx)
    // FIXME possible casting exceptions here
    val realRole: Role = concreteRole.representation.asInstanceOf[Role]
    val realRoles: Seq[Role] = concreteRoles.representation.asInstanceOf[Seq[Role]]
    // now iterate over the roles to check whether the given role is in there
    realRoles.exists(_.containsRole(realRole))
    /*for(r <- realRoles) {
      if(r.containsRole(realRole)) return true // XXX why does this have to be an explicit "return" 
    }
    false*/
  }
}

/**
 *
 */
case class PermissionIn(permission: Value, roles: Value) extends Expression {

  override def evaluate(implicit ctx: EvaluationCtx): Boolean = {
    val concretePermission = permission.getConcreteValue(ctx)
    val concreteRoles = roles.getConcreteValue(ctx)
    // FIXME possible casting exceptions here
    val realPermission: Permission = concretePermission.representation.asInstanceOf[Permission]
    val realRoles: Seq[Role] = concreteRoles.representation.asInstanceOf[Seq[Role]]
    // now iterate over the roles to check whether the given role is in there
    realRoles.exists(_.hasPermission(realPermission))
    /*for(r <- realRoles) {
      if(r.hasPermission(realPermission)) return true // XXX why does this have to be an explicit "return"
    }
    false*/
  }
}