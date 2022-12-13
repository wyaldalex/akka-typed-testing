package com.tudux

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object ScalaTypedTesting  extends App {

  trait SimpleThing
  case object EatChocolate extends SimpleThing
  case object WashDishes extends SimpleThing
  case object LearnAkka extends SimpleThing

  val emotionalMutableActor: Behavior[SimpleThing] = Behaviors.setup { context =>
    var happiness = 0

    Behaviors.receiveMessage {
      case EatChocolate =>
        happiness += 1
        Behaviors.same
        context.log.info(s"Eating chocolate getting a dossage of dopamine! current happiness: $happiness")
        Behaviors.same
      case WashDishes =>
        happiness += -2
        Behaviors.same
        context.log.info(s"Washing Dishes... current happiness: $happiness")
        Behaviors.same
      case LearnAkka =>
        happiness += 10
        Behaviors.same
        context.log.info(s"Learning Akka! current happiness: $happiness")
        Behaviors.same
      case _ =>
        happiness += 10
        Behaviors.same
        context.log.info(s"Huuu????")
        Behaviors.same
    }
  }

  def emotionalFunctionalActor(happiness: Int = 0): Behavior[SimpleThing] = Behaviors.receive { (context, message) =>
    message match {
      case EatChocolate =>
        context.log.info(s"Eating chocolate getting a dossage of dopamine! current happiness: $happiness")
        emotionalFunctionalActor(happiness + 1)
      case WashDishes =>
        context.log.info(s"Washing Dishes... current happiness: $happiness")
        emotionalFunctionalActor(happiness - 2)
      case LearnAkka =>
        context.log.info(s"Learning Akka! current happiness: $happiness")
        emotionalFunctionalActor(happiness + 5)
      case _ =>
        context.log.info(s"Huuu????")
        emotionalFunctionalActor(happiness)
    }
  }

  val emotionalActorSystem = ActorSystem(emotionalMutableActor,"EmotionalSystem")
  val emotionalActorSystem2 = ActorSystem(emotionalFunctionalActor(),"EmotionalFunctionalSystem")

  emotionalActorSystem ! EatChocolate
  emotionalActorSystem ! EatChocolate
  emotionalActorSystem ! WashDishes
  emotionalActorSystem ! LearnAkka

  emotionalActorSystem2 ! EatChocolate
  emotionalActorSystem2 ! EatChocolate
  emotionalActorSystem2 ! WashDishes
  emotionalActorSystem2 ! LearnAkka
  emotionalActorSystem2 ! EatChocolate

  //emotionalActorSystem ! 131 // this one dont compiles as now it has type checking and an Int message has not been defined


}
