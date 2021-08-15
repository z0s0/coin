package coin.atomics


class Reference {
  class Counter {
    private var value = 0

    def inc = value += 1
    def get = value
  }

//  private final val catsRuntime = cats.effect.unsafe.implicits.global
//  private final val zioPlatform = zio.internal.Platform
//    .makeDefault(1024)
//    .withReportFailure(_ => ())
//    .withTracing(zio.internal.Tracing.disabled)
//  private final val zioRuntime = zio.Runtime.unsafeFromLayer(zio.ZEnv.live, zioPlatform)

  def testCatsEffectRef() = {}

  def testZIORef() = {}

  def testVolatileVar() = {}

  def testRawAtomic() = {}
}
