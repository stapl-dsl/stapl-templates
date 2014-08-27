package stapl.templates.htype

import stapl.core.BasicValue
import stapl.core.SeqValue

/**
 * 
 */
class HTypeImpl(value: HType) extends BasicValue(value, HTypeType) {
  
  def canEqual(other: Any): Boolean = other.isInstanceOf[HTypeImpl]
}