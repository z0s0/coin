package coin.byte_buffers

import org.openjdk.jmh.annotations._
import sun.misc.Unsafe

import java.nio.{ByteBuffer, ByteOrder}

object UnsafeBaseline {

}
@State(Scope.Benchmark)
class UnsafeBaseline {
  val reflectField = classOf[Unsafe].getDeclaredField("theUnsafe")
  reflectField.setAccessible(true)

  final val UNSAFE = reflectField.get(null).asInstanceOf[Unsafe]
  final val addr = UNSAFE.allocateMemory(40)

  @Benchmark
  def testUnsafeGet() = {
    val addr = UNSAFE.allocateMemory(100)
    var sum = 0
    var i = 0

    while (i < 10) {
      sum += UNSAFE.getInt(addr + i * 4)
      i += 1
    }

    sum
  }

  final val bb = ByteBuffer.allocateDirect(40)

  @Benchmark
  def testDirectBuffer() = {
    var sum = 0

    while (bb.hasRemaining) {
      sum += bb.getInt()
    }

    bb.clear()
    sum
  }

  final val anotherBB = ByteBuffer.allocateDirect(40)

  @Benchmark
  def testDirectBufferWithoutPointerShift() = {
    var sum = 0
    var i = 0

    while (i < 10) {
      sum += anotherBB.getInt(i * 4)
      i += 1
    }

    sum
  }

  final val eaBB = ByteBuffer.allocateDirect(40).order(ByteOrder.nativeOrder())

  @Benchmark
  def testEndianAwareDirectBuffer() = {
    var sum = 0
    var i = 0

    while (i < 10) {
      sum += eaBB.getInt(i * 4)
      i += 1
    }

    sum
  }
}
