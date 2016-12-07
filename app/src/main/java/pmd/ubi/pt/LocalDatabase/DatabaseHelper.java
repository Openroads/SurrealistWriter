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

    //UserTable
    public static final String USERS_TABLE = "Users";
    public static final String USERS_ID = "Id";
    public static final String USER_USERNAME = "Username";
    public static final String USER_EMAIL = "Email";
    public static final String USER_HASHED_PASSWORD = "HashedPassword";
    public static final String USER_CREATION_DATE = "CreationDate";


    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + USERS_TABLE + "("
            + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_USERNAME + " VARCHAR(50) NOT NULL, "
            + USER_EMAIL + " VARCHAR(50) NOT NULL, "
            + USER_HASHED_PASSWORD + " VARCHAR(100) NOT NULL, "
            + USER_CREATION_DATE + " VARCHAR(50) NOT NULL "
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
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        Log.w(TAG, "Upgrading database from version " + i + " to " + i1);

        //Cleare data
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + USERS_TABLE);

        //Recreate tables
        onCreate(sqLiteDatabase);
    }
}
