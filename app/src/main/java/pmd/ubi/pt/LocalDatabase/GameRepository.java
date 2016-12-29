package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.objects.Game;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 29-Dec-16.
 */

public class GameRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.GAME_ID, DatabaseHelper.GAME_NUMBEROFPLAYERS, DatabaseHelper.GAME_CURRENT_TEXT, DatabaseHelper.GAME_DATE, DatabaseHelper.GAME_WORD_MAXIMUM};

    public GameRepository(Context context)
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

    public Game createGame(int _numberOfPlayers, String _currentText, String _date, int _wordMax)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.GAME_NUMBEROFPLAYERS, _numberOfPlayers);
        values.put(DatabaseHelper.GAME_CURRENT_TEXT, _currentText);
        values.put(DatabaseHelper.GAME_DATE, _date);
        values.put(DatabaseHelper.GAME_WORD_MAXIMUM, _wordMax);

        long insertId = mDatabase.insert(DatabaseHelper.GAME_TABLE, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_TABLE, mAllColumns, DatabaseHelper.GAME_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Game game = cursorToGame(cursor);
        cursor.close();
        return game;
    }

    private Game cursorToGame(Cursor cursor)
    {
        Game game = new Game();
        if(cursor != null)
        {
            game.setId(cursor.getLong(0));
            game.setNumbersOfPlayers(cursor.getInt(1));
            game.setCurrentText(cursor.getString(2));
            game.setDate(DateTimeConverter.stringToDate(cursor.getString(3)));
            game.setWordMaximum(cursor.getInt(4));
        }

        return game;
    }

    public List<Game> getAllGames()
    {
        List<Game> games = new ArrayList<Game>();
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_TABLE, mAllColumns, null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Game game = cursorToGame(cursor);
                games.add(game);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return games;
    }

    public Game getGameById(long id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.GAME_TABLE, mAllColumns, DatabaseHelper.GAME_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Game game = cursorToGame(cursor);
        return game;
    }

    public void changeCurrentText(Game game, String current)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.GAME_NUMBEROFPLAYERS, game.getNumbersOfPlayers());
        values.put(DatabaseHelper.GAME_CURRENT_TEXT, current);
        values.put(DatabaseHelper.GAME_DATE, game.getDate());
        values.put(DatabaseHelper.GAME_WORD_MAXIMUM, game.getWordMaximum());

        long updateId = mDatabase.update(DatabaseHelper.GAME_TABLE, values, DatabaseHelper.OFFLINE_USERS_ID + " = " + game.getId(), null);
    }




}
