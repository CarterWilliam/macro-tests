package will.chains

import org.specs2.mutable.Specification
import will.chains.Average.AverageStep

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class StepSpec extends Specification {

  "Steps" should {
    "be chainable" in {
      Await.result(AverageStep.run(), 1.second) should be equalTo Average(2.0)
    }
  }
}
