package pmd.ubi.pt.objects;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class GameScore
{
    private long id;
    private long gameId;
    private long userId;
    private int score;


    public GameScore()
    {

    }

    public GameScore(long _id, long _gameId, long _userId, int _score)
    {
        this.id = _id;
        this.gameId = _gameId;
        this.userId = _userId;
        this.score = _score;
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
    public int getScore()
    {
        return this.score;
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
    public void setScore(int _score)
    {
        this.score = _score;
    }
}
