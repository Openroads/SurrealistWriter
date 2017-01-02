package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.objects.Ranking;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class RankingRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.RANKING_ID, DatabaseHelper.RANKING_USERID, DatabaseHelper.RANKING_SCORE};


    public RankingRepository(Context context)
    {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(context);

        //Open database
        try
        {
            open();
        }
        catch(SQLException ex)
        {
            Log.e(TAG, "SQLException on openning database " + ex.getMessage());
            ex.printStackTrace();
            close();
        }
    }

    public void open() throws SQLException
    {
        mDatabase = mDbHelper.getWritableDatabase();
    }
    public void close()
    {
        mDbHelper.close();
    }

    public Ranking createRanking(long _userId, int _score)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.RANKING_USERID, _userId);
        values.put(DatabaseHelper.RANKING_SCORE, _score);

        long insertId = mDatabase.insert(DatabaseHelper.RANKING_TABLE, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.RANKING_TABLE, mAllColumns, DatabaseHelper.RANKING_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Ranking ranking = cursorToRanking(cursor);
        cursor.close();

        return ranking;
    }

    public void updateRanking(long _userId, int _score){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.RANKING_USERID, _userId);


        Cursor cRetrieveScore = mDatabase.query(DatabaseHelper.RANKING_TABLE, mAllColumns,
                DatabaseHelper.RANKING_USERID + " = " + _userId, null, null, null, null);
        cRetrieveScore.moveToFirst();
        int dbScore = cRetrieveScore.getInt(2);
        dbScore += _score;

        values.put(DatabaseHelper.RANKING_SCORE, dbScore);
        mDatabase.update(DatabaseHelper.RANKING_TABLE,values,"UserId="+_userId,null);


    }

    private Ranking cursorToRanking(Cursor cursor)
    {
        Ranking ranking = new Ranking();
        if(cursor != null)
        {
            ranking.setId(cursor.getLong(0));
            ranking.setUserId(cursor.getLong(1));
            ranking.setScore(cursor.getInt(2));
        }

        return ranking;
    }

    public List<Ranking> getAllRanking()
    {
        List<Ranking> rankings = new ArrayList<Ranking>();
        Cursor cursor = mDatabase.query(DatabaseHelper.RANKING_TABLE, mAllColumns, null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Ranking ranking = cursorToRanking(cursor);
                rankings.add(ranking);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return rankings;

    }

    public Ranking getRankingById(long id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.RANKING_TABLE, mAllColumns, DatabaseHelper.RANKING_ID+ " = ?", new String[]{String.valueOf(id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Ranking ranking = cursorToRanking(cursor);
        return ranking;
    }

    public Ranking getRankingByUserId(long id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.RANKING_TABLE, mAllColumns, DatabaseHelper.RANKING_USERID + " = ?", new String[]{String.valueOf(id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Ranking ranking = cursorToRanking(cursor);
        return ranking;
    }



}
