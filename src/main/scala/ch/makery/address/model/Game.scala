package ch.makery.address.model

// Game class that is used to represent the entire blackjack game
class Game {

  private val deck = new CardDeck()
  val dealer = new Dealer()
  val player = new Player()
  var mode = "Easy"
  var isPlayerTurn = true
  var isGameOver = false
  var isGameTie = false
  var hasPlayerWon = false
  var gameOverInfo = ""

  def selectEasyMode(): Unit = { // sets the game mode to easy

    mode = "Easy"
  }

  def selectHardMode(): Unit = { // sets the game mode to hard

    mode = "Hard"
  }

  def play(): Unit = { // used to initialize and start the game

    deck.setDeck()
    isPlayerTurn = true
    isGameOver = false
    isGameTie = false
    hasPlayerWon = false

    deck.shuffle()
    player.addCard(deck.draw().get)
    dealer.addCard(deck.draw().get)
    player.addCard(deck.draw().get)
    dealer.addCard(deck.draw().get)

  }

  def playerHit(): Unit = { // allows players to draw a single card to their current cards

    if (isPlayerTurn) {

      if (player.calculateHand() < 21 && player.cards.length < 5) {

        player.addCard(deck.draw().get)

        if (player.calculateHand() == 21) {
          playerStand()
        }

        else if (player.calculateHand() > 21) {

          checkWinner()
        }
      }
    }
    if (player.cards.length == 5 || player.calculateHand() == 21) playerStand()
  }


  def playerStand(): Unit = { // used to allow players to stick to their current cards. Also makes the dealer draw cards
    isPlayerTurn = false
    while (dealerShouldHit() && dealer.cards.length < 5) {
      dealer.addCard(deck.draw().get)
    }

    checkWinner()
  }

  def showScore(): Int = { // used to retrieve the players score

    player.score
  }

  def checkWinner(): String = { // used to check the winner of the game and update the game over information

    while(!isGameOver) {

      if (player.calculateHand() > 21) {

        gameOverInfo = "You bust, Dealer wins"
      }
      else if (dealer.calculateHand() > 21) {

        player.updateScore()
        hasPlayerWon = true
        gameOverInfo = "Dealer busts, You Win"
      }
      else if (player.calculateHand() == 21 && dealer.calculateHand() == 21) {

        isGameTie = true
        gameOverInfo = "Both you and the dealer have a Blackjack, its a Tie"
      }
      else if (player.calculateHand() == 21) {

        player.updateScore()
        hasPlayerWon = true
        gameOverInfo = "You have a Blackjack, You Win"
      }
      else if (dealer.calculateHand() == 21) {

        gameOverInfo = "Dealer has a Blackjack, You Lose"
      }
      else if (player.calculateHand() > dealer.calculateHand()) {

        player.updateScore()
        hasPlayerWon = true
        gameOverInfo = "Your hand has a higher value, You Win"
      }
      else if (dealer.calculateHand() > player.calculateHand()) {
        gameOverInfo = "Dealer's hand has a higher value, You Lose"
      }
      else {

        isGameTie = true
        gameOverInfo = "You have the same hand value as the dealer, its a Tie"
      }

      isGameOver = true

    }
    gameOverInfo
  }


  def dealerShouldHit(): Boolean = { // determines whether the dealer should take more cards or not. Depends on the game mode

    if (mode.equals("easy")) {

      dealer.calculateHand() < 16
    }
    else {

      dealer.calculateHand() < 18
    }
  }

  def restartGame(): Unit = { // allows players to restart the game by resetting the cards and initializing the game

      player.clearCards()
      dealer.clearCards()
      play()

  }


}
