package coin.collections

import org.openjdk.jmh.annotations._

import scala.util.Random

object BranchMispredict {
  final val tinySortedArray = buildSortedArray(51)
  final val mediumSortedArray = buildSortedArray(10_001)
  final val giantSortedArray = buildSortedArray(10_000_001)

  final val tinyShuffledArray = buildShuffledArray(51)
  final val mediumShuffledArray = buildShuffledArray(10_001)
  final val giantShuffledArray = buildShuffledArray(10_000_001)

  private def buildSortedArray(size: Int): Array[Int] = {
    val random = new Random()

    Array.fill(size)(random.nextInt(500))
      .sortInPlace()
      .array
  }

  private def buildShuffledArray(size: Int): Array[Int] = {
    val random = new Random()

    Array.fill(size)(random.nextInt(500))
  }
}

@Fork(2)
@Warmup(iterations = 3)
@Measurement(iterations = 6)
@BenchmarkMode(Array(Mode.Throughput))
class BranchMispredict {
  import BranchMispredict._

  @Benchmark
  def testTinySortedArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < tinySortedArray.length) {
      if (tinySortedArray(i) >= 250) {
        sum += tinySortedArray(i)
      }
      i += 1
    }

    sum
  }

  @Benchmark
  def testTinyShuffledArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < tinyShuffledArray.length) {
      if (tinyShuffledArray(i) >= 250){
        sum += tinyShuffledArray(i)
      }

      i += 1
    }

    sum
  }

  @Benchmark
  def testMediumSortedArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < mediumSortedArray.length) {
      if (mediumSortedArray(i) >= 250) {
        sum += mediumSortedArray(i)
      }

      i += 1
    }

    sum
  }

  @Benchmark
  def testMediumShuffledArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < mediumShuffledArray.length) {
      if (mediumShuffledArray(i) >= 250){
        sum += mediumShuffledArray(i)
      }

      i += 1
    }

    sum
  }

  @Benchmark
  def testGiantSortedArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < giantSortedArray.length) {
      if (giantSortedArray(i) >= 250) {
        sum += giantSortedArray(i)
      }

      i += 1
    }

    sum
  }

  @Benchmark
  def testGiantShuffledArrayHalfSum(): Long = {
    var sum = 0
    var i = 0

    while (i < giantShuffledArray.length) {
      if (giantShuffledArray(i) >= 250){
        sum += giantShuffledArray(i)
      }

      i += 1
    }

    sum
  }
}
