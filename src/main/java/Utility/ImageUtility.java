package Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtility {
    /**
     * Converts BufferedImage to equivalent byte[] format
     * Reference: https://stackoverflow.com/questions/15414259/java-bufferedimage-to-byte-array-and-back
     *
     * @param image The BufferedImage to be converted to byte[]
     * @return image in byte[] format
     * @author Nikolay Kuznetsov
     */
    public static byte[] BufferedImagetoBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
