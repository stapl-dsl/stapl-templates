package stapl.templates.htype


/**
 * Class used for representing a type in a type hierarchy.
 */
class HType(val name: String, val parent: Option[HType]) {
  
  /**
   * Returns whether this type is the given type, i.e., if this 
   * type equals the given type or the given type is in an ancestor 
   * of this type.
   */
  def is(htype: HType): Boolean = {
    if(this == htype) return true // TODO why does this have to be an explicit "return"
    parent match {
      case Some(p) => p.is(htype)
      case None => false
    }
  } 
}
object HType {
  
  def apply(name: String) = new HType(name, None)
  
  def apply(name: String, parent: HType) = new HType(name, Some(parent))
}