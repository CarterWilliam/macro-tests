package will.macrotests

import scala.language.experimental.macros
import org.specs2.matcher.ShouldMatchers
import org.specs2.mutable.Specification

class FunGenSpec extends Specification with ShouldMatchers {

  "FunGen.generate" should {

    "generate the identity function" in {
      FunGen.generate[String, String](identity).execute("hmm") shouldEqual "hmm"
    }

    "generate the duplicate function" in {
      val duplicate = FunGen.generate[String, String] { a: String => a + a }
      duplicate.execute("hmm") shouldEqual "hmmhmm"
    }

    "handle all types" in {
      val double = FunGen.generate[Int, Int] { a: Int => a + a }
      double.execute(8) shouldEqual 16
    }

    "allow a single config parameter" in {
      val appendParam = FunGen.generate[String, Int, String]{ (input: String, eight: Int) =>
        input + eight
      }

      appendParam.execute("in") shouldEqual "in8"
    }

  }

}
