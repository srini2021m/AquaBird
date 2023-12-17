package angryflappybird;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//The Application layer
//Team 7 Aeronauts
//Kalki, Jennifer and Ruby

public class AngryFlappyBird extends Application {
  
 private boolean autopilotMode = false;
 private boolean isDay = true;
 private boolean isBacking = false;
 
 private Defines DEF = new Defines();
 Random random = new Random();
 
   // time related attributes
 private long clickTime, startTime, elapsedTime,lastBackgroundChangeTime,backingTime, autopilotStartTime;
 private AnimationTimer timer;
 private MediaPlayer mainMusic;
 private MediaPlayer eggSounds;
 private MediaPlayer damaged;
 private MediaPlayer APSound;
 private MediaPlayer pipePass;
 
 
   // game components
  //public Text GameOverText;
 
 
 private ImageView gameOverImage;
 public Text autopilotText;
 public Text livesText;  //update lives count
 public Text scoresText; //update score
 public int lives;  //keep counts of lives
 public int scores; //keep scores
 
 
  //variable for backing feature
  private boolean backing = false;
 private boolean pigCollision = false;
 private boolean pipeCollision = false;
 
 
 private Sprite bird;
 private ArrayList<Sprite> floors;
 private ArrayList<Sprite> upPipes;
 private ArrayList<Sprite> downPipes;
 private ArrayList<Sprite> pigs;
 private ArrayList<Sprite> whiteEggs;
 private ArrayList<Sprite> goldenEggs;
 private ArrayList<Sprite> Eggs;
 
 
 //private ArrayList<Sprite> Eggs;
  // game flags
 private boolean CLICKED, GAME_START, GAME_OVER;
 
  // scene graphs
 private Group gameScene;     // the left half of the scene
 private VBox gameControl;    // the right half of the GUI (control)
 private GraphicsContext gc;  
 
  /**
   * the mandatory main method
   * @param args
   */
 public static void main(String[] args) {
     launch(args);
 }
 
