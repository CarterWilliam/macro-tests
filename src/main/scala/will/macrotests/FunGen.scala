package will.macrotests

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context


trait Fun[A, B] {
  def execute(a: A): B
}

object FunGen {

  val config = Config("8")

  def generate[A, B](f: A => B): Fun[A, B] = macro generateImpl[A, B]

  def generateImpl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: Context)(f: c.Expr[A => B]): c.Tree = {
    import c.universe._

    val aType = weakTypeOf[A]
    val bType = weakTypeOf[B]

    q"""
      new Fun[$aType, $bType] {
        def execute(a: $aType): $bType = $f.apply(a)
      }
     """
  }

  def generate[Input, Parameter: FromConfigurable, Output](f: (Input, Parameter) => Output): Fun[Input, Output] =
    macro generateWithConfigImpl[Input, Parameter, Output]

  def generateWithConfigImpl[Input: c.WeakTypeTag, Parameter: c.WeakTypeTag, Output: c.WeakTypeTag](c: Context)(f: c.Expr[(Input, Parameter) => Output])
                                                                                                   (fromConfigurable: c.Expr[FromConfigurable[Parameter]]): c.Tree = {
    import c.universe._

    val I = weakTypeOf[Input]
    val P = weakTypeOf[Parameter]
    val O = weakTypeOf[Output]

    val result = q"""
      new Fun[$I, $O] {
        def p: $P = ${reify(config)}.get[$P]()($fromConfigurable)
        def execute(a: $I): $O = $f.apply(a, p)
      }
     """

    result
  }


}
