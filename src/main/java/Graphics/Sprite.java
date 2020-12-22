package Graphics;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private final BufferedImage image;

    public Sprite(String imagePath, double scaleFactor) {
//        BufferedImage tmp = Scalr.resize(sprite.getImage(),
//                Scalr.Method.BALANCED,
//                (int) Math.floor(sprite.getImage().getWidth() * scaleFactor),
//                (int) Math.floor(sprite.getImage().getHeight() * scaleFactor));
        BufferedImage tmp = loadImage(imagePath);
        this.image = Scalr.resize(tmp, Scalr.Method.BALANCED, (int) Math.floor(tmp.getWidth() * scaleFactor),
                (int) Math.floor(tmp.getHeight() * scaleFactor));
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

    public BufferedImage getImage() {
        return image;
    }
}