 /**
  * the start method sets the Stage layer
  */
 @Override
 public void start(Stage primaryStage) throws Exception {
    
    
   //initialize music components
    
     mainMusic = new MediaPlayer(new Media(getClass().getResource("../resources/images/BlueWorld.mp3").toString()));
    
     eggSounds = new MediaPlayer(new Media(getClass().getResource("../resources/images/CollectedSFX.mp3").toString()));
    
     damaged = new MediaPlayer(new Media(getClass().getResource("../resources/images/Hit.mp3").toString()));
    
     APSound = new MediaPlayer(new Media(getClass().getResource("../resources/images/Aquarium.mp3").toString()));
    
     pipePass = new MediaPlayer(new Media(getClass().getResource("../resources/images/pipeSFX.mp3").toString()));
    
  
     // initialize scene graphs and UIs
     resetGameControl();    // resets the gameControl
     resetGameScene(true);  // resets the gameScene
  
     HBox root = new HBox();
     HBox.setMargin(gameScene, new Insets(0,0,0,15));
     root.getChildren().add(gameScene);
     root.getChildren().add(gameControl);
  
     // add scene graphs to scene
     Scene scene = new Scene(root, DEF.APP_WIDTH, DEF.APP_HEIGHT);
  
     // finalize and show the stage
     primaryStage.setScene(scene);
     primaryStage.setTitle(DEF.STAGE_TITLE);
     primaryStage.setResizable(false);
     primaryStage.show();
 }
 /**
  * changes game play detail based on difficulty level selected
  * @param difficulty level in string form "Easy" "Medium" "Hard"
  */
 private void changeDifficultyLevel(String difficulty) {
   
     switch (difficulty) {
         case "Easy":
             break;
         case "Medium":
             DEF.BIRD_DROP_VEL = 250; //increase bird drop velocity
             break;
         case "Hard":
             DEF.PIG_YSPEED=0.4; //increase speed of pigs falling down the pipes
             DEF.BIRD_DROP_VEL = 250; //increase bird drop velocity
             break;
     }
 }
 
/**
 * resets the game controls and sets up various UI elements for gameplay initiation and difficulty settings.
 */
 private void resetGameControl() {
   
     DEF.startButton.setOnMouseClicked(this::mouseClickHandler); //setup game play through the Start Game button
   
     //setup difficulty level selection through levels dropdown checkbox
     DEF.levels.setOnAction(event -> changeDifficultyLevel(DEF.levels.getValue()));
     //initialize image views for information legend
     ImageView whiteEggImageView = new ImageView(DEF.IMAGE.get("whiteEgg"));
     ImageView goldenEggImageView = new ImageView(DEF.IMAGE.get("goldenEgg"));
     ImageView pigImageView = new ImageView(DEF.IMAGE.get("pig"));
   
     double imageSize = 50.0;
     whiteEggImageView.setFitWidth(imageSize);
     whiteEggImageView.setFitHeight(imageSize);
     goldenEggImageView.setFitWidth(imageSize);
     goldenEggImageView.setFitHeight(imageSize);
 
     pigImageView.setFitWidth(imageSize);
     pigImageView.setFitHeight(imageSize);
     
     VBox whiteEggBox = new VBox(5);
     whiteEggBox.getChildren().addAll(whiteEggImageView, DEF.whiteEgg);
     
     VBox goldenEggBox = new VBox(5);
     goldenEggBox.getChildren().addAll(goldenEggImageView, DEF.goldenEgg);
     
     VBox pigBox = new VBox(5);
     pigBox.getChildren().addAll(pigImageView, DEF.pig);
     
     // Create the main VBox to hold all the image/text pairs
     VBox imageAndTextVBox = new VBox(10);
     imageAndTextVBox.getChildren().addAll(whiteEggBox, goldenEggBox, pigBox);
     
     gameControl = new VBox(10);
     gameControl.getChildren().addAll(DEF.startButton);
     gameControl.getChildren().addAll(DEF.levels);
     gameControl.getChildren().add(imageAndTextVBox);
        
 }
 /**
  * method to handle mouse clicks from user
  */
 public void mouseClickHandler(MouseEvent e) {
     if (GAME_OVER) {
        
         resetGameScene(false);
    
     }
     else if (GAME_START){
         clickTime = System.nanoTime();
      
         gameOverImage.setVisible(false);
         gameScene.getChildren().remove(gameOverImage);
       
    
  }
     GAME_START = true;
     CLICKED = true;
 }
 
