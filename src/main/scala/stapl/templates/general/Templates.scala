package stapl.templates.general

// See stapl.core.templates

import stapl.core._

trait ResourceOwners extends BasicPolicy {
  
  resource.owner = SimpleAttribute(String)
}

trait Time extends BasicPolicy {
  
  environment.currentDateTime = SimpleAttribute(DateTime)
}

trait Location extends BasicPolicy {
  
  subject.location = SimpleAttribute(String)
}

/**
 * Package containing some policy templates which commonly occur in policies. 
 */
trait GeneralTemplates {

  /**
   * TODO Actually, this is DenyIffNot
   */
  def OnlyPermitIff(id: String)(target: Expression, condition: Expression): Policy =
    Policy(id) := when(target) apply PermitOverrides to (
      Rule("OnlyPermitIff-condition") := permit iff (condition),
      Rule("OnlyPermitIff-deny") := deny)

  /**
   * TODO Actually, this is PermitIffNot
   */
  def OnlyDenyIff(id: String)(target: Expression, condition: Expression): Policy =
    Policy(id) := when(target) apply DenyOverrides to (
      Rule("OnlyDenyIff-condition") := deny iff (condition),
      Rule("OnlyDenyIff-permit") := permit)

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