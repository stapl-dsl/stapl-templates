package stapl.templates.rbac

import stapl.core.ListAttribute
import stapl.core.pdp.EvaluationCtx
import stapl.core.AbstractPolicy
import stapl.core.Rule
import stapl.core.AlwaysTrue
import stapl.core.Deny
import stapl.core.Expression
import stapl.core.And
import stapl.core.Not
import stapl.core.BasicPolicy

trait Roles extends BasicPolicy {  
  
  // add the roles attribute
  subject.roles = ListAttribute(RoleType)
  
  def denyIfNotOneOf(roles: Role*): AbstractPolicy = {
    var condition: Expression = AlwaysTrue
    for(role <- roles) {
      condition = And(condition, subject.hasRole(role))
    }
    condition = Not(condition)
    new Rule("denyIfNotOneOf")(
        target = AlwaysTrue,
        effect = Deny,
        condition = condition
    )
  } 
}
