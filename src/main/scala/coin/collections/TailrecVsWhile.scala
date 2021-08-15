package coin.collections

import scala.util.Random
import org.openjdk.jmh.annotations._

import scala.annotation.tailrec

/*
 Benchmark                              Mode  Cnt          Score         Error  Units
 TailrecVsWhile.testGiantListRec       thrpt   25        350,519 ±      33,776  ops/s
 TailrecVsWhile.testGiantListTailrec   thrpt   25        402,993 ±      20,173  ops/s
 TailrecVsWhile.testGiantListWhile     thrpt   25        406,692 ±      15,931  ops/s
 TailrecVsWhile.testMiddleListRec      thrpt   25     592760,129 ±   37831,916  ops/s
 TailrecVsWhile.testMiddleListTailrec  thrpt   25     612904,067 ±   37424,060  ops/s
 TailrecVsWhile.testMiddleListWhile    thrpt   25        385,124 ±      15,209  ops/s
 TailrecVsWhile.testTinyListRec        thrpt   25  108836822,438 ± 4772547,046  ops/s
 TailrecVsWhile.testTinyListTailrec    thrpt   25  112882888,335 ± 4676467,915  ops/s
 TailrecVsWhile.testTinyListWhile      thrpt   25  114621676,233 ± 5646963,962  ops/s

 */
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
  def testTinyListRec(): Long = {
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
  def testMiddleListRec() = {
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

 @Benchmark
  def testGiantListRec() = {
    def helper(currentNode: ListNode, currentSum: Long): Long =
      if (currentNode == null) currentSum
      else helper(currentNode.next, currentSum + currentNode.value)

    helper(giantList, 0L)
  }
}
