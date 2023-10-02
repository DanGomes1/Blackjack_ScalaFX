package ch.makery.address.model

import scala.collection.mutable.ListBuffer

// card deck class that is used to define a deck of cards
class CardDeck {
   val suits = List("hearts", "diamonds", "clubs", "spades")
   val ranks = List("ace", "2", "3", "4", "5", "6",
    "7", "8", "9", "10", "jack", "queen", "king")
   var cards = new ListBuffer[Card]()

  def setDeck(): Unit = { // used to initialize the deck of cards
    cards.clear()
    cards ++= (for {
      suit <- suits
      rank <- ranks
    } yield Card(rank, suit))

  }


  def shuffle(): Unit = { // used to shuffle the cards so that the placement is random
    cards = scala.util.Random.shuffle(cards)
  }

  def draw(): Option[Card] = { // used to remove a single card from the top of the deck
    if (cards.isEmpty) {
      None
    } else {
      Some(cards.remove(0))
    }
  }
}
