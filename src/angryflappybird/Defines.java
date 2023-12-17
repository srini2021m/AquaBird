package angryflappybird;

import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

//Team 7 Aeronauts
//Kalki, Jennifer and Ruby

public class Defines {
    
    // dimension of the GUI application
    final int APP_HEIGHT = 600;
    final int APP_WIDTH = 600;
    final int SCENE_HEIGHT = 570;
    final int SCENE_WIDTH = 400;

    // coefficients related to the BIRD
    final int BIRD_WIDTH = 40;
    final int BIRD_HEIGHT = 40;
    final int BIRD_POS_X = 70;
    final int BIRD_POS_Y = 200;
    final int BIRD_DROP_TIME = 300000000;   // the elapsed time threshold before the BIRD starts dropping
    int BIRD_DROP_VEL = 150;// the BIRD drop velocity
    
    
    final int BIRD_BOUNCE_VEL = 400;
    final int BIRD_BOUNCE_TIME = 1000000000;
    
    
    final int BIRD_FLY_VEL = -30;
    final int BIRD_IMG_LEN = 4;
    final int BIRD_IMG_PERIOD = 5;
    

    // coefficients related to the floors
    final int FLOOR_WIDTH = 400;
    final int FLOOR_HEIGHT = 100;
    final int FLOOR_COUNT = 2;
    
    // coefficients related to the pipes
    final double PIPE_INITIAL_Y_POS = 240;
    final double PIPE_HEIGHT_GAP = 550;
    final double DOWN_PIPE_Y_RANGE = 200;
    
    final int PIPE_WIDTH = 70;
    final int PIPE_HEIGHT = 335;
    final int PIPE_COUNT = 2;
    
    final int PIPE_HOR_GAP = 350;
    

    // coefficients related to eggs
    final int EGG_WIDTH = 50;
    final int EGG_HEIGHT = 50;
    final int EGG_COUNT = 2;
    //had issues with conversion
    final int EGG_INITIAL_Y_POS = (int) PIPE_INITIAL_Y_POS - EGG_HEIGHT; 
    
    // coefficients related to pigs
    final int PIG_WIDTH = 60;
    final int PIG_HEIGHT = 60;
    final int PIG_COUNT = 2;
    double PIG_YSPEED = 0.2;
    
    //coeeficients related to lives
    final int INITIAL_LIVES = 3;
    
    // coefficients related to time
    final int SCENE_SHIFT_TIME = 5;
    final double SCENE_SHIFT_INCR = -0.4;
    final double NANOSEC_TO_SEC = 1.0 / 1000000000.0;
    final double SEC_TO_NANOSEC = 1000000000.0;
    final double TRANSITION_TIME = 0.1;
    final int TRANSITION_CYCLE = 2;
    
    
    // coefficients related to media display
    final String STAGE_TITLE = "Angry Flappy Bird";

    public final String IMAGE_DIR = "../resources/images/";
    final String[] IMAGE_FILES = {"background","flappy0", "flappy1", "flappy2", "flappy3", "floor","goldenEgg","whiteEgg","pig","nightsky","upPipe","downPipe","gameover"};

    final HashMap<String, ImageView> IMVIEW = new HashMap<String, ImageView>();
    final HashMap<String, Image> IMAGE = new HashMap<String, Image>();
    
    //nodes on the scene graph
    Button startButton;
    Text whiteEgg;
    Text goldenEgg;
    Text pig;
    ComboBox<String> levels;
    
    // constructor
    
    Defines() {
        
        // initialize images
        for(int i=0; i<IMAGE_FILES.length; i++) {
            Image img;
            if (i == 5) {
                img = new Image(pathImage(IMAGE_FILES[i]), FLOOR_WIDTH, FLOOR_HEIGHT, false, false);
            }
            else if (i == 6 || i == 7) {
                img = new Image(pathImage(IMAGE_FILES[i]), EGG_WIDTH, EGG_HEIGHT, false, false);
            }
            else if (i == 8) {
                img = new Image(pathImage(IMAGE_FILES[i]), PIG_WIDTH, PIG_HEIGHT, false, false);
            }
            else if (i == 10 || i == 11) {
                img = new Image(pathImage(IMAGE_FILES[i]), PIPE_WIDTH, PIPE_HEIGHT, false, false);
            }
                                
            else if (i == 1 || i == 2 || i == 3 || i == 4){
                img = new Image(pathImage(IMAGE_FILES[i]), BIRD_WIDTH, BIRD_HEIGHT, false, false);
            }
            else {
                img = new Image(pathImage(IMAGE_FILES[i]), SCENE_WIDTH, SCENE_HEIGHT, false, false);
            }
            IMAGE.put(IMAGE_FILES[i],img);
        }
        
        // initialize image views
        for(int i=0; i<IMAGE_FILES.length; i++) {
            ImageView imgView = new ImageView(IMAGE.get(IMAGE_FILES[i]));
            IMVIEW.put(IMAGE_FILES[i],imgView);
        }
        
        // initialize scene nodes
        startButton = new Button("Go!");

        // initialize scene nodes
        startButton = new Button("Start Game");
        
        levels = new ComboBox<String>();
        levels.getItems().addAll("Easy", "Medium", "Hard");
        levels.setValue("Easy");    whiteEgg = new Text("Bonus Points");
        goldenEgg= new Text("Lets you snooze");
        pig = new Text("Avoid pigs");
    
    }
    
    /**
     * Getter for the path of an image
     * @param filepath
     * @return image path
     */
    public String pathImage(String filepath) {
        String fullpath = getClass().getResource(IMAGE_DIR+filepath+".png").toExternalForm();
        return fullpath;
    }
    
    /**
     * Resizes an image to desires size
     * @param filepath
     * @param width desired
     * @param height desired
     * @return resized image
     */
    public Image resizeImage(String filepath, int width, int height) {
        IMAGE.put(filepath, new Image(pathImage(filepath), width, height, false, false));
        return IMAGE.get(filepath);
    }
}