package will.macrotests

trait FromConfigurable[T] {
  def fromConfig(config: String): T
}

object FromConfigurable {
  implicit val intFromConfig = new FromConfigurable[Int] {
    override def fromConfig(config: String): Int = config.toInt
  }
}


case class Config(private val content: String) {
  def get[T: FromConfigurable](): T = {
    implicitly[FromConfigurable[T]].fromConfig(content)
  }
}