 /**
  * resets the game scene by initializing game variables, game objects, UI elements, and media players.
  */
 private void resetGameScene(boolean firstEntry) {
  
     //reset game variables
     CLICKED = false;
     GAME_OVER = false;
     GAME_START = false;
     floors = new ArrayList<>();
     upPipes = new ArrayList<>();
     downPipes = new ArrayList<>();
     pigs = new ArrayList<>();
     whiteEggs = new ArrayList<>();
     goldenEggs = new ArrayList<>();
     Eggs = new ArrayList<>();
  
  
     if(firstEntry) {
      // create two canvases
         Canvas canvas = new Canvas(DEF.SCENE_WIDTH, DEF.SCENE_HEIGHT);
         gc = canvas.getGraphicsContext2D();
         // Create day background
         ImageView dayBackground = DEF.IMVIEW.get("background");
         // create the game scene
         gameScene = new Group();
         gameScene.getChildren().addAll(dayBackground, canvas);
     }
   
     //reset game objects
     initializeLivesText();
     initializeGameOverImage();
     initializeAutopilotText();
     initializeScoresText();
     initializeFloors();
     initializePipes();
     initializeEggs();
     initializePigs();
     initializeBird();
     
     startTime = System.nanoTime();
     timer = new MyTimer();
     timer.start();
   
     if (gameOverImage != null) {
         gameOverImage.setVisible(false);
         gameScene.getChildren().remove(gameOverImage);
       
     }
    
  // Stop and reset all MediaPlayers
     stopAndResetMediaPlayers();
   
 }
 /**
  * Method to stop and reset all MediaPlayers
  */
private void stopAndResetMediaPlayers() {
  mainMusic.stop();
  mainMusic.seek(Duration.ZERO);
  eggSounds.stop();
  eggSounds.seek(Duration.ZERO);
  damaged.stop();
  damaged.seek(Duration.ZERO);
  APSound.stop();
  APSound.seek(Duration.ZERO);
  pipePass.stop();
  pipePass.seek(Duration.ZERO);
}
 /**
  * creates and initialize floor of the game
  */
 private void initializeFloors() {
     for (int i = 0; i < DEF.FLOOR_COUNT; i++) {
         int posX = i * DEF.FLOOR_WIDTH;
         int posY = DEF.SCENE_HEIGHT - DEF.FLOOR_HEIGHT;
         Sprite floor = new Sprite(posX, posY, DEF.IMAGE.get("floor"));
         floor.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         floor.render(gc);
         floors.add(floor);
     }
 }
 /**
  * creates and initializes upper and bottom pipes
  */
 private void initializePipes() {
     for (int i = 0; i < DEF.PIPE_COUNT; i++) {
         double xPos = i * DEF.PIPE_HOR_GAP + (DEF.SCENE_WIDTH / 2) + (DEF.SCENE_WIDTH / 4);
         double downPipeYPos = DEF.PIPE_INITIAL_Y_POS + DEF.DOWN_PIPE_Y_RANGE * Math.random();
         double upPipeYPos = downPipeYPos - DEF.PIPE_HEIGHT_GAP;
         Sprite upPipe = new Sprite(xPos, upPipeYPos, DEF.IMAGE.get("upPipe"));
         Sprite downPipe = new Sprite(xPos, downPipeYPos, DEF.IMAGE.get("downPipe"));
       
         upPipe.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         downPipe.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         upPipe.render(gc);
         downPipe.render(gc);
         downPipes.add(downPipe);
         upPipes.add(upPipe);
         
     }
 }
 /**
  * creates and initializes white and golden eggs on bottom pipes
  */
 private void initializeEggs() {
     for (int i = 0; i < DEF.EGG_COUNT; i++) {
      //setup x and y positions based on pipes values as eggs are placed on top of bottom pipes
         double xPos = i * DEF.PIPE_HOR_GAP + (DEF.SCENE_WIDTH / 2) + (DEF.SCENE_WIDTH / 4);
         double yPos = DEF.PIPE_INITIAL_Y_POS + DEF.DOWN_PIPE_Y_RANGE * Math.random();
       
         //set up white and golden eggs so that they don't both appear on same pipe
         Sprite whiteEgg = new Sprite(xPos + 1000, yPos - DEF.EGG_HEIGHT + 1000,DEF.IMAGE.get("whiteEgg"));
         whiteEgg.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         whiteEggs.add(whiteEgg);
       
         Sprite goldenEgg = new Sprite(xPos + 10000, yPos - DEF.EGG_HEIGHT + 1000,DEF.IMAGE.get("goldenEgg"));
         goldenEgg.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         goldenEggs.add(goldenEgg);
       
         Eggs.add(whiteEgg);
         Eggs.add(goldenEgg);
         whiteEgg.render(gc);
         goldenEgg.render(gc);
     }
 }
  /**
  * creates and initializes pig objects
  */
 private void initializePigs() {
     for (int i = 0; i < DEF.PIG_COUNT; i++) {
    //setup x and y positions based on pipes values as pigs fall from the upper pipes
         double xPos = i * DEF.PIPE_HOR_GAP + (DEF.SCENE_WIDTH / 2) + (DEF.SCENE_WIDTH / 4);
         double yPos = DEF.PIPE_INITIAL_Y_POS + DEF.DOWN_PIPE_Y_RANGE * Math.random();
         Sprite pig = new Sprite(xPos + 1000, yPos - DEF.PIG_HEIGHT + 5000, DEF.IMAGE.get("pig"));
       
         pig.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
         pig.render(gc);        
         pigs.add(pig);
     }
 }
 
