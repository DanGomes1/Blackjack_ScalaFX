package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class PlayerEditNameDialogController( // controller class that is used to control the interactions within the edit name dialog popup

                                   private val  nameField : TextField,

                                 )
{
      var dialogStage : Stage  = null
      var okClicked            = false
      var player = MainApp.player




  def handleOk(action :ActionEvent){ // used to handle the event when user clicks the ok button

    if (isInputValid()) {

      MainApp.player.setName(nameField.text.value)
    }

    okClicked = true
    dialogStage.close()
  }

  def handleCancel(action :ActionEvent) { // used to handle the event when user clicks the cancel button
    dialogStage.close();
  }
  def nullChecking (x : String) = x == null || x.isEmpty // used to determine if the textfield is empty or not

  def isInputValid() : Boolean = { // used to check whether the user input is valid and then display the appropriate warning message
    var warningMessage = ""

    if (nullChecking(nameField.text.value)) {

    if (player.getName.isDefined) warningMessage = "No changes detected, Your current name will be used"
    else warningMessage = "A default name of Player will be used"
  }
    if (warningMessage.isEmpty) {
      true;
    } else {
      // Show the error message.
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(dialogStage)
        title = "Warning"
        headerText = "No input found"
        contentText = warningMessage
      }.showAndWait()

      false;
    }
   }
}
