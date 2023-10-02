package ch.makery.address.model

import scala.collection.mutable.ListBuffer

// abstract class that is used to represent a character object within the game
abstract class Character {

  var cards: ListBuffer[Card] = ListBuffer[Card]()

  def addCard(card: Card):Unit ={ // used to add a single card to the character's current cards

    cards += card
  }

  def clearCards():Unit ={ // used to clear all the cards the character possesses
    cards.clear()
  }

  def showHand(): ListBuffer[Card] = { // used to retrieve the cards the character has

    cards
  }

  def calculateHand(): Int = { // used to calculate the sum of card values of the character

    val aceValue = if (cards.length > 2) 1 else 11
    val faceValue = 10
    cards.map {
      case Card("ace", _) => aceValue
      case Card("king", _) => faceValue
      case Card("queen", _) => faceValue
      case Card("jack", _) => faceValue
      case Card("10", _) => 10
      case Card("9", _) => 9
      case Card("8", _) => 8
      case Card("7", _) => 7
      case Card("6", _) => 6
      case Card("5", _) => 5
      case Card("4", _) => 4
      case Card("3", _) => 3
      case Card("2", _) => 2
    }.sum
  }



}
