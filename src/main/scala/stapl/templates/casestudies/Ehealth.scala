package stapl.templates.casestudies

import stapl.core.SubjectAttributeContainer
import stapl.templates.general.Location
import stapl.templates.general.ResourceCreation
import stapl.templates.general.Time
import stapl.templates.rbac._
import stapl.templates.htype.HType
import stapl.templates.htype.HTypes
import stapl.templates.general.GeneralTemplates
import stapl.core._
import stapl.templates.general.Ownership
import stapl.templates.general.Shifts

trait Treating extends Ownership {

  subject.treated = ListAttribute(String)

  /**
   *
   */
  def treatingOwner(subject: SubjectAttributeContainer, resource: ResourceAttributeContainer): Expression =
    resource.owner_id in subject.treated
}

/**
 * Defined by domain specialist
 */
trait BreakingGlass extends BasicPolicy {
  
  // TODO: when importing stapl.core._ we can assign attributes
  // to subject, resource, action and environment without extending
  // BasicPolicy, but these are not the correct instances... 
  // => make this impossible by renaming the instances in stapl.core or
  // moving this to another package
  
  subject.triggered_breaking_glass = SimpleAttribute(Bool)
}

/**
 * Defined by domain specialist
 */
trait Consent extends BreakingGlass {
  
  resource.owner_withdrawn_consents = ListAttribute(String)
  
  def denyIfNotConsent(target: Expression = AlwaysTrue) = Policy("policy:1") := when (target) apply PermitOverrides to (
        Rule("consent") := deny iff (subject.id in resource.owner_withdrawn_consents),
        Rule("breaking-glass") := permit iff (subject.triggered_breaking_glass) performing (log(subject.id + " performed breaking-the-glass procedure"))
    ) performing (log("permit because of breaking-the-glass procedure") on Permit)
}

/**
 * This trait initializes the policy environment with the appropriate attributes and
 * policy templates for the policies of the hospital. This trait will probably be written
 * by the security developer of the hospital.
 */
trait HospitalPolicy extends BasicPolicy
						with Time
						with GeneralTemplates 
						with Roles 
						with Treating 
						with Shifts 
						with Location
						with BreakingGlass
						with Consent {

  /**
   * The hospital's role hierarchy. For scoping reasons, we define the roles
   * in a separate object.
   *
   * Note that the hospital policies do not employ permissions.
   *
   * TODO how can we couple the roles to the subject refinement? It would be nice to be
   * able to statically assign roles to different subjects.
   */
  val person = Role("person")
  val patient = Role("patient", person)
  val employee = Role("employee", person)
  val medical_personel = Role("medical_personel", employee)
  val nurse = Role("nurse", medical_personel)
  val physician = Role("physician", medical_personel)
  val gp = Role("gp", physician)

  /**
   * The hospital's departments. Again, we define the departments
   * in a separate object for scoping reasons.
   *
   * Note that departments are just Strings, as opposed to Roles which
   * are a separate type. This allows for hardcoded values in the policies
   * or incorrect values, but does not require to extend STAPL itself.
   */
  // TODO an enum attribute type would also come in handy
  val cardiology = "cardiology"
  val emergency = "emergency"
  val elder_care = "elder_care"

  /**
   * Some attributes specific to the hospital
   */
  //environment.currentDateTime = SimpleAttribute(DateTime)
  //resource.type_ = SimpleAttribute(String)
  //resource.owner_withdrawn_consents = ListAttribute(String)
  //resource.operator_triggered_emergency = SimpleAttribute(Bool)
  //resource.indicates_emergency = SimpleAttribute(Bool)
  //resource.owner_id = SimpleAttribute("owner:id", String)
  resource.owner_responsible_physicians = ListAttribute("owner:responsible_physicians", String)
  resource.owner_discharged = SimpleAttribute("owner:discharged", Bool)
  resource.owner_discharged_dateTime = SimpleAttribute("owner:discharged_dateTime", DateTime)
  //resource.patient_status = SimpleAttribute(String)
  //resource.created = SimpleAttribute(DateTime)
  //subject.roles = ListAttribute(String)
  //subject.triggered_breaking_glass = SimpleAttribute(Bool)
  subject.department = SimpleAttribute(String)
  subject.current_patient_in_consultation = SimpleAttribute(String)
  subject.treated_in_last_six_months = ListAttribute(String)
  subject.primary_patients = ListAttribute(String)
  subject.is_head_physician = SimpleAttribute(Bool)
  //subject.treated = ListAttribute(String)
  subject.treated_by_team = ListAttribute(String)
  subject.admitted_patients_in_care_unit = ListAttribute(String)
  //subject.shift_start = SimpleAttribute(DateTime)
  //subject.shift_stop = SimpleAttribute(DateTime)
  //subject.location = SimpleAttribute(String)
  subject.admitted_patients_in_nurse_unit = ListAttribute(String)
  subject.allowed_to_access_pms = SimpleAttribute(Bool)
  subject.responsible_patients = ListAttribute(String)

}