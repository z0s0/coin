package coin.classes

import org.openjdk.jmh.annotations.Benchmark

/*
 [info] ChimneyVsManual.testChimneyConversion  thrpt   25   6350291,180 ±  64056,884  ops/s
 [info] ChimneyVsManual.testManualConversion   thrpt   25  23020050,638 ± 908941,943  ops/s
 ————————————————————————————————————————
 [info] ChimneyVsManual.testDirectMappingChimney  thrpt   25  148923239,959 ± 10541540,561  ops/s
 [info] ChimneyVsManual.testDirectMappingManual   thrpt   25  124219061,084 ±  7219104,044  ops/s
*/

object ChimneyVsManual {
  final val p1 = Person(
    firstName = "Viktor",
    lastName = "Petrov",
    age = 44,
    authData = AuthData(password = "blabla12", username = "vik12"),
    address = Address("Thailand", "Bangkok", "Thonglor", 55),
    orders = List(Order(33, List("apple", "laptop")))
  )

  final val p2 = Person(
    firstName = "Frank",
    lastName = "Lampord",
    age = 46,
    authData = AuthData(password = "blabla334", username = "frankl"),
    address = Address("England", "London", "Somewhere", 53),
    orders = List(
      Order(100, List("Ball1", "Ball2")),
      Order(200, List("Beer"))
    )
  )

  final case class User(
                         fullName: String,
                         age: Int,
                         purchases: List[Order],
                         username: String,
                         address: Address
                       )

  final case class Person(
                           firstName: String,
                           lastName: String,
                           age: Int,
                           authData: AuthData,
                           address: Address,
                           orders: List[Order]
                         )

  final case class PersonCopy(
                               firstName: String,
                               lastName: String,
                               age: Int,
                               authData: AuthData,
                               address: Address,
                               orders: List[Order]
                             )

  final case class AuthData(
                             username: String,
                             password: String
                           )

  final case class Address(country: String, city: String, street: String, houseNum: Int)

  final case class Order(total: Int, products: List[String])
}

class ChimneyVsManual {

  import ChimneyVsManual._
  import io.scalaland.chimney.dsl._

  @Benchmark
  def testChimneyConversion() = {
    p1.into[User]
      .withFieldComputed(_.fullName, p => p.firstName + " " + p.lastName)
      .withFieldComputed(_.age, _.age + 10)
      .withFieldRenamed(_.orders, _.purchases)
      .withFieldComputed(_.username, _.authData.username)
      .transform

    p2.into[User]
      .withFieldComputed(_.fullName, p => p.firstName + " " + p.lastName)
      .withFieldRenamed(_.orders, _.purchases)
      .withFieldComputed(_.username, _.authData.username)
      .withFieldComputed(_.address, _.address.copy(country = "Russia"))
      .transform
  }

  @Benchmark
  def testManualConversion() = {
    User(
      fullName = p1.firstName + " " + p1.lastName,
      age = p1.age + 10,
      purchases = p1.orders,
      username = p1.authData.username,
      address = p1.address
    )

    User(
      fullName = p2.firstName + " " + p2.lastName,
      age = p2.age,
      purchases = p2.orders,
      username = p2.authData.username,
      address = p2.address.copy(country = "Russia")
    )
  }

  @Benchmark
  def testDirectMappingManual() =
    PersonCopy(
      firstName = p1.firstName,
      lastName = p1.lastName,
      address = p1.address,
      authData = p1.authData,
      orders = p1.orders,
      age = p1.age
    )

  @Benchmark
  def testDirectMappingChimney() =
    p1.transformInto[PersonCopy]
}
