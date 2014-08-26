package stapl.templates

import stapl.core.SubjectAttributeContainer
import stapl.core.ActionAttributeContainer
import stapl.core.ResourceAttributeContainer
import stapl.core.EnvironmentAttributeContainer

trait Template {
  def subject: SubjectAttributeContainer
  def action: ActionAttributeContainer
  def resource: ResourceAttributeContainer
  def environment: EnvironmentAttributeContainer
}