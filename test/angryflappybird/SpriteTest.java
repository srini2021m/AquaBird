package angryflappybird;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;

import javafx.geometry.Rectangle2D;


class SpriteTest {
    
    private Sprite sprite;
    private Image image;
    
    
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
        sprite = new Sprite();
        image = new Image(getClass().getResource("../resources/images/background.png").toExternalForm());

        
    }

    @Test
    void testSprite() {
        assertNotNull(sprite);
        assertEquals(0, sprite.getPositionX());
        assertEquals(0, sprite.getPositionY());
        assertEquals(0, sprite.getVelocityX());
        assertEquals(0, sprite.getVelocityY());
        assertNull(sprite.getImage());
    }

    @Test
    void testSpriteDoubleDoubleImage() {
        Sprite spriteWithImage = new Sprite(10, 20, image);
        assertNotNull(spriteWithImage);
        assertEquals(10, spriteWithImage.getPositionX());
        assertEquals(20, spriteWithImage.getPositionY());
        assertEquals(0, spriteWithImage.getVelocityX());
        assertEquals(0, spriteWithImage.getVelocityY());
        assertNotNull(spriteWithImage.getImage());
        assertEquals(image, spriteWithImage.getImage());
    }

    @Test
    void testSetImage() {
        sprite.setImage(image);
        assertNotNull(sprite.getImage());
        assertEquals(image, sprite.getImage());
    }

    @Test
    void testGetImage() {
        assertNull(sprite.getImage());
        sprite.setImage(image);
        assertNotNull(sprite.getImage());
        assertEquals(image, sprite.getImage());
    }

    @Test
    void testSetPositionXY() {
        sprite.setPositionXY(30, 40);
        assertEquals(30, sprite.getPositionX());
        assertEquals(40, sprite.getPositionY());
    }

    @Test
    void testGetPositionX() {
        assertEquals(0, sprite.getPositionX());
        sprite.setPositionXY(50, 60);
        assertEquals(50, sprite.getPositionX());
    }

    @Test
    void testGetPositionY() {
        assertEquals(0, sprite.getPositionY());
        sprite.setPositionXY(70, 80);
        assertEquals(80, sprite.getPositionY());
    }

    @Test
    void testSetVelocity() {
        sprite.setVelocity(5, 10);
        assertEquals(5, sprite.getVelocityX());
        assertEquals(10, sprite.getVelocityY());
    }

    @Test
    void testAddVelocity() {
        sprite.setVelocity(2, 3);
        sprite.addVelocity(4, 5);
        assertEquals(6, sprite.getVelocityX());
        assertEquals(8, sprite.getVelocityY());
    }

    @Test
    void testGetVelocityX() {
        assertEquals(0, sprite.getVelocityX());
        sprite.setVelocity(15, 20);
        assertEquals(15, sprite.getVelocityX());
    }

    @Test
    void testGetVelocityY() {
        assertEquals(0, sprite.getVelocityY());
        sprite.setVelocity(25, 30);
        assertEquals(30, sprite.getVelocityY());
    }

    @Test
    void testSetWidth() {
        sprite.setWidth(100);
        assertEquals(100, sprite.getWidth());
    }

    @Test
    void testSetHeight() {
        sprite.setHeight(150);
        assertEquals(150, sprite.getHeight());
    }

    @Test
    void testGetWidth() {
        assertEquals(0, sprite.getWidth());
        sprite.setWidth(200);
        assertEquals(200, sprite.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(0, sprite.getHeight());
        sprite.setHeight(250);
        assertEquals(250, sprite.getHeight());
    }


    @Test
    void testGetBoundary() {
        sprite.setPositionXY(10, 20);
        sprite.setWidth(30);
        sprite.setHeight(40);
        Rectangle2D boundary = sprite.getBoundary();
        assertNotNull(boundary);
        assertEquals(10, boundary.getMinX());
        assertEquals(20, boundary.getMinY());
        assertEquals(30, boundary.getWidth());
        assertEquals(40, boundary.getHeight());
    }

    @Test
    void testIntersectsSprite() {
        Sprite otherSprite = new Sprite(15, 25, image);
        assertFalse(sprite.intersectsSprite(otherSprite));
        sprite.setPositionXY(20, 30);
        sprite.setWidth(50);
        sprite.setHeight(60);
        otherSprite.setPositionXY(45, 55);
        otherSprite.setWidth(70);
        otherSprite.setHeight(80);
        assertTrue(sprite.intersectsSprite(otherSprite));
    }

    @Test
    void testUpdate() {
        sprite.setVelocity(2, 3);
        sprite.update(0.5);
        assertEquals(1, sprite.getPositionX());
        assertEquals(1.5, sprite.getPositionY());
    }

}
