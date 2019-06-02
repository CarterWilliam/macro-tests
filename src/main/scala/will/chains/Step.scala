package will.chains

import cats.Monad

abstract class Step[F[_] : Monad, T] {
  def run(): F[T]
}

abstract class ChainableStep[F[_] : Monad, A, B](implicit aStep: Step[F, A])
  extends Step[F, B] {

  def apply(a: A): F[B]

  override def run(): F[B] = {
    implicitly[Monad[F]].flatMap(aStep.run())(apply)
  }
}
