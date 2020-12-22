package Graphics;


import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Scene {
    private static final int SCALE_MULTIPLIER = 1;
    private final int width = 2048;
    private final int height = 1536;
    private final BufferedImage stage;
    private final Graphics2D g;

    public Scene() {
        stage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) stage.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect ( 0, 0, stage.getWidth(), stage.getHeight());
    }

    public void addSprite(Sprite sprite, int x, int y) {
        g.drawImage(sprite.getImage(),
                x,
                y,
                sprite.getImage().getWidth(null),
                sprite.getImage().getHeight(null),
                null);
    }

    public void saveImage(String name) {
        File outputFile = new File(name + ".png");
        try {
            ImageIO.write(stage, "png", outputFile);
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
