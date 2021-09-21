package coin.collections
import org.openjdk.jmh.annotations.Benchmark

import java.util.{HashMap => JMap}
import scala.collection.mutable.{HashMap => MutableMap}
import scala.collection.immutable.{Map => ImmutableMap}
import scala.util.Random

/*
[info] MapLookup.testLargeJavaMap         thrpt   25      874,230 ±     50,984  ops/s
[info] MapLookup.testLargeScalaImmutMap   thrpt   25      184,249 ±      6,353  ops/s
[info] MapLookup.testLargeScalaMutMap     thrpt   25      714,176 ±     12,069  ops/s
[info] MapLookup.testMediumJavaMap        thrpt   25   191143,643 ±  15978,238  ops/s
[info] MapLookup.testMediumScalaImmutMap  thrpt   25    67788,645 ±   3731,485  ops/s
[info] MapLookup.testMediumScalaMutMap    thrpt   25   169862,952 ±  10439,416  ops/s
[info] MapLookup.testSmallJavaMap         thrpt   25  3496125,264 ±  90747,126  ops/s
[info] MapLookup.testSmallScalaImmutMap   thrpt   25  1157798,914 ±  29237,393  ops/s
[info] MapLookup.testSmallScalaMutMap     thrpt   25  3906654,043 ± 418507,971  ops/s
 */
object MapLookup {
  private final val smallRange = (0 until(100))
  private final val mediumRange = (0 until(1000))
  private final val largeRange = (0 until(100_000))
  private val r = new Random()

  val shuffledSmallArr = r.shuffle(smallRange.toList).toArray
  val shuffledMediumArr = r.shuffle(mediumRange.toList).toArray
  val shuffledLargeArr = r.shuffle(largeRange.toList).toArray

  final val smallJavaMap = new JMap[Int, Int]()
  smallRange.foreach(n => smallJavaMap.put(n, n))

  final val smallScalaMutMap = MutableMap.empty[Int, Int]
  smallRange.foreach(n => smallScalaMutMap.put(n, n))

  final val smallScalaImmutMap = smallRange.foldLeft(ImmutableMap.empty[Int, Int])((acc, el) => acc + ((el, el)))

  final val mediumJavaMap = new JMap[Int, Int]()
  mediumRange.foreach(n => mediumJavaMap.put(n, n))

  final val mediumScalaMutMap = MutableMap.empty[Int, Int]
  mediumRange.foreach(n => mediumScalaMutMap.put(n, n))

  final val mediumScalaImmutMap = mediumRange.foldLeft(ImmutableMap.empty[Int, Int])((acc, el) => acc + ((el, el)))

  final val largeJavaMap = new JMap[Int, Int]()
  largeRange.foreach(n => largeJavaMap.put(n, n))

  final val largeScalaMutMap = MutableMap.empty[Int, Int]
  largeRange.foreach(n => largeScalaMutMap.put(n, n))

  final val largeScalaImmutMap = largeRange.foldLeft(ImmutableMap.empty[Int, Int])((acc, el) => acc + ((el, el)))
}

class MapLookup {
  import MapLookup._

  @Benchmark
  def testSmallJavaMap() = shuffledSmallArr.foreach(smallJavaMap.get)

  @Benchmark
  def testSmallScalaMutMap() = shuffledSmallArr.foreach(smallScalaMutMap.get)

  @Benchmark
  def testSmallScalaImmutMap() = shuffledSmallArr.foreach(smallScalaImmutMap.get)

  @Benchmark
  def testMediumJavaMap() = shuffledMediumArr.foreach(mediumJavaMap.get)

  @Benchmark
  def testMediumScalaMutMap() = shuffledMediumArr.foreach(mediumScalaMutMap.get)

  @Benchmark
  def testMediumScalaImmutMap() = shuffledMediumArr.foreach(mediumScalaImmutMap.get)

  @Benchmark
  def testLargeJavaMap() = shuffledLargeArr.foreach(largeJavaMap.get)

  @Benchmark
  def testLargeScalaMutMap() = shuffledLargeArr.foreach(largeScalaMutMap.get)

  @Benchmark
  def testLargeScalaImmutMap() = shuffledLargeArr.foreach(largeScalaImmutMap.get)
}
