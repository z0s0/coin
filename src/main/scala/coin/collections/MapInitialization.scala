package coin.collections

import org.openjdk.jmh.annotations.Benchmark

/*
  Expected baseline— Java mutable map
[info] MapInitialization.testLargeJavaMap         thrpt   25      81,574 ±     5,053  ops/s
[info] MapInitialization.testLargeScalaImmutMap   thrpt   25      45,593 ±     0,126  ops/s
[info] MapInitialization.testLargeScalaMutMap     thrpt   25      86,898 ±     6,902  ops/s
[info] MapInitialization.testMediumJavaMap        thrpt   25   51592,549 ±  1938,440  ops/s
[info] MapInitialization.testMediumScalaImmutMap  thrpt   25    8002,865 ±   135,713  ops/s
[info] MapInitialization.testMediumScalaMutMap    thrpt   25   54023,363 ±  1504,304  ops/s
[info] MapInitialization.testSmallJavaMap         thrpt   25  569030,009 ± 37840,659  ops/s
[info] MapInitialization.testSmallScalaImmutMap   thrpt   25  133494,112 ±  5122,933  ops/s
[info] MapInitialization.testSmallScalaMutMap     thrpt   25  554560,911 ± 23776,054  ops/s

 */

import java.util.{HashMap => JMap}
import scala.collection.mutable.{HashMap => MutableMap}
import scala.collection.immutable.{Map => ImmutableMap}

object MapInitialization {
  final val smallRange = (0 until(100)).toArray
  final val mediumRange = (0 until(1000)).toArray
  final val largeRange = (0 until(100_000)).toArray
}

class MapInitialization {
  import MapInitialization._

  @Benchmark
  def testSmallJavaMap(): Unit = {
    val map = new JMap[Int, Int]()

    smallRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testSmallScalaMutMap() = {
    val map = MutableMap.empty[Int, Int]

    smallRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testSmallScalaImmutMap() = {
    val map = ImmutableMap.empty[Int, Int]

    smallRange.foldLeft(map)((acc, el) => {
      acc + ((el, el))
    })
  }

  @Benchmark
  def testMediumJavaMap() = {
    val map = new JMap[Int, Int]()

    mediumRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testMediumScalaMutMap() = {
    val map = MutableMap.empty[Int, Int]

    mediumRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testMediumScalaImmutMap() = {
    val map = ImmutableMap.empty[Int, Int]

    mediumRange.foldLeft(map)((acc, el) => {
      acc + ((el, el))
    })
  }

  @Benchmark
  def testLargeJavaMap() = {
    val map = new JMap[Int, Int]()

    largeRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testLargeScalaMutMap() = {
    val map = MutableMap.empty[Int, Int]

    largeRange.foreach(num => map.put(num, num))
  }

  @Benchmark
  def testLargeScalaImmutMap() = {
    val map = ImmutableMap.empty[Int, Int]

    largeRange.foldLeft(map)((acc, el) => {
      acc + ((el, el))
    })
  }
}
