package stapl.templates.htype

import stapl.core.ResourceAttributeContainer
import stapl.core.Expression


/**
 * Class used for representing a subject with additional RBAC methods.
 */
class HierarchicalTypedResource(resource: ResourceAttributeContainer) {
  
  /**
   * Returns whether this resource has the given type. A resource can 
   * have this type directly or indirectly.
   */
  def hasType(htype: HType): Expression = new IsType(htype, resource.htype) 
  
}