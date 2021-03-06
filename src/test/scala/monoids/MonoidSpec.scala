package monoids

import org.scalatest.{FreeSpec, Matchers}

class MonoidSpec extends FreeSpec with Matchers {
  "Monoid" - {
    "String" in {
      // TODO property based testing
      val sMonoid = new Monoid[String] {
        override def op(a1: String, a2: String): String = a1 + a2

        val zero = ""
      }

      sMonoid.op(sMonoid.op("a", "b"), "c") shouldBe sMonoid.op("a", sMonoid.op("b", "c"))
      sMonoid.op(sMonoid.zero, "a") shouldBe "a"
      sMonoid.op("a", sMonoid.zero) shouldBe "a"
    }

    "10.1" - {
      "intAddition" in {
        val iAddition = new Monoid[Int]{
          override def op(a1: Int, a2: Int): Int = a1 + a2

          override def zero: Int = 0
        }
        iAddition.op(iAddition.op(1, 2), 3) shouldBe iAddition.op(1, iAddition.op(2,3))
        iAddition.op(iAddition.zero, 1) shouldBe 1
        iAddition.op(1, iAddition.zero) shouldBe 1
      }

      "intMultiplication" in {
        val iMultiplication = new Monoid[Int] {
          override def op(a1: Int, a2: Int): Int = a1 * a2

          override def zero: Int = 1
        }
        iMultiplication.op(iMultiplication.op(1, 2), 3) shouldBe iMultiplication.op(1, iMultiplication.op(2,3))
        iMultiplication.op(iMultiplication.zero, 1) shouldBe 1
        iMultiplication.op(1, iMultiplication.zero) shouldBe 1
      }

      "boolOr" in {
        val boolOr = new Monoid[Boolean] {
          override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2

          override def zero: Boolean = false
        }
        boolOr.op(boolOr.op(true, true), false) shouldBe boolOr.op(true, boolOr.op(true, false))
        boolOr.op(boolOr.zero, false) shouldBe false
        boolOr.op(false, boolOr.zero) shouldBe false
        boolOr.op(boolOr.zero, true) shouldBe true
        boolOr.op(true, boolOr.zero) shouldBe true
      }

      "boolAnd" in {
        val boolAnd = new Monoid[Boolean] {
          override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2

          override def zero: Boolean = true
        }
        boolAnd.op(boolAnd.op(true, true), false) shouldBe boolAnd.op(true, boolAnd.op(true, false))
        boolAnd.op(boolAnd.zero, false) shouldBe false
        boolAnd.op(false, boolAnd.zero) shouldBe false
        boolAnd.op(boolAnd.zero, true) shouldBe true
        boolAnd.op(true, boolAnd.zero) shouldBe true
      }
    }

    "10.2" in {
      def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
        override def op(a1: Option[A], a2: Option[A]): Option[A] = a1 orElse a2

        override def zero: Option[A] = None
      }

      // TODO

    }

    "10.3" in {
      def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
        override def op(a1: (A) => A, a2: (A) => A): (A) => A = a1.andThen(a2)

        override def zero: (A) => A = (a: A) => a
      }


      def plusOne(x: Int): Int = x + 1
      endoMonoid[Int].op(endoMonoid[Int].op(x => x + 1 , x => x + 2), x => x + 3).apply(10) shouldBe
        endoMonoid[Int].op(x => x + 1, endoMonoid[Int].op( x => x + 2, x => x + 3)).apply(10)
      endoMonoid[Int].op(endoMonoid[Int].zero, plusOne).apply(10) shouldBe plusOne(10)
      endoMonoid[Int].op( x => x + 1, endoMonoid[Int].zero).apply(10) shouldBe plusOne(10)
    }
  }


}
