package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    BufferedImage image;

    public Sprite(String imagePath) {
        this.image = loadImage(imagePath);
    }

    public static BufferedImage loadImage(String spritePath) {
        try {
            return ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // if all else fails, use error sprite
        return loadImage("src/main/resources/Images/error.png");
    }
}