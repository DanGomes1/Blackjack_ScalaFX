package ch.makery.address.model

// concrete class that is used to represent a player object. It extends from the abstract character class
class Player(var name: Option[String] = None) extends Character{

  var score = 0

  def getName: Option[String] = { // gets the name of the player as an Option of String
    name
  }

  def setName(newName: String): Unit = { // sets the name of the player
    name = Some(newName)
  }

  def updateScore() = { // used to increase the player score by 1 everytime they win a round

    score += 1
  }

}
