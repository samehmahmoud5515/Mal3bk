package BusinessClasses;

import android.graphics.Bitmap;

/**
 * Created by logic-designs on 5/1/2017.
 */

public class MainPageData {
    String playgroundName;

    String description;
    String town;
    String playGroundImage;
    String price,startOfOffer,endOfOffer,playgroundType;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartOfOffer() {
        return startOfOffer;
    }

    public void setStartOfOffer(String startOfOffer) {
        this.startOfOffer = startOfOffer;
    }

    public String getEndOfOffer() {
        return endOfOffer;
    }

    public void setEndOfOffer(String endOfOffer) {
        this.endOfOffer = endOfOffer;
    }

    public String getPlaygroundType() {
        return playgroundType;
    }

    public void setPlaygroundType(String playgroundType) {
        this.playgroundType = playgroundType;
    }

    String  playgroundID;
    public String getPlaygroundID() {
        return playgroundID;
    }

    public void setPlaygroundID(String playgroundID) {
        this.playgroundID = playgroundID;
    }


    public String getPlayGroundImage() {
        return playGroundImage;
    }

    public void setPlayGroundImage(String playGroundImage) {
        this.playGroundImage = playGroundImage;
    }

    public String getplaygroundName() {
        return playgroundName;
    }

    public void setplaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
