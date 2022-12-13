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

  val emotionalActorSystem = ActorSystem(emotionalMutableActor,"EmotionalSysmte")

  emotionalActorSystem ! EatChocolate
  emotionalActorSystem ! EatChocolate
  emotionalActorSystem ! WashDishes
  emotionalActorSystem ! LearnAkka

  //emotionalActorSystem ! 131 // this one dont compiles as now it has type checking and an Int message has not been defined


}
