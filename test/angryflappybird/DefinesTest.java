package angryflappybird;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;


class DefinesTest {
    
    private Defines defines;
    
    
    /**
     * This needs to happen in order for the JavaFX objects to initialize properly before testing
     */
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {
      });
    }
    
    @BeforeEach
    void setUp() throws Exception {
        
        
        defines = new Defines();
        
    }

    @Test
    void testDefines() {
        assertNotNull(defines);

        // Check the default values
        assertEquals(600, defines.APP_HEIGHT);
        assertEquals(600, defines.APP_WIDTH);
        assertEquals(570, defines.SCENE_HEIGHT);
        assertEquals(400, defines.SCENE_WIDTH);
        assertEquals(40, defines.BIRD_WIDTH);
        assertEquals(40, defines.BIRD_HEIGHT);
        assertEquals(70, defines.BIRD_POS_X);
        assertEquals(200, defines.BIRD_POS_Y);
        assertEquals(300000000, defines.BIRD_DROP_TIME);
        assertEquals(150, defines.BIRD_DROP_VEL);
        assertEquals(0.2, defines.PIG_YSPEED);
        assertEquals(400, defines.BIRD_BOUNCE_VEL);
        assertEquals(1000000000, defines.BIRD_BOUNCE_TIME);
        assertEquals(-30, defines.BIRD_FLY_VEL);
        assertEquals(4, defines.BIRD_IMG_LEN);
        assertEquals(5, defines.BIRD_IMG_PERIOD);
        assertEquals(400, defines.FLOOR_WIDTH);
        assertEquals(100, defines.FLOOR_HEIGHT);
        assertEquals(2, defines.FLOOR_COUNT);
        assertEquals(240, defines.PIPE_INITIAL_Y_POS);
        assertEquals(550, defines.PIPE_HEIGHT_GAP);
        assertEquals(200, defines.DOWN_PIPE_Y_RANGE);
        assertEquals(70, defines.PIPE_WIDTH);
        assertEquals(335, defines.PIPE_HEIGHT);
        assertEquals(2, defines.PIPE_COUNT);
        assertEquals(350, defines.PIPE_HOR_GAP);
        assertEquals(50, defines.EGG_WIDTH);
        assertEquals(50, defines.EGG_HEIGHT);
        assertEquals(2, defines.EGG_COUNT);
        assertEquals(60, defines.PIG_WIDTH);
        assertEquals(60, defines.PIG_HEIGHT);
        assertEquals(2, defines.PIG_COUNT);
        assertEquals(190, defines.EGG_INITIAL_Y_POS);
        assertEquals(3, defines.INITIAL_LIVES);
        assertEquals(5, defines.SCENE_SHIFT_TIME);
        assertEquals(-0.4, defines.SCENE_SHIFT_INCR);
        assertEquals(1.0 / 1000000000.0, defines.NANOSEC_TO_SEC);
        assertEquals(1000000000.0, defines.SEC_TO_NANOSEC);
        assertEquals(0.1, defines.TRANSITION_TIME);
        assertEquals(2, defines.TRANSITION_CYCLE);
        assertEquals("Angry Flappy Bird", defines.STAGE_TITLE);
        assertNotNull(defines.IMAGE_DIR);
        assertEquals("../resources/images/", defines.IMAGE_DIR);
        assertNotNull(defines.IMAGE_FILES);
        assertEquals(13, defines.IMAGE_FILES.length);
        assertNotNull(defines.IMVIEW);
        assertEquals(13, defines.IMVIEW.size());
        assertNotNull(defines.IMAGE);
        assertEquals(13, defines.IMAGE.size());
        assertNotNull(defines.startButton);
        assertNotNull(defines.levels);
        assertNotNull(defines.whiteEgg);
        assertNotNull(defines.goldenEgg);
        assertNotNull(defines.pig);
        assertEquals("Start Game", defines.startButton.getText());
        assertEquals(3, defines.levels.getItems().size());
        assertEquals("Easy", defines.levels.getValue());
        assertEquals("Bonus Points", defines.whiteEgg.getText());
        assertEquals("Lets you snooze", defines.goldenEgg.getText());
        assertEquals("Avoid pigs", defines.pig.getText());
    }


    @Test
    void testPathImage() {
        String imagePath = defines.pathImage("background");
        assertNotNull(imagePath);
        assertTrue(imagePath.endsWith("background.png"));
    }

    @Test
    void testResizeImage() {
        Image resizedImage = defines.resizeImage("floor", 400, 100);
        assertNotNull(resizedImage);
        assertEquals(400, resizedImage.getWidth());
        assertEquals(100, resizedImage.getHeight());
    }
}