  /**
  * creates and initializes the bird object
  */
 private void initializeBird() {
     bird = new Sprite(DEF.BIRD_POS_X, DEF.BIRD_POS_Y, DEF.IMAGE.get("flappy0"));
     bird.render(gc);
 }
 
  /**
  * initializes the lives counter and adds it to the game scene
  */
 private void initializeLivesText() {
     lives = DEF.INITIAL_LIVES;
     if (livesText != null) {
         gameScene.getChildren().remove(livesText);
     }
     livesText = new Text("Lives left: " + lives);
     livesText.setStyle("-fx-font-size: 20; -fx-fill: white; -fx-font-weight: bold;-fx-font-family: Georgia;");
     livesText.setTranslateX(DEF.SCENE_WIDTH - 120);
     livesText.setTranslateY(DEF.SCENE_HEIGHT - 10);
     gameScene.getChildren().add(livesText);
 }
 
 /**
  * initializes the scores counter and adds it to the game scene
  */
 private void initializeScoresText() {
     scores = 0;
     if (livesText != null) {
         gameScene.getChildren().remove(scoresText);
     }
     scoresText = new Text("Score: " + scores);
     scoresText.setStyle("-fx-font-size: 20; -fx-fill: white; -fx-font-weight: bold;-fx-font-family: Georgia;");
     scoresText.setTranslateX(DEF.SCENE_WIDTH - 100);
     scoresText.setTranslateY(20);
     gameScene.getChildren().add(scoresText);
 }
 
 /**
  * Update scoresText method updates the score text depending on event that is happening within the game
  */
  public void updateScoreText() {
      scoresText.setText("Score: " + scores);
  }
 
  /**
  * initializes the autopilot tracker and adds it to the game scene
  */
 private void initializeAutopilotText() {
     autopilotText = new Text("Autopilot Mode On");
     autopilotText.setStyle("-fx-font-size: 20; -fx-fill: Yellow; -fx-font-weight:bold ;-fx-font-family: Georgia;");
     autopilotText.setTranslateX(10);
     autopilotText.setTranslateY(20);
     autopilotText.setVisible(false); // Initially, hide the text
     gameScene.getChildren().add(autopilotText);
   
 }
 
 /**
  * initializes the game over text and adds it to the game scene
  */
  private void initializeGameOverImage() {
      if (gameOverImage == null) {
          gameOverImage = new ImageView(DEF.IMAGE.get("gameover"));
          gameOverImage.setFitWidth(200);
          gameOverImage.setFitHeight(100);
          // Set the position of the image
          gameOverImage.setTranslateX(DEF.SCENE_WIDTH / 2 - 100);
          gameOverImage.setTranslateY(DEF.SCENE_HEIGHT / 2);
          // Make the image invisible initially
          gameOverImage.setVisible(false);
          gameScene.getChildren().add(gameOverImage);
      }
  }
      //timer stuff
     class MyTimer extends AnimationTimer {
      
         int counter = 0;
      
         @Override
         public void handle(long now) {       
             // time keeping
             elapsedTime = now - startTime;
             startTime = now;
          
             // clear current scene
             gc.clearRect(0, 0, DEF.SCENE_WIDTH, DEF.SCENE_HEIGHT);
          
             if (GAME_START) {
                  mainMusic.play();
                
                  // step1a: update floor
                 moveFloor();
               
                 //step1b: update pipes, pigs and eggs
                 moveDownPipesAndEggs();
                 moveUpPipesAndPigs();
               
                 // step2: update BIRD
                 moveBird();
                 checkCollision();
              
                 // step3: change backgrounds
                 long currentTime = System.nanoTime();
               
                 if ((currentTime - lastBackgroundChangeTime)/1000000000 >= 10) {
                     changeBackground();
                     lastBackgroundChangeTime = currentTime;
                   
                }
             
             }
             // end the game when blob hit stuffs
                if (GAME_OVER) {
                    showHitEffect();
                    for (Sprite floor : floors) {
                        floor.setVelocity(0, 0);
                    }
                    // Add the image to the game scene
                    gameScene.getChildren().add(gameOverImage);
                    initializeGameOverImage();
                     mainMusic.stop();
                    timer.stop();
                }
          
         }
      
