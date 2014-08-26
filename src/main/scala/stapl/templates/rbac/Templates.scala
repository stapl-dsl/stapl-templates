package stapl.templates.rbac

import stapl.templates.Template
import stapl.core.ListAttribute
import stapl.core.pdp.EvaluationCtx

trait RBACTemplate extends Template {
  
  // add the roles attribute
  subject.roles = ListAttribute(RoleType)
}
