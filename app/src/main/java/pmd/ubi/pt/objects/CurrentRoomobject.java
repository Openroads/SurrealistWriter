package pmd.ubi.pt.objects;

import java.io.Serializable;

/**
 * Created by Piotr on 01-Jan-17.
 */

public class CurrentRoomobject implements Serializable
{
    private String name;
    private int maxNumbersOfPlayers;
    private int numberOfRounds;
    private int numberOfCharacters;
    private int numberOfCurrentPlayers;
    private String hashedPassword;
    private long adminId;
    private int gameID;

    public CurrentRoomobject(String name, int maxNumbersOfPlayers, int numberOfRounds, int numberOfCharacters, int numberOfCurrentPlayers, String hashpassword, long adminId, int gameID)
    {
        this.name = name;
        this.maxNumbersOfPlayers = maxNumbersOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.numberOfCharacters = numberOfCharacters;
        this.numberOfCurrentPlayers = numberOfCurrentPlayers;
        this.hashedPassword = hashpassword;
        this.adminId = adminId;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumbersOfPlayers() {
        return maxNumbersOfPlayers;
    }

    public void setMaxNumbersOfPlayers(int maxNumbersOfPlayers) {
        this.maxNumbersOfPlayers = maxNumbersOfPlayers;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public void setNumberOfCharacters(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }

    public int getNumberOfCurrentPlayers() {
        return numberOfCurrentPlayers;
    }

    public void setNumberOfCurrentPlayers(int numberOfCurrentPlayers) {
        this.numberOfCurrentPlayers = numberOfCurrentPlayers;
    }
}