         /**
          * changes the background from day to night
          */
         private void changeBackground() {
          
             // Create day and night backgrounds
             ImageView dayBackground = DEF.IMVIEW.get("background");
             ImageView nightBackground = DEF.IMVIEW.get("nightsky");
                if (isDay) {
                    // Change to night background
                    gameScene.getChildren().set(0, dayBackground);
                } else {
                    // Change to day background
                    gameScene.getChildren().set(0, nightBackground);
                }
                isDay = !isDay;
            }
              
         /**
          * moves the floor to change the game scene
          */
         private void moveFloor() {
             for(int i=0; i<DEF.FLOOR_COUNT; i++) {
                 if (floors.get(i).getPositionX() <= -DEF.FLOOR_WIDTH) {
                     double nextX = floors.get((i+1)%DEF.FLOOR_COUNT).getPositionX() + DEF.FLOOR_WIDTH;
                     double nextY = DEF.SCENE_HEIGHT - DEF.FLOOR_HEIGHT;
                     floors.get(i).setPositionXY(nextX, nextY);
                 }
                 floors.get(i).render(gc);
                 floors.get(i).update(DEF.SCENE_SHIFT_TIME);
             }
          }
       
         /**
          * moves the upper half of the pipes horizontally along the scene
          * moves the pigs down the upper half pipes
          * randomizes the appearance of pigs at upper pipes
          */
         private void moveUpPipesAndPigs() {
             for (int i = 0; i < DEF.PIPE_COUNT; i++) {
                 double nextX;
                 double nextY;
               
                 // Check if the up pipe has moved off the left side of the screen
                 if (upPipes.get(i).getPositionX() <= -DEF.FLOOR_WIDTH) {
                     // Move the up pipe to the next position on the right
                     nextX = upPipes.get((i + 1) % DEF.PIPE_COUNT).getPositionX() + DEF.FLOOR_WIDTH;
                     nextY = downPipes.get(i).getPositionY() - DEF.PIPE_HEIGHT_GAP;
                     upPipes.get(i).setPositionXY(nextX, nextY);
                   
                  // Increase the score for the first pipe as well
                     scores++;
                 // Update the scores text
                     updateScoreText();
                   
                     double rando = random.nextDouble();
                     if (rando <= 0.6) {
                             // random appearance of pigs at pipes
                             pigs.get(i).setPositionXY(nextX, 5);
                             //the pigs move down the up pipes and also horizontally along the scene
                             pigs.get(i).setVelocity(-0.4, DEF.PIG_YSPEED);
                     }
               
                   
                  // Check if the bird has passed through this up pipe and also the corresponding bottom pipe
                     if (!bird.intersectsSprite(upPipes.get(i))&& !bird.intersectsSprite(downPipes.get(i))){
                         pipePass.play(); 
                         // Increase the score
                         scores++;
                         // Update the scores text
                         updateScoreText();
                         pipePass.stop();
                  
                     }
                 }
                 upPipes.get(i).render(gc);
                 upPipes.get(i).update(DEF.SCENE_SHIFT_TIME);
                 pigs.get(i).render(gc);
                 pigs.get(i).update(DEF.SCENE_SHIFT_TIME);
               
             }
         }
  
