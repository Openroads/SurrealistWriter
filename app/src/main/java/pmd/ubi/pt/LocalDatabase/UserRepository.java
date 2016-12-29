package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

import pmd.ubi.pt.objects.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 05-Dec-16.
 */

public class UserRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.USERS_ID, DatabaseHelper.USER_USERNAME, DatabaseHelper.USER_EMAIL, DatabaseHelper.USER_HASHED_PASSWORD, DatabaseHelper.USER_CREATION_DATE};


    public UserRepository(Context context)
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

    public User createUser(String _username, String _email, String _hashedpassword)
    {
        ContentValues values = new ContentValues();

        Date currentDate = new Date();

        values.put(DatabaseHelper.USER_USERNAME, _username);
        values.put(DatabaseHelper.USER_EMAIL, _email);
        values.put(DatabaseHelper.USER_HASHED_PASSWORD, _hashedpassword);
        values.put(DatabaseHelper.USER_CREATION_DATE, DateTimeConverter.dateToString(currentDate));

        long insertId = mDatabase.insert(DatabaseHelper.USERS_TABLE, null, values);

        Cursor cursor = mDatabase.query(DatabaseHelper.USERS_TABLE, mAllColumns, DatabaseHelper.USERS_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();

        return newUser;
    }

    protected User cursorToUser(Cursor cursor)
    {
        User question = new User();
        if(cursor != null)
        {

            question.setId(cursor.getLong(0));
            question.setUserName(cursor.getString(1));
            question.setEmail(cursor.getString(2));
            Date d;
            d = DateTimeConverter.stringToDate(cursor.getString(3));
            //question.setHashedPassword(cursor.getString(3), d);
        }


        return question;
    }


}
