package stapl.templates.casestudies

import stapl.core.SubjectAttributeContainer
import stapl.core._
import stapl.templates.general.ResourceOwners
import stapl.templates.general.Location
import stapl.templates.rbac._
import stapl.templates.htype.HType
import stapl.templates.htype.HTypes
import stapl.templates.general.GeneralTemplates

trait Treating extends ResourceOwners {

  subject.treating = ListAttribute(String)

  /**
   *
   */
  def testTreatingOwner(subject: SubjectAttributeContainer, resource: ResourceAttributeContainer): AbstractPolicy =
    Rule("treating-owner") := deny iff (!(resource.owner in subject.treating))
}

trait Shifts extends BasicPolicy {

  subject.shift_start = SimpleAttribute(DateTime)
  subject.shift_stop = SimpleAttribute(DateTime)
  environment.currentDateTime = SimpleAttribute(DateTime)

  def testHasShift(subject: SubjectAttributeContainer): AbstractPolicy =
    Rule("has-shift") := deny iff !((environment.currentDateTime gteq subject.shift_start) & (environment.currentDateTime lteq subject.shift_stop))
}

trait Consent extends BasicPolicy {

}

/**
 * This trait initializes the policy environment with the appropriate attributes and
 * policy templates for the policies of the hospital. This trait will probably be written
 * by the security developer of the hospital.
 */
trait HospitalPolicy extends BasicPolicy with GeneralTemplates with Roles with Treating with Shifts with Location with HTypes {

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
   * The hospital's resource types.
   */
  val patientStatus = HType("patientstatus")

  /**
   * The hospital's departments. Again, we define the departments
   * in a separate object for scoping reasons.
   *
   * Note that departments are just Strings, as opposed to Roles which
   * are a separate type. This allows for hardcoded values in the policies
   * or incorrect values, but does not require to extend STAPL itself.
   */
  val cardiology = "cardiology"
  val emergency = "emergency"
  val elder_care = "elder_care"

  /**
   * Some attributes specific to the hospital
   */
  //environment.currentDateTime = SimpleAttribute(DateTime)
  resource.type_ = SimpleAttribute(String)
  resource.owner_withdrawn_consents = ListAttribute(String)
  resource.operator_triggered_emergency = SimpleAttribute(Bool)
  resource.indicates_emergency = SimpleAttribute(Bool)
  resource.owner_id = SimpleAttribute("owner:id", String)
  resource.owner_responsible_physicians = ListAttribute("owner:responsible_physicians", String)
  resource.owner_discharged = SimpleAttribute("owner:discharged", Bool)
  resource.owner_discharged_dateTime = SimpleAttribute("owner:discharged_dateTime", DateTime)
  resource.patient_status = SimpleAttribute(String)
  resource.created = SimpleAttribute(DateTime)
  //subject.roles = ListAttribute(String)
  subject.triggered_breaking_glass = SimpleAttribute(Bool)
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