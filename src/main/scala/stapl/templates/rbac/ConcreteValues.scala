package stapl.templates.rbac

import stapl.core.BasicValue
import stapl.core.SeqValue


/**
 * 
 */
class PermissionImpl(value: Permission) extends BasicValue(value, PermissionType) {
  
  def canEqual(other: Any): Boolean = other.isInstanceOf[PermissionImpl]
}

/**
 * 
 */
class RoleImpl(value: Role) extends BasicValue(value, RoleType) {
  
  def canEqual(other: Any): Boolean = other.isInstanceOf[RoleImpl]
}

/**
 * 
 */
class RoleSeqImpl(seq: Seq[Role]) extends SeqValue(seq, RoleType) {
  
  def canEqual(other: Any) = other.isInstanceOf[RoleSeqImpl]
}

/**
 * 
 */
class PermissionSeqImpl(seq: Seq[Permission]) extends SeqValue(seq, PermissionType) {
  
  def canEqual(other: Any) = other.isInstanceOf[PermissionSeqImpl]
}