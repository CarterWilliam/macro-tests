package will.chains

import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class SomeIntegers(integers: List[Int])
object SomeIntegers {
  implicit object ProvideIntegers extends Step[Future, SomeIntegers] {
    override def run(): Future[SomeIntegers] = {
      Future.successful(SomeIntegers(List(1, 2, 3)))
    }
  }
}

case class Average(avg: Double)
object Average {
  implicit object AverageStep extends ChainableStep[Future, SomeIntegers, Average] {
    override def apply(transactions: SomeIntegers): Future[Average] = {
      Future.successful {
        Average(transactions.integers.sum / transactions.integers.size)
      }
    }
  }
}
