package pmd.ubi.pt.objects;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class GameUser
{
    private long id;
    private long gameId;
    private long userId;
    private String color;

    public GameUser()
    {

    }

    public GameUser(long _id, long _gameId, long _userId, String _color)
    {
        this.id = _id;
        this.gameId = _gameId;
        this.userId = _userId;
        this.color = _color;
    }

    //Getters
    public long getId()
    {
        return this.id;
    }
    public long getGameId()
    {
        return this.gameId;
    }

    public long getUserId()
    {
        return this.userId;
    }

    public String getColor()
    {
        return this.color;
    }

    //Setters
    public void setId(long _id)
    {
        this.id = _id;
    }
    public void setGameId(long _id)
    {
        this.gameId = _id;
    }
    public void setUserId(long _id)
    {
        this.userId = _id;
    }

    public void setColor(String _color)
    {
        this.color = _color;
    }
}
