package stapl.templates.general

// See stapl.core.templates

import stapl.core._

trait Time extends BasicPolicy {
  
  environment.currentDateTime = SimpleAttribute(DateTime)
}

trait Shifts extends Time {

  subject.shift_start = SimpleAttribute(DateTime)
  subject.shift_stop = SimpleAttribute(DateTime)

  class SubjectWithShifts(subject: SubjectAttributeContainer) {
	def onShift: Expression =
			(environment.currentDateTime gteq subject.shift_start) & (environment.currentDateTime lteq subject.shift_stop)
  }
  implicit def subject2SubjectWithShifts(subject: SubjectAttributeContainer) = new SubjectWithShifts(subject)
}

trait Location extends BasicPolicy {
  
  subject.location = SimpleAttribute(String)
}

trait ResourceCreation extends BasicPolicy {
  
  resource.created = SimpleAttribute(DateTime)
  resource.creator = SimpleAttribute(String)
}

trait Ownership extends BasicPolicy {
  
  resource.owner_id = SimpleAttribute("owner:id", String)
}

/**
 * Package containing some policy templates which commonly occur in policies. 
 */
trait GeneralTemplates extends BasicPolicy {

  /**
   * TODO Actually, this is DenyIffNot
   */
  def OnlyPermitIff(id: String)(target: Expression)(condition: Expression): Policy =
    Policy(id) := when(target) apply PermitOverrides to (
      Rule("OnlyPermitIff-condition") := permit iff (condition),
      Rule("OnlyPermitIff-deny") := deny)
  def OnlyPermitIff(target: Expression, condition: Expression): Policy =
    OnlyPermitIff("only-permit-iff")(target)(condition)
  def OnlyPermitIff(condition: Expression): Policy =
    OnlyPermitIff("only-permit-iff")(AlwaysTrue)(condition)

  /**
   * TODO Actually, this is PermitIffNot
   */
  def OnlyDenyIff(id: String)(target: Expression)(condition: Expression): Policy =
    Policy(id) := when(target) apply DenyOverrides to (
      Rule("OnlyDenyIff-condition") := deny iff (condition),
      Rule("OnlyDenyIff-permit") := permit)
  def OnlyDenyIff(target: Expression, condition: Expression): Policy =
    OnlyDenyIff("only-permit-iff")(target)(condition)
  def OnlyDenyIff(condition: Expression): Policy =
    OnlyDenyIff("only-permit-iff")(AlwaysTrue)(condition)

  /**
   * Default permit with default id.
   */
  def defaultPermit: Rule = defaultPermit("default-permit")

  /**
   * Always returns Permit on every request.
   */
  def defaultPermit(id: String): Rule =
    new Rule(id)(
      target = AlwaysTrue,
      effect = Permit,
      condition = AlwaysTrue)

  /**
   * Default deny with default id.
   */
  def defaultDeny: Rule = defaultDeny("default-deny")

  /**
   * Always returns Deny on every request.
   */
  def defaultDeny(id: String): Rule =
    new Rule(id)(
      target = AlwaysTrue,
      effect = Deny,
      condition = AlwaysTrue)

}