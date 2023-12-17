# How to play Aqua Bird

## System Setup
1. To run the program, you want to use an IDE like Eclipse that allows easy use of JavaFX libraries. Download Eclipse [here](https://www.eclipse.org/downloads/).

2. Once you have your desired IDE which can run JavaFX on your system, download JavaFX onto your laptop. Download JavaFX [here](https://gluonhq.com/products/javafx/).
	- Download the accurate version according to your system type and Java version.
	- Store the downloaded javafx folder in a place you remember.

3. On the IDE, create a library for JavaFX and add .jar files from the javafx folder download.
	- On Eclipse, this looks like: Window > Preferences > Java > BuildPath > User Libraries >
          						   New > Name “JavaFX[YourVersionNumber]” > Select it and “Add External Jars” > add the .jar files.

4. Once you have cloned the Team 7: Aqua Bird program from GitHub, add the JavaFx user library you just created to the program.
	- On Eclipse, this looks like: Right click on project > Build Path > Configure Build Path >
								   Click on ClassPath > Add Library > User Library > add the library you just created.

5. While adding the JavaFX library, also add a JRE system library to the project's ModulePath.
	- To do this on Eclipse: ModulePath > JRE System Library > Workspace Default

## Features of the game
1. The game emulates a combination of 2 popular bird-centered games: Flappy Bird and Angry Birds. The bird, called 'Aqua Bird' due to our beach + ocean theme, needs to pass through different obstacles and can also gain bonuses through the game.

2. The background of the game scene changes periodically.

3. The game can be played by constantly clicking on the Start Game button, that enables the bird to fly. 

4. The bird intially has a total of 3 lives which changes throughout game play.

5. There are 3 difficulty levels the game can be played with: Easy, Medium, and Hard. The level should be chosen at the start of a game. The different levels alter different game features by making it more complex to pass through obstacles successfully. 

6. The bird passes through a set of pipes of varied length. The bird gains points based on the pipes passed through.

7. Some of these bottom halves of these pipes have normal pearls or sparkling clams on them which gives the bird a bonus: a normal white pearl increases the score, and a sparkling clam allows the bird to enter autopilot mode which doesn't require any game play from the user.

8. At random, there are octopus's that fall from the upper half of the pipes. These octopus's can eat the clam if they come in contact with it, and the bird cannot get the bonus from that clam anymore.

9. When the bird makes contact with a pipe, the floor of the game or an octupus, a life is lost. Points are lost when a clam is eated by an octupus.
	- When a collision is made, the bird bounces back off the collided object and returns to a position where it can start flying from again. 
	- When a life is lost and the bird still has more than 0 lives, the score is kept the same so that the user can track their progress throughout their 3 bird lives. 

10. When 3 lives are lost, a Game Over message pops up after which the game can be played again with any difficulty level. The games and lives are reset when the game restarts.

11. A legend of information about game objects can be seen constantly throughout game play on the right-hand side of the game panel.






