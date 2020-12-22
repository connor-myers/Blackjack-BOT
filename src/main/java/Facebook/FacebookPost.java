package Facebook;

import java.awt.image.BufferedImage;

public class FacebookPost {

    private String textContent = "";
    private BufferedImage imageContent;

    /**
     * Returns the String contained in Managers.FacebookPost
     * @return
     * The String contained in Managers.FacebookPost
     */
    public String getTextContent(){
        return this.textContent;
    }

    /**
     * Returns the BufferedImage contained in Managers.FacebookPost
     * @return
     * The BufferedImage contained in Managers.FacebookPost
     */
    public BufferedImage getImageContent(){
        return this.imageContent;
    }

    /**
     * Adds more text to the Managers.FacebookPost's textContent
     * @param textToAdd
     * The String you want to add to textContent
     */
    public void addToText(String textToAdd) {
        this.textContent = this.textContent + "\n" + textToAdd + "\n";
    }

    public void setTextContent(String text){
        this.textContent = text;
    }

    /**
     * Set the imageContent of Managers.FacebookPost object to specific BufferedImage
     * @param image
     * BufferedImage that you want to post.
     */
    public void setImageContent(BufferedImage image){
        this.imageContent = image;
    }
}