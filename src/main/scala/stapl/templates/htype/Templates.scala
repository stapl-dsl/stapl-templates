package stapl.templates.htype

import stapl.templates.Template
import stapl.core.SimpleAttribute
import stapl.core.pdp.EvaluationCtx

trait HTypes extends Template {
  
  // add the htype attribute
  resource.htype = SimpleAttribute(HTypeType)
  
  // no methods to define here
}
