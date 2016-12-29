package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.objects.GameScore;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class GameScoreRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.GAME_SCORE_ID, DatabaseHelper.GAME_SCORE_GAMEID, DatabaseHelper.GAME_SCORE_USERID, DatabaseHelper.GAME_SCORE_SCORE};


    public GameScoreRepository(Context context)
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


    public GameScore createGameScore(long _gameId, long _userId, int _score)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.GAME_SCORE_GAMEID, _gameId);
        values.put(DatabaseHelper.GAME_SCORE_USERID, _userId);
        values.put(DatabaseHelper.GAME_SCORE_SCORE, -_score);

        long insertId = mDatabase.insert(DatabaseHelper.GAME_SCORE_TABLE, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_SCORE_TABLE, mAllColumns, DatabaseHelper.GAME_SCORE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        GameScore gameScore = cursorToGameScore(cursor);
        cursor.close();

        return gameScore;
    }

    public GameScore cursorToGameScore(Cursor cursor)
    {
        GameScore gameScore = new GameScore();

        if(cursor != null)
        {
            gameScore.setId(cursor.getLong(0));
            gameScore.setGameId(cursor.getLong(1));
            gameScore.setUserId(cursor.getLong(2));
            gameScore.setScore(cursor.getInt(3));
        }

        return gameScore;
    }

    public List<GameScore> getAllGameScore()
    {
        List<GameScore> gameScores = new ArrayList<GameScore>();

        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_SCORE_TABLE, mAllColumns, null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameScore gameScore = cursorToGameScore(cursor);
                gameScores.add(gameScore);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return gameScores;
    }

    public GameScore getGameScoreById(long id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_SCORE_TABLE, mAllColumns, DatabaseHelper.GAME_SCORE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        GameScore gameScore = cursorToGameScore(cursor);
        return gameScore;
    }

    public List<GameScore> getGameScoresByGameId(long _id)
    {
        List<GameScore> gameScores = new ArrayList<GameScore>();

        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_SCORE_TABLE, mAllColumns, DatabaseHelper.GAME_SCORE_GAMEID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameScore gameScore = cursorToGameScore(cursor);
                gameScores.add(gameScore);
                cursor.moveToNext();
            }
            cursor.close();
        }


        return gameScores;
    }

    public List<GameScore> getGameScoresByUserId(long _id)
    {
        List<GameScore> gameScores = new ArrayList<GameScore>();

        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_SCORE_TABLE, mAllColumns, DatabaseHelper.GAME_SCORE_USERID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameScore gameScore = cursorToGameScore(cursor);
                gameScores.add(gameScore);
                cursor.moveToNext();
            }
            cursor.close();
        }


        return gameScores;
    }

}
