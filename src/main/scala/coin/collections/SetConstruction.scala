package coin.collections

import org.openjdk.jmh.annotations.Benchmark
import java.util.{HashSet => JSet}
import scala.collection.mutable.{Set => MutMap}

/*
  SetConstruction.testLargeJavaSet         thrpt   25      411.602 ±     15.056  ops/s
  SetConstruction.testLargeScalaImmutSet   thrpt   25      100.671 ±      6.929  ops/s
  SetConstruction.testLargeScalaMutSet     thrpt   25     1637.060 ±     80.462  ops/s
  SetConstruction.testMediumJavaSet        thrpt   25     8666.576 ±    227.849  ops/s
  SetConstruction.testMediumScalaImmutSet  thrpt   25     1354.387 ±     55.710  ops/s
  SetConstruction.testMediumScalaMutSet    thrpt   25    20348.241 ±    410.128  ops/s
  SetConstruction.testSmallJavaSet         thrpt   25  7528984.051 ± 727200.953  ops/s
  SetConstruction.testSmallScalaImmutSet   thrpt   25   966475.517 ±   5273.066  ops/s
  SetConstruction.testSmallScalaMutSet     thrpt   25  8983671.135 ± 272258.341  ops/s
 */

object SetConstruction {
  private final val smallSize = (0 until 20).map(_.toString)
  private final val mediumSize = (0 until 10_000).map(_.toString)
  private final val largeSize = (0 until 100_000).map(_.toString)
}

class SetConstruction {
  import SetConstruction._

  @Benchmark
  def testSmallJavaSet() = {
    val set = new JSet[String]()
    smallSize.foreach(set.add)
  }

  @Benchmark
  def testSmallScalaMutSet() = {
    val set = MutMap.empty[String]
    set.addAll(smallSize)
  }

  @Benchmark
  def testSmallScalaImmutSet() = smallSize.toSet

  @Benchmark
  def testMediumJavaSet() = {
    val set = new JSet[String]()
    mediumSize.foreach(set.add)
  }

  @Benchmark
  def testMediumScalaMutSet() = {
    val set = MutMap.empty[String]
    set.addAll(mediumSize)
  }

  @Benchmark
  def testMediumScalaImmutSet(): Set[String] = mediumSize.toSet

  @Benchmark
  def testLargeJavaSet() = {
    val set = new JSet[String]
    largeSize.foreach(set.add)
  }

  @Benchmark
  def testLargeScalaMutSet() = {
    val set = MutMap.empty[String]
    set.addAll(largeSize)
  }

  @Benchmark
  def testLargeScalaImmutSet() = largeSize.toSet
}
