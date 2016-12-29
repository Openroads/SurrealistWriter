package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.objects.GameUser;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 28-Dec-16.
 */

public class GameUserRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.GAME_USER_ID, DatabaseHelper.GAME_USER_GAMEID, DatabaseHelper.GAME_USER_USERID, DatabaseHelper.GAME_USER_COLOR};

    public GameUserRepository(Context context)
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

    public GameUser createGameUser(long _gameId, long _userId, String _color)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.GAME_USER_GAMEID, _gameId);
        values.put(DatabaseHelper.GAME_USER_USERID, _userId);
        values.put(DatabaseHelper.GAME_USER_COLOR, _color);

        long insertId = mDatabase.insert(DatabaseHelper.GAME_USER_TABLE, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_USER_TABLE, mAllColumns, DatabaseHelper.GAME_USER_ID+ " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        GameUser gameUser = cursorToGameUser(cursor);
        cursor.close();

        return gameUser;
    }

    protected GameUser cursorToGameUser(Cursor cursor)
    {
        GameUser gameUser = new GameUser();
        if(cursor != null)
        {
            gameUser.setId(cursor.getLong(0));
            gameUser.setGameId(cursor.getLong(1));
            gameUser.setUserId(cursor.getLong(2));
            gameUser.setColor(cursor.getString(3));
        }

        return gameUser;
    }

    public List<GameUser> getAllGameUser()
    {
        List<GameUser> gameUsers = new ArrayList<GameUser>();
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_USER_TABLE, mAllColumns, null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameUser gameUser = cursorToGameUser(cursor);
                gameUsers.add(gameUser);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return gameUsers;
    }

    public GameUser getGameUserById(long _id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_USER_TABLE, mAllColumns, DatabaseHelper.GAME_USER_ID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        GameUser gameUser = cursorToGameUser(cursor);
        return gameUser;
    }

    public List<GameUser> getGameUsersByGameId(long _id)
    {
        List<GameUser> gameUsers = new ArrayList<GameUser>();

        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_USER_TABLE, mAllColumns, DatabaseHelper.GAME_USER_GAMEID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameUser gameUser = cursorToGameUser(cursor);
                gameUsers.add(gameUser);
                cursor.moveToNext();
            }
            cursor.close();
        }


        return gameUsers;
    }

    public List<GameUser> getGameUsersByUserId(long _id)
    {
        List<GameUser> gameUsers = new ArrayList<GameUser>();

        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_USER_TABLE, mAllColumns, DatabaseHelper.GAME_USER_USERID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                GameUser gameUser = cursorToGameUser(cursor);
                gameUsers.add(gameUser);
                cursor.moveToNext();
            }
            cursor.close();
        }


        return gameUsers;
    }

}
