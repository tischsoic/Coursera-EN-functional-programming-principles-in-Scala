import patmat.Huffman.{Fork, Leaf, encode, decode, encodeOneChar, createCodeTree}

abstract class Nat {
  def isZero: Boolean

  def predecessor: Nat

  def successor: Nat = new Succ(Zero)

  def +(that: Nat): Nat

  def -(that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true

  def predecessor: Nat = throw new Error()


  def +(that: Nat): Nat = that

  def -(that: Nat): Nat = if (that.isZero) Zero else throw new Error()
}

class Succ(n: Nat) extends Nat {
  def isZero: Boolean = false

  def predecessor: Nat = n

  def +(that: Nat): Nat = new Succ(n + that)

  def -(that: Nat): Nat =
    if (that.isZero) this
    else n - that.predecessor
}

val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
val e1 = encode(t1)("ab".toList)
val d1 = decode(t1, e1)
encode(t1)(List('b'))
"asdf".toList.flatMap(c => List(1,1,0))

encodeOneChar(t1, 'a')
encodeOneChar(t1, 'b')

createCodeTree("asdasd".toList)


