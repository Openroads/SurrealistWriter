package pmd.ubi.pt.objects;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class Ranking
{
    private long id;
    private long userId;
    private int score;

    public Ranking()
    {

    }

    public Ranking(long _id, long _userId, int _scores)
    {
        this.id = _id;
        this.userId = _userId;
        this.score = _scores;
    }

    //Getters
    public long getId()
    {
        return this.id;
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
    public void setUserId(long _id)
    {
        this.userId = _id;
    }
    public void setScore(int _score)
    {
        this.score = _score;
    }
}
