package Facebook;

import Utility.ImageUtility;
import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

import java.util.logging.Logger;

public class FacebookManager {
    private String pageAccessToken;
    private String pageId;
    private FacebookClient fbClient;

    public FacebookManager(String pageAccessToken, String pageId) {
        this.pageAccessToken = pageAccessToken;
        this.pageId = pageId;
        this.fbClient = createClient();
    }

    private FacebookClient createClient() {
        FacebookClient fbClient = null;
        try {
            fbClient = new DefaultFacebookClient(this.pageAccessToken, Version.VERSION_3_1);
            fbClient.fetchObject("me", User.class);
            fbClient.fetchObject(this.pageId, Page.class);
        } catch (FacebookException ex) {
            ex.printStackTrace(System.err);
        }

        return fbClient;
    }

    public void postImageWithText(FacebookPost postContent) {
        byte[] imageAsBytes = ImageUtility.BufferedImagetoBytes(postContent.getImageContent());
        FacebookType photo = fbClient.publish(this.pageId + "/photos",
                FacebookType.class,
                BinaryAttachment.with("photo",
                        imageAsBytes,
                        "image/jpg"),
                Parameter.with("message",
                        postContent.getTextContent()));
    }
}