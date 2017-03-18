package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int =
      if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      val bracketStates = chars.toStream
        .scanLeft((0, 0))((acc, char) => acc match {
          case (x, y) => char match {
            case '(' => (x + 1, y)
            case ')' => (x, y + 1)
            case _ => (x, y)
          }
        })

      val leftMoreOrEqualToRight = bracketStates.map({
        case (x, y) => x >= y
      }).foldLeft(true)((acc, b) => acc && b)

      val countEquals = bracketStates.last match {
        case (x, y) => x == y
      }

      countEquals && leftMoreOrEqualToRight
    }

  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money == 0) return 1
      if (coins.isEmpty) return 0

      val coinsSorted = coins.sorted
      val firstCoin = coinsSorted.head

      val changesRange: List[Int] = 0 to (money / firstCoin) toList
      val solutionsNo: List[Int] = changesRange.map((s) => countChange(money - s * firstCoin, coinsSorted.tail))

      solutionsNo.sum
    }
  }
