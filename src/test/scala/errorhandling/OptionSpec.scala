package errorhandling

import org.scalatest.{FreeSpec, Matchers}

class Chapter4Spec extends FreeSpec with Matchers {
  import Chapter4._
  "Ex 4.2" - {
    "variance of 1,2,3,4 should be 1.25" in {
      variance(Seq(1,2,3,4)) shouldBe Some(1.25)
    }
  }
}

class OptionSpec extends FreeSpec with Matchers{

  "Ex 4.1" - {
    "map" in {
      Some(1).map(_ + 1) shouldBe Some(2)
      None.map(x => Some(())) shouldBe None
    }

    "flatMap" in {
      Some(1).flatMap(x => Some(x + 1)) shouldBe Some(2)
      None.flatMap(x => Some(())) shouldBe None
    }

    "filter" in {
      Some(1).filter(_ == 0) shouldBe None
      Some(1).filter(_ == 1) shouldBe Some(1)
      None.filter(x => true) shouldBe None
    }

    "getOrElse" in {
      Some(1).getOrElse(2) shouldBe 1
      None.getOrElse(2) shouldBe 2
    }

    "orElse" in {
      Some(1).orElse(Some(2)) shouldBe Some(1)
      None.orElse(Some(2)) shouldBe Some(2)
    }
  }


}