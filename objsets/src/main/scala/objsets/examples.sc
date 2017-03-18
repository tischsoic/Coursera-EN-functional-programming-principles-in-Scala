abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

object Empty extends IntSet{
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  def union(other: IntSet): IntSet = {
//    println("other: " + other)
    other
  }
  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
//    println("incl: " + x)

    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  def union(other: IntSet) = {
//    println("left " + left + ", " + "right " + right + ", " + "other " + other + ", " + "elem " + elem + ", ")
    ((left union right) union other) incl elem
  }

  override def toString = "{" + left + elem + right + "}"
}

val s = Empty.incl(7).incl(10).incl(5).incl(9).incl(2)
val b = Empty.incl(4).incl(6)

s union b
println(s union b)

def takeN[T](n: Int, l: List[T]): T = {
  if (n != 0 && l == Nil) throw new IndexOutOfBoundsException

  if (n == 0) l.head
  else takeN(n - 1, l.tail)
}

takeN(3, List(1,2,3))