         /**
          * moves the bottom half of the pipes horizontally along the scene
          * moves the eggs alond with the bottom pipes
          * randomizes the appearance of eggs
          */
          private void moveDownPipesAndEggs() {
              for (int i = 0; i < DEF.PIPE_COUNT; i++) {
                  double nextX;
                  double nextY;
                
                  // Check if the pipe has moved off the left side of the screen
                  if (downPipes.get(i).getPositionX() <= -DEF.FLOOR_WIDTH) {
                      // Move the pipe to the next position on the right
                      nextX = downPipes.get((i + 1) % DEF.PIPE_COUNT).getPositionX() + DEF.FLOOR_WIDTH;
                      nextY = DEF.PIPE_INITIAL_Y_POS + DEF.DOWN_PIPE_Y_RANGE * random.nextDouble();
                      // Set the positions for downPipe, whiteEgg, and goldenEgg
                      downPipes.get(i).setPositionXY(nextX, nextY + DEF.EGG_HEIGHT);
                    
                      // Generate a random number to decide whether to show an egg
                      // use number to also decide between white and golden eggs
                      double rando = random.nextDouble();
                      if (rando <= 0.6) {
                          if (rando <= 0.4) {
                              whiteEggs.get(i).setPositionXY(nextX + 10, nextY + 5);
                          } else {
                              goldenEggs.get(i).setPositionXY(nextX + 10, nextY + 5);
                          }
                      }
                  }
                
                  whiteEggs.get(i).render(gc);
                  whiteEggs.get(i).update(DEF.SCENE_SHIFT_TIME);
                  goldenEggs.get(i).render(gc);
                  goldenEggs.get(i).update(DEF.SCENE_SHIFT_TIME);
                  downPipes.get(i).render(gc);
                  downPipes.get(i).update(DEF.SCENE_SHIFT_TIME);
              }
          }
         /**
           * enters autopilot mode when applicable
           * animates the bird graphic as the Start Game button is clicked
           * if the button is unclicked, implements dropping of the bird
           */
          private void moveBird() {
           
              if (autopilotMode) {
                  autopilotLogic();
                  mainMusic.stop();
                  }
              else if (!autopilotMode && !backing) {
                 long diffTime = System.nanoTime() - clickTime;
              
                 // bird flies upward with animation
                 if (CLICKED && diffTime <= DEF.BIRD_DROP_TIME) {
                  
                     int imageIndex = Math.floorDiv(counter++, DEF.BIRD_IMG_PERIOD);
                     imageIndex = Math.floorMod(imageIndex, DEF.BIRD_IMG_LEN);
                     bird.setImage(DEF.IMAGE.get("flappy"+String.valueOf(imageIndex)));
                     bird.setVelocity(0, DEF.BIRD_FLY_VEL);
                 }
               
                 // bird drops after a period of time without button click
                 else {
                     bird.setImage(DEF.IMAGE.get("flappy1"));
                     bird.setVelocity(0, DEF.BIRD_DROP_VEL);
                     CLICKED = false;
                 }
              }
             
              else if (backing) {
                  isbacking();
              }
             
             
              bird.update(elapsedTime * DEF.NANOSEC_TO_SEC);
              bird.render(gc);
            
        
          }
         
