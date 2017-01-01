package pmd.ubi.pt.objects;

/**
 * Created by Piotr on 01-Jan-17.
 */

public class RankDisplay implements Comparable<RankDisplay>
{
    private String userName;
    private int scores;

    public RankDisplay(String userName, int scores)
    {
        this.userName = userName;
        this.scores = scores;
    }

    public String toString()
    {
        return "User: " + this.userName + ", Scores: " + this.scores;
    }

    //Getters
    public int getScores()
    {
        return this.scores;
    }

    @Override
    public int compareTo(RankDisplay another)
    {
        int compareScores = ((RankDisplay) another).getScores();
        return compareScores - this.scores;
    }
}
