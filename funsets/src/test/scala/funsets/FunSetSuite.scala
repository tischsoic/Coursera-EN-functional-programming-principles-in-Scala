package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements which exists in first AND second sets") {
    new TestSets {
      val s12 = union(s1, s2)
      val s23 = union(s2, s3)
      val sIntersect = intersect(s12, s23)
      assert(contains(sIntersect, 2), "Intersect contains 2")
      assert(!contains(sIntersect, 1), "Intersect does not contain 1")
      assert(!contains(sIntersect, 3), "Intersect does not contain 3")
    }
  }

  test("diff contains only elements which exists in first set AND NOT in second set") {
    new TestSets {
      val s12 = union(s1, s2)
      val s23 = union(s2, s3)
      val sDiff = diff(s12, s23)
      assert(contains(sDiff, 1), "Diff contains 1")
      assert(!contains(sDiff, 2), "Diff does not contain 2")
      assert(!contains(sDiff, 3), "Diff does not contain 3")
    }
  }

  test("filter filters correctly the set") {
    new TestSets {
      val s12 = union(s1, s2)
      val s123 = union(s12, s3)
      val sFiltered = filter(s123, (x: Int) => x % 2 != 0)
      assert(contains(sFiltered, 1), "Filtered set contains 1")
      assert(!contains(sFiltered, 2), "Filtered set does not contain 2")
      assert(contains(sFiltered, 3), "Filtered set contains 3")
    }
  }

  test("forall detects that NOT every element of set satisfies condition") {
    new TestSets {
      val s12 = union(s1, s2)
      val s123 = union(s12, s3)
      assert(!forall(s123, (x) => x % 2 == 0), "Contains 1 and 3 for which x % 2 != 0")
    }
  }

  test("forall detects that EVERY element of set satisfies condition") {
    new TestSets {
      val s12 = union(s1, s2)
      val s123 = union(s12, s3)
      assert(forall(s123, (x) => x % 2 != 0 || x == 2), "Every element should satisfy condition")
    }
  }

  test("exists detects that element exists or not") {
    new TestSets {
      val s12 = union(s1, s2)
      assert(!exists(s12, (x) => x == 3), "Element 3 does not exists")
      assert(exists(s12, (x) => x == 2), "Element 2 does exists")
    }
  }

  test("map maps the elements of set") {
    new TestSets {
      val s12 = union(s1, s2)
      val sMapped = map(s12, (x) => x * x)
      assert(contains(sMapped, 1), "Contains 1")
      assert(contains(sMapped, 4), "Contains 4")
      assert(!contains(sMapped, 2), "Does not contains 2")
    }
  }
}
