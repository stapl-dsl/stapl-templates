package stapl.templates

/**
 * The implicit conversions for this template.
 */
import stapl.core.ResourceAttributeContainer
import stapl.core.ConcreteValue

package object htype {
  
  implicit def htype2Value(htype: HType): ConcreteValue = new HTypeImpl(htype)
  
  /**
   * Add methods to resource for checking the type
   */
  implicit def resource2HTypeResource(resource: ResourceAttributeContainer): HierarchicalTypedResource = 
    new HierarchicalTypedResource(resource)
}