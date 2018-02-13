package BusinessClasses;

/**
 * Created by logic-designs on 4/27/2017.
 */

public class GetPlayerData {
    String namePlayer;
    String townPlayer;
    String ratingBarPlayer;
    String ratingPlayer;
    String positionPlayer;
    String speedPlayer;
    String skillPlayer;
    String shootingPlayer;
    String PlayerImage;
    String PlayerId;
    String teamId;
    int inMyTeam;
    String rateable;
    String canchangePosition="";

    public String getCanchangePosition() {
        return canchangePosition;
    }

    public void setCanchangePosition(String canchangePosition) {
        this.canchangePosition = canchangePosition;
    }

    public String getRateable() {
        return rateable;
    }

    public void setRateable(String rateable) {
        this.rateable = rateable;
    }

    public int getInMyTeam() {
        return inMyTeam;
    }

    public void setInMyTeam(int inMyTeam) {
        this.inMyTeam = inMyTeam;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public String getPlayerImage() {
        return PlayerImage;
    }

    public void setPlayerImage(String playerImage) {
        PlayerImage = playerImage;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getTownPlayer() {
        return townPlayer;
    }

    public void setTownPlayer(String townPlayer) {
        this.townPlayer = townPlayer;
    }

    public String getRatingBarPlayer() {
        return ratingBarPlayer;
    }

    public void setRatingBarPlayer(String ratingBarPlayer) {
        this.ratingBarPlayer = ratingBarPlayer;
    }

    public String getRatingPlayer() {
        return ratingPlayer;
    }

    public void setRatingPlayer(String ratingPlayer) {
        this.ratingPlayer = ratingPlayer;
    }

    public String getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(String positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    public String getSpeedPlayer() {
        return speedPlayer;
    }

    public void setSpeedPlayer(String speedPlayer) {
        this.speedPlayer = speedPlayer;
    }

    public String getSkillPlayer() {
        return skillPlayer;
    }

    public void setSkillPlayer(String skillPlayer) {
        this.skillPlayer = skillPlayer;
    }

    public String getShootingPlayer() {
        return shootingPlayer;
    }

    public void setShootingPlayer(String shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }
}
