package coin.collections

import scala.util.Random
import org.openjdk.jmh.annotations._

import scala.annotation.tailrec

object TailrecVsWhile {
  final case class ListNode(value: Int, next: ListNode)

  final val tinyList = buildList(10)
  final val middleList = buildList(1000)
  final val giantList  = buildList(1_000_000)

  def buildList(size: Int): ListNode = {
    val random = new Random()
    var list = null.asInstanceOf[ListNode]
    var i = 0

    while (i < size - 1) {
      list = ListNode(value = random.nextInt(1000), next = list)
      i += 1
    }

    list
  }
}

class TailrecVsWhile {

  import TailrecVsWhile._

  @Benchmark
  def testTinyListWhile(): Long = {
    var currentNode = tinyList
    var sum = 0L

    while (currentNode != null) {
      sum += currentNode.value
      currentNode = currentNode.next
    }

    sum
  }

  @Benchmark
  def testTinyListTailrec(): Long = {
    @tailrec
    def helper(currentNode: ListNode, currentSum: Long): Long =
      if (currentNode == null) currentSum
      else helper(currentNode.next, currentSum + currentNode.value)

    helper(tinyList, 0L)
  }

  @Benchmark
  def testMiddleListWhile() = {
    var currentNode = giantList
    var sum = 0L

    while (currentNode != null) {
      sum += currentNode.value
      currentNode = currentNode.next
    }

    sum
  }

  @Benchmark
  def testMiddleListTailrec() = {
    @tailrec
    def helper(currentNode: ListNode, currentSum: Long): Long =
      if (currentNode == null) currentSum
      else helper(currentNode.next, currentSum + currentNode.value)

    helper(middleList, 0L)
  }

  @Benchmark
  def testGiantListWhile() = {
    var currentNode = giantList
    var sum = 0L

    while (currentNode != null) {
      sum += currentNode.value
      currentNode = currentNode.next
    }

    sum
  }

  @Benchmark
  def testGiantListTailrec() = {
    @tailrec
    def helper(currentNode: ListNode, currentSum: Long): Long =
      if (currentNode == null) currentSum
      else helper(currentNode.next, currentSum + currentNode.value)

    helper(giantList, 0L)
  }
}
