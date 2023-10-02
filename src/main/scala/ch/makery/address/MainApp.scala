package ch.makery.address
import ch.makery.address.model.Game
import ch.makery.address.view.PlayerEditNameDialogController
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}




object MainApp extends JFXApp { // used as the driver class to run the game


  val game = new Game()
  val player = game.player

  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Blackjack"
    icons += new Image("file:src/main/resources/ch/makery/address/view/images/jack-of-spades.png")
    scene = new Scene {
      stylesheets += getClass.getResource("view/DarkTheme.css").toString
      root = roots
    }
  }
  // actions for display person overview window
  def showGameOverview() = { // displays the GameOverview page
    val resource = getClass.getResource("view/GameOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }


  def showWelcome() = { // displays the welcome page

    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  // call to display PersonOverview when app start

  def showPlayerEditDialog(): Boolean = { // displays the player edit name dialog page
    val resource = getClass.getResourceAsStream("view/PlayerEditNameDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PlayerEditNameDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        title = "Edit Name"
        icons += new Image("file:src/main/resources/ch/makery/address/view/images/jack-of-spades.png")
        stylesheets += getClass.getResource("view/DarkTheme.css").toString
        root = roots2
      }
    }
    control.dialogStage = dialog
    dialog.showAndWait()
    control.okClicked
  }


  showWelcome() // displays the welcome page when the main app is run


}

