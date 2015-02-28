package stapl.templates.htype

import stapl.core.Value
import stapl.core.Expression
import stapl.core.pdp.EvaluationCtx
import stapl.core.ConcreteValue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Success, Failure}

/**
 * Returns whether type2 is a type1. This holds if type2 equals type1 or
 * if type1 is an ancestor of type2.
 */
case class IsType(type1: Value, type2: Value) extends Expression {

  override def evaluate(implicit ctx: EvaluationCtx): Boolean = {
    val concreteType1 = type1.getConcreteValue(ctx)
    val concreteType2 = type2.getConcreteValue(ctx)
    // FIXME possible casting exceptions here
    val realType1: HType = concreteType1.representation.asInstanceOf[HType]
    val realType2: HType = concreteType2.representation.asInstanceOf[HType]
    realType2.is(realType1)
  }
}