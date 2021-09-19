package coin.collections

object Playground {
  def main(args: Array[String]): Unit = {
    val map = Map.empty[Int, Int]

    val result = (1 until(10)).foldLeft(map)((acc, el) => acc + ((el, el)))

    println(result)
  }
}
