package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafxml.core.macros.sfxml

@sfxml
class WelcomeController(){ // controller class that is used to control interactions in the Welcome page

  def handleStart(): Unit = { // used to handle the event of user clicking the start game button

    MainApp.showPlayerEditDialog()
    MainApp.showGameOverview()
  }

  def handleExit(): Unit = { // used to handle the event of the user clicking the exit button
    System.exit(0)
  }

  def handleShowInstructions(action: ActionEvent):Unit = { // used to display the game instructions when the instructions button is pressed

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

}