          /**
           * makes the bird bounce backwards when there is a collision 
           */
          private void isbacking() {
              long backingDifference = System.nanoTime() - backingTime;
              mainMusic.stop();
              if (backingDifference <= 4000000000L) {
                  if (backingDifference <= 2000000000L) {
                      // Move the bird left during the first 3 seconds of backing
                      bird.setVelocity(-DEF.SCENE_SHIFT_INCR - 200, 0);
                  } else {
                      // Move the bird down during the next 3 seconds of backing
                      bird.setVelocity(0, 500);
                  }
                  stopComponents();
              } else {
                  // Reset the backing-related flags
                  backing = false;
                  pigCollision = false;
                  pipeCollision = false;
                  restartComponents();
                  // Reset the bird's position and velocity
                  bird.setVelocity(0, DEF.BIRD_FLY_VEL);
                  bird.setPositionXY(0, DEF.SCENE_WIDTH/2);
                  clickTime = System.nanoTime();
              }
          }
          /**
           * checks for collision between primarily of birds and game objects
           * collision is not tracked when bird is in autopilot
           */
          public void checkCollision() {
           
              for (Sprite floor : floors) {
                 
                  if (!pigCollision && !pipeCollision) {
                   // Check collision with floor
                      if (bird.intersectsSprite(floor)) {
                          damaged.play();
                        
                          LivesReduction();
                          GAME_OVER = GAME_OVER || bird.intersectsSprite(floor);
                      }
                  }
              }
          
              if (!autopilotMode && !backing) {
                  // Check collision with upPipes and downPipes
                  for (Sprite upPipe : upPipes) {
                      if (bird.intersectsSprite(upPipe)) {
                         
                          backingTime = System.nanoTime();
                         
                          backing = true;
                          pipeCollision = true;
                   
                          damaged.play();                        
                          LivesReduction();
                          GAME_OVER = GAME_OVER || bird.intersectsSprite(upPipe);
                      }
                  }
                  for (Sprite downPipe : downPipes) {
                      if (bird.intersectsSprite(downPipe)) {
                          backingTime = System.nanoTime();
                         
                          backing = true;
                          pipeCollision = true;  
                         
                          damaged.play();
                         
                          LivesReduction();
                          GAME_OVER = GAME_OVER || bird.intersectsSprite(downPipe);
                      }
                  }
                  // Check collision with white eggs
                  for (Sprite whiteEgg : whiteEggs) {
                      if (bird.intersectsSprite(whiteEgg)) {
                          handleWhiteEggCollision(whiteEgg);
                          eggSounds.play();

                      }
                  }
                  // Check collision with golden eggs
                  for (Sprite goldenEgg : goldenEggs) {
                      if (bird.intersectsSprite(goldenEgg)) {
                          handleGoldenEggCollision(goldenEgg);
                          eggSounds.play();
                      }
                  }
                  //Check for collision cases with pigs
                  for (Sprite pig:pigs) {
                      if(bird.intersectsSprite(pig)) {
                         
                          backingTime = System.nanoTime();
                         
                          backing = true;
                          pigCollision = true;
                          pig.setPositionXY(1000, 1000);    
                          damaged.play();
                         
                         
                          LivesReduction();
                      }
                      //Check for when a pig "eats" an egg
                      for (Sprite egg:Eggs) {
                          if (egg.intersectsSprite(pig)) {
                              handlePigEggInteraction(egg);
                          }
                      }
                  } 
          }
   
          }
         
         
            
  
  
          /**
           * enter autpilot mode when bird interacts with a golden egg
           * remove golden egg from visible game scene
           * @param goldenEgg interacted with
           */
          private void handleGoldenEggCollision(Sprite goldenEgg) {
              goldenEgg.setPositionXY(1000, 1000);
              // Start autopilot mode
              autopilotMode = true;
              autopilotStartTime = System.nanoTime();
          }
  
    /**
           * enter autopilot mode for six seconds
           * bird flies at a constant speed in a straight live
           */
          private void autopilotLogic() {
              // Calculate the elapsed time since autopilot started
              long autopilotElapsedTime = System.nanoTime() - autopilotStartTime;
              // Fly upward for the first 6 seconds
              if (autopilotElapsedTime <= 6 * DEF.SEC_TO_NANOSEC) {
                  //give autopilot feature
                  bird.setVelocity(0,0);
                   APSound.play();
                  autopilotText.setVisible(true); // Show autopilot text
              } else {
                  // After 6 seconds, back to normal
                  bird.setVelocity(0, DEF.BIRD_FLY_VEL);
                  autopilotText.setVisible(false); // Hide autopilot text
                  autopilotMode = false;
                   APSound.stop();
              }
          }
  
  /**
           * increase points when bird interacts with a white egg
           * remove white egg from visible game scene
           * @param whiteEgg interacted with
           */
          private void handleWhiteEggCollision(Sprite whiteEgg) {
              scores += 6;
              eggSounds.play();
              eggSounds.stop();
              updateScoreText();
              whiteEgg.setPositionXY(1000, 1000);             
              }
  
        
          /**
           * decrease score when a pig "eats" an egg
           * remove egg from visible game scene
           * @param egg the pig interacts with
           */
          private void handlePigEggInteraction(Sprite egg) {
              scores -= 4;
              updateScoreText();
              egg.setPositionXY(1000, 1000);
          }
        
              
        
          /**
           * reduces the number of lives
           * when 3 lives have been lost, display a game over message on the scene
           * if the game is not yet over, reset the position of the bird to position before collision
           */
          private void LivesReduction() {
              lives--;
             
           // Ensure lives don't go below 0
              lives = Math.max(0, lives);
              // Update lives text
                 updateLivesText();
             
                 if (lives == 0) {
                     GAME_OVER = true;
                  // Make the image visible
                     gameOverImage.setVisible(true);
                    
                    
                     backing = false;
                     pigCollision =  false;
                     pipeCollision = false;
                 
                  
                 } else {
                     resetPosition();
                 }
                      
             }
       
          /**
           * resets the position of the bird after experiencing a collision event 
           */
          private void resetPosition() {
              // Reset the bird position and velocity after hitting the floor
              GAME_OVER = false;
              gameOverImage.setVisible(false);
              bird.setPositionXY(DEF.BIRD_POS_X, DEF.BIRD_POS_Y);
              eggSounds.stop();
              bird.setVelocity(0, 0);
              clickTime = System.nanoTime();
              bird.render(gc);
          }
                 
        
          /**
           * stop all the components when the game is over (3 lives have been lost)
           */
          private void stopComponents() {
              for (Sprite floor : floors) {
                  floor.setVelocity(0, 0);        
              }
              for (Sprite downPipe : downPipes) {
                  downPipe.setVelocity(0, 0);        
              }
              for (Sprite upPipe : upPipes) {
                  upPipe.setVelocity(0, 0);        
              }
              for (Sprite whiteEgg : whiteEggs) {
                  whiteEgg.setVelocity(0, 0);        
              }
               for (Sprite goldenEgg : goldenEggs) {
                  goldenEgg.setVelocity(0, 0);        
              }
               for (Sprite pig : pigs) {
                   pig.setVelocity(0, 0);        
               }
          }
         
          /**
           * restart all the components when the game is replayed
           */
          private void restartComponents() {
//               System.out.println("components have been restarted");
              for (Sprite floor : floors) {
                  floor.setVelocity(DEF.SCENE_SHIFT_INCR, 0);       
              }
              for (Sprite downPipe : downPipes) {
                  downPipe.setVelocity(DEF.SCENE_SHIFT_INCR, 0);        
              }
              for (Sprite upPipe : upPipes) {
                  upPipe.setVelocity(DEF.SCENE_SHIFT_INCR, 0);         
              }
              for (Sprite whiteEgg : whiteEggs) {
                  whiteEgg.setVelocity(DEF.SCENE_SHIFT_INCR, 0);        
              }
              for (Sprite goldenEgg : goldenEggs) {
                  goldenEgg.setVelocity(DEF.SCENE_SHIFT_INCR, 0);        
              }
              for (Sprite pig : pigs) {
                  pig.setVelocity(DEF.SCENE_SHIFT_INCR, 0);        
              }
          }
        
          
          /**
           *  method updates the livesText to help user how many lives they have left
           */
          public void updateLivesText() {
              // Update the text property of the existing Text node
              livesText.setText("Lives left: " + lives);
          }
       
        
       
          private void showHitEffect() {
             ParallelTransition parallelTransition = new ParallelTransition();
             FadeTransition fadeTransition = new FadeTransition(Duration.seconds(DEF.TRANSITION_TIME), gameScene);
             fadeTransition.setToValue(0);
             fadeTransition.setCycleCount(DEF.TRANSITION_CYCLE);
             fadeTransition.setAutoReverse(true);
             parallelTransition.getChildren().add(fadeTransition);
             parallelTransition.play();
          }
       
     } // End of MyTimer class
 } // End of AngryFlappyBird Class



