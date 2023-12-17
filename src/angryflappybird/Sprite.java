package angryflappybird;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//Team 7 Aeronauts
//Kalki, Jennifer and Ruby
//Class to create game objects

public class Sprite {  
	
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;

    /**
     * constructor for sprite that initializes it at (0,0)
     */
    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
    }
    
    /**
     * constructor for sprite that initializes an object at a certain position
     * @param pX x-value for position
     * @param pY y-value for position
     * @param image to be used to create Sprite object
     */
    public Sprite(double pX, double pY, Image image) {
    	setPositionXY(pX, pY);
        setImage(image);
        this.velocityX = 0;
        this.velocityY = 0;
    }
    
    /**
     * Setter for image
     * @param image to be set
     */
    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    /**
     * Getter for image
     * @return image
     */
    public Image getImage() {
        return image;
           
    }

    /**
     * Setter for position
     * @param positionX x position value 
     * @param positionY y position value
     */
    public void setPositionXY(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Getter for x-value of position
     * @return x-value
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Getter for y-value of position
     * @return y-value
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Setter for velocity
     * @param velocityX x-axis velocity
     * @param velocityY y-axis velocity
     */
    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * Add to current velocuty
     * @param x x-value to be added
     * @param y y-value to be added
     */
    public void addVelocity(double x, double y) {
        this.velocityX += x;
        this.velocityY += y;
    }

    /**
     * Getter for x-axis velocity
     * @return x-axis velocity
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Getter for y-axis velocity
     * @return y-axis velocity
     */
    public double getVelocityY() {
        return velocityY;
    }
    
    /**
     * Setter for width
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Setter for height
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }
    
    /**
     * Getter for widht
     * @return width
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Getter for height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * render image on graphics context based on x and y position values
     * @param gc graphics context
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    /**
     * Creates a new rectangular boundary corresponding to the sprite's position and dimensions
     * @return rectangular boundary
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    /**
     * Checks for whether the boundaries of two sprite objects intersect each other
     * @param s sprite object
     * @return boolean value for intersection
     */
    public boolean intersectsSprite(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    /**
     * Updates the position of a moving sprite with new positions
     * @param time to calculte x and y positions based on velocity
     */
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
}
