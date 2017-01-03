package pmd.ubi.pt.LocalDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Piotr on 05-Dec-16.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String TAG = "DBHelper";

    public static final String DATABASE_NAME = "MyDatabase.sqlite";
    public static final int DATABASE_VERSION = 1;

    /******************** Tables ********************/

    //OfflineUserTable
    public static final String OFFLINE_USERS_TABLE = "OfflineUsers";
    public static final String OFFLINE_USERS_ID = "Id";
    public static final String OFFLINE_USERS_USERNAME = "UserName";
    public static final String OFFLINE_USERS_STATUS = "Status";


    //GameUserTable
    public static final String GAME_USER_TABLE = "GameUsers";
    public static final String GAME_USER_ID = "Id";
    public static final String GAME_USER_GAMEID = "GameId";
    public static final String GAME_USER_USERID = "UserId";
    public static final String GAME_USER_COLOR = "Color";

    //GameScoreTable
    public static final String GAME_SCORE_TABLE = "GameScore";
    public static final String GAME_SCORE_ID = "Id";
    public static final String GAME_SCORE_GAMEID = "GameId";
    public static final String GAME_SCORE_USERID = "UserId";
    public static final String GAME_SCORE_SCORE = "Score";

    //Ranking Table
    public static final String RANKING_TABLE = "Ranking";
    public static final String RANKING_ID = "Id";
    public static final String RANKING_USERID = "UserId";
    public static final String RANKING_SCORE = "Score";

    //Game Table
    public static final String GAME_TABLE = "Game";
    public static final String GAME_ID = "Id";
    public static final String GAME_NUMBEROFPLAYERS = "NumberOfPlayers";
    public static final String GAME_CURRENT_TEXT = "CurrentText";
    public static final String GAME_DATE = "Data";
    public static final String GAME_WORD_MAXIMUM = "WordMaximum";



    /******************** End of Tables ********************/


    public static final String SQL_CREATE_OFFLINE_USERS_TABLE = "CREATE TABLE " + OFFLINE_USERS_TABLE + "("
            + OFFLINE_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OFFLINE_USERS_USERNAME + " VARCHAR(50) NOT NULL, "
            + OFFLINE_USERS_STATUS + " INTEGER NOT NULL "
            + " );";


    public static final String SQL_CREATE_GAME_USER_TABLE = "CREATE TABLE " + GAME_USER_TABLE + "("
            + GAME_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GAME_USER_GAMEID + " INTEGER NOT NULL, "
            + GAME_USER_USERID + " INTEGER NOT NULL, "
            + GAME_USER_COLOR + " VARCHAR(50) NOT NULL "
            + " );";

    public static final String SQL_CREATE_GAME_SCORE_TABLE = "CREATE TABLE " + GAME_SCORE_TABLE + "("
            + GAME_SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GAME_SCORE_GAMEID + " INTEGER NOT NULL, "
            + GAME_SCORE_USERID + " INTEGER NOT NULL, "
            + GAME_SCORE_SCORE + " INTEGER NOT NULL "
            + " );";

    public static final String SQL_CREATE_RANKING_TABLE = "CREATE TABLE " + RANKING_TABLE + "("
            + RANKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RANKING_USERID + " INTEGER NOT NULL, "
            + RANKING_SCORE + " INTEGER NOT NULL "
            + " );";

    public static final String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + GAME_TABLE + "("
            + GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GAME_NUMBEROFPLAYERS + " INTEGER NOT NULL, "
            + GAME_CURRENT_TEXT + " VARCHAR(765) NOT NULL, "
            + GAME_DATE + " VARCHAR(50) NOT NULL, "
            + GAME_WORD_MAXIMUM + " INTEGER NOT NULL "
            + " );";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_OFFLINE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_SCORE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RANKING_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        Log.w(TAG, "Upgrading database from version " + i + " to " + i1);

        //Cleare data
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + OFFLINE_USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + GAME_USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + GAME_SCORE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + RANKING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + GAME_TABLE);

        //Recreate tables
        onCreate(sqLiteDatabase);
    }
}
