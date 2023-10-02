package ch.makery.address.view
import ch.makery.address.MainApp
import ch.makery.address.model.{Dealer, Player}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml

@sfxml
class GameOverviewController( // controller class that is used to control interactions within the game overview page
                                private var playerNameLabel: Label,
                                private val playerCard1: ImageView,
                                private val playerCard2: ImageView,
                                private val playerCard3: ImageView,
                                private val playerCard4: ImageView,
                                private val playerCard5: ImageView,
                                private val dealerCard1: ImageView,
                                private val dealerCard2: ImageView,
                                private val dealerCard3: ImageView,
                                private val dealerCard4: ImageView,
                                private val dealerCard5: ImageView,
                                private val playerScoreLabel: Label,
                                private val playerCardValueLabel: Label,
                                private val dealerCardValueLabel: Label,
                                private val gameModeLabel: Label

                              ) {

  val game = MainApp.game


  def startGame(): Unit ={ // used to start the game when the method is called


    game.play()
    showCards(game.player)
    showCards(game.dealer)
    displayPlayerScore(game.player)
    showCardValue(game.player)
    showCardValue(game.dealer)
    showPlayerName()
    showGameMode()

  }

  def showPlayerName(): Unit ={ // used to update the corresponding labels when the user changes their name

    if (game.player.getName.isDefined){
      playerNameLabel.text = s"${game.player.getName.get}'s Cards:"
    }
    else{
      playerNameLabel.text = "Player's Cards"
    }

  }

  def changePlayerName(action: ActionEvent): Unit ={ // used to change the user's name

    MainApp.showPlayerEditDialog()
    playerNameLabel.text = MainApp.player.getName.getOrElse("")
    showPlayerName()
    showCardValue(MainApp.player)

  }

  def showGameMode(): Unit = { // used to update the game mode label to display the current game mode

    gameModeLabel.text = s"Game Mode: ${game.mode}"
  }


  def displayPlayerScore(player: Player): Unit = { // used to display the player's current score

    playerScoreLabel.text = s"Player Score: ${player.score}"
  }

  def showCardValue(player: Player): Unit = { // used to show the card value of the player. Displays nothing on hard mode

    if (game.mode.equals("Easy")) {
      player.getName match {
        case Some(name) =>

          playerCardValueLabel.text = s"$name's Card Value: ${player.calculateHand()}"
        case None =>
          playerCardValueLabel.text = s"Player's Card Value: ${player.calculateHand()}"
      }
    } else {
      playerCardValueLabel.text = ""
    }
  }

  def showCardValue(dealer: Dealer): Unit = { // used to show the card value of the dealer. Displays nothing on hard mode

    if (game.mode.equals("Easy")){

      if (!game.isGameOver){

        dealerCardValueLabel.text = "Dealer's Card Value: ???"
      }

      else{

        dealerCardValueLabel.text = s"Dealer's Card Value: ${dealer.calculateHand()}"
      }
    }
    else {
      dealerCardValueLabel.text = ""
    }

    }



  def showCards(player: Player): Unit = { // used to display the player's cards as their corresponding images

    val playerCards = Seq(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5)
    for ((imageView, card) <- playerCards.zipAll(player.cards, null, null)) {
      if (card != null) {
        imageView.image = new Image(getClass.getResource(s"/ch/makery/address/view/images/${card.rank}_of_${card.suit}.jpg").toString)
      } else {
        imageView.image = new Image(getClass.getResource("/ch/makery/address/view/images/empty.jpg").toString)
      }
    }
  }

  def showCards(dealer: Dealer): Unit = { // used to display the dealer's cards as their corresponding images

    val dealerCards = Seq(dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5)
    if (game.isGameOver) {
      for ((imageView, card) <- dealerCards.zipAll(dealer.cards, null, null)) {
        if (card != null) {
          imageView.image = new Image(getClass.getResource(s"/ch/makery/address/view/images/${card.rank}_of_${card.suit}.jpg").toString)
        } else {
          imageView.image = new Image(getClass.getResource("/ch/makery/address/view/images/empty.jpg").toString)
        }
      }
    } else {
      for ((imageView, card) <- dealerCards.zipAll(dealer.cards, null, null)) {
        if (card != null) {
          if (imageView == dealerCard1) {
            imageView.image = new Image(getClass.getResource("/ch/makery/address/view/images/back.jpg").toString)
          }
          else {
            imageView.image = new Image(getClass.getResource(s"/ch/makery/address/view/images/${card.rank}_of_${card.suit}.jpg").toString)
          }
        } else {
          imageView.image = new Image(getClass.getResource("/ch/makery/address/view/images/empty.jpg").toString)
        }
      }
    }
  }


  def handleExit(): Unit = { // used to handle the event when user clicks the exit button

     System.exit(0)
  }

  def handleRestart(action: ActionEvent): Unit = { // used to restart the game when user clicks the restart button

   game.restartGame()
    showCards(game.player)
    showCards(game.dealer)
    showCardValue(game.player)
    showCardValue(game.dealer)
    displayPlayerScore(game.player)

  }

  def handleEasyMode(action: ActionEvent): Unit = { // used to handle the event when user click the easy button

    game.selectEasyMode()
    showGameMode()
    showCardValue(game.player)
    showCardValue(game.dealer)
  }

  def handleHardMode(action: ActionEvent): Unit = { // used to handle the event when user clicks the hard button

    game.selectHardMode()
    showGameMode()
    showCardValue(game.player)
    showCardValue(game.dealer)
  }

  def handleShowInstructions(action: ActionEvent): Unit = { // used to display the game instructions when the instructions button is clickec

    val instructionPopup = new Alert(Alert.AlertType.Information) {
      initOwner(MainApp.stage)
      title = "Game Instructions"
      headerText = "How To Play"
      contentText =
        "1. At the start of the game, the player and dealer are given 2 cards each\n\n" +
          "2. The goal of the game is to get a higher card value than the dealer without exceeding 21\n\n" +
          "3. The maximum value that you and the dealer can have is 21\n\n" +
          "4. The value of your cards are as follows:\n\n" +
          "a) Face Cards(Jack, Queen, King) are valued at 10\n\n" +
          "b) Number Cards follow the values on the card\n\n" +
          "c) Ace is valued at 11 if you have only 2 cards but 1 if you have more\n\n" +
          "5. If you wish to take more cards, press the Hit Button\n\n" +
          "6. If you have taken enough cards, press the Stand Button to stick with your cards\n\n" +
          "7. You win if the dealer's card value exceeds 21 or if your card value is higher than his\n\n" +
          "8. You Lose if your card value exceeds 21 or if the dealer has a higher card value than you\n\n" +
          "9. You Draw if your card value is the same as the dealer's\n\n"

    }.showAndWait()

  }

  def handleShowAbout(action: ActionEvent): Unit = { // used to display the game's about page when the About button is clicked

    val aboutPopup = new Alert(Alert.AlertType.Information) {
      initOwner(MainApp.stage)
      title = "About"
      headerText = "About the Creator"
      contentText = "This Simple Blackjack Game was designed by Daniel Alexander Gomes for his final programming assignment"

    }.showAndWait()

  }

  def handleHit(action: ActionEvent): Unit = { // used to handle the event when user clicks on the hit button

    if (game.isPlayerTurn) {
      game.playerHit()
      showCards(game.player)
    }
    showCards(game.player)
    showCards(game.dealer)
    if (game.isGameOver){
      showGameOverAlert()
    }
    showCardValue(game.player)
  }

  def handleStand(action: ActionEvent): Unit = { // used to handle the event when user clicks on the stand buttton

    game.playerStand()
    showCardValue(game.player)
    showCardValue(game.dealer)
    showCards(game.player)
    showCards(game.dealer)
    displayPlayerScore(game.player)
    showGameOverAlert()
  }

  def showGameOverAlert(): Unit = { // used to display the game over alert and information once the game winner has been determined

    showCardValue(game.dealer)
    val alert = new Alert(Alert.AlertType.Information) {
      initOwner(MainApp.stage)
      title = "Game Over"
      if (game.hasPlayerWon) {
        headerText = "Congratulations!"
        contentText = game.gameOverInfo
      } else if (game.isGameTie) {
        headerText = "It's a Tie!"
        contentText = game.gameOverInfo
      } else {
        headerText = "Better Luck Next Time!"
        contentText = game.gameOverInfo
      }
    }showAndWait()

  }

  startGame() // starts the game when the game overview page loads

}
