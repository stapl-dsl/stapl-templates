package stapl.templates.htype

import stapl.core.SimpleAttribute
import stapl.core.pdp.EvaluationCtx
import stapl.core.BasicPolicy
import stapl.templates.htype.HTypeType;

trait HTypes extends BasicPolicy {
  
  // add the htype attribute
  resource.htype = SimpleAttribute(HTypeType)
  
  // no methods to define here
}
