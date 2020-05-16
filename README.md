Game Of Three - Player 1
========================

Code Setup:
----------
* Application is built in Spring boot framework and Maven.
* Java 8 is used to create the project.
* To import the application in IDE, simply import it as a Java project and set it as a Maven project.


Introduction:
------------
* To start the player, go to file StartPlayer.java and run it.
* it will start the application on port 8082 which is defined in application.yml file.
* Now the Player1 of the game is up and running.
* To start the game, GOTPlayer2 also needs to start the application and initiate the game by sending some number.
* Player 1 does not need to provide any input for the game. The number will be sent by player 2.
* When player 2 will start the game, Player 1 can see the message on terminal/console that Player 2 is connected.
* When the game begins, both players will be sending and receiving numbers automatically. They can see it on console/Terminal.
* At the end, result will shown to both the players, who has won or lost.