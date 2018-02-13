package BusinessClasses;

import android.graphics.Bitmap;

/**
 * Created by logic-designs on 4/23/2017.
 */

public class RegisterPlaygroundData  {

    private int playgroundId;
    private String playgroundName=" ";
    private String playgroundLocation=" ";
    private String playgroundRank=" ";
    private String playgroundImage;
    private String clubName;


    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }




    public int getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(int playgroundId) {
        this.playgroundId = playgroundId;
    }

    public String getPlaygroundImage() {
        return playgroundImage;
    }

    public void setPlaygroundImage(String playgroundImage) {
        this.playgroundImage = playgroundImage;
    }




    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getPlaygroundLocation() {
        return playgroundLocation;
    }

    public void setPlaygroundLocation(String playgroundLocation) {
        this.playgroundLocation = playgroundLocation;
    }

    public String getPlaygroundRank() {
        return playgroundRank;
    }

    public void setPlaygroundRank(String playgroundRank) {
        this.playgroundRank = playgroundRank;
    }
}
