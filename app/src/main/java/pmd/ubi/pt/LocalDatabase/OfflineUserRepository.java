package pmd.ubi.pt.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.objects.OfflineUser;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 27-Dec-16.
 */

public class OfflineUserRepository
{
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.OFFLINE_USERS_ID, DatabaseHelper.OFFLINE_USERS_USERNAME, DatabaseHelper.OFFLINE_USERS_STATUS};

    public OfflineUserRepository(Context context)
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

    public OfflineUser createOfflineUser(String _userName, int _status)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.OFFLINE_USERS_USERNAME, _userName);
        values.put(DatabaseHelper.OFFLINE_USERS_STATUS, _status);

        long insertId = mDatabase.insert(DatabaseHelper.OFFLINE_USERS_TABLE, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.OFFLINE_USERS_TABLE, mAllColumns, DatabaseHelper.OFFLINE_USERS_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        OfflineUser offlineUser = cursorToOfflineUser(cursor);
        cursor.close();

        return offlineUser;
    }

    protected OfflineUser cursorToOfflineUser(Cursor cursor)
    {
        OfflineUser offlineUser = new OfflineUser();
        if(cursor != null)
        {
            offlineUser.setUserId(cursor.getLong(0));
            offlineUser.setUserName(cursor.getString(1));
            offlineUser.setUserStatus(cursor.getInt(2));
        }

        return offlineUser;
    }

    public List<OfflineUser> getAllOfflineUsers()
    {
        List<OfflineUser> offlineUserList = new ArrayList<OfflineUser>();

        Cursor cursor = mDatabase.query(DatabaseHelper.OFFLINE_USERS_TABLE, mAllColumns, null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                OfflineUser offlineUser = cursorToOfflineUser(cursor);
                offlineUserList.add(offlineUser);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return offlineUserList;
    }

    public OfflineUser getOfflineUserById(long id)
    {
        Cursor cursor = mDatabase.query(DatabaseHelper.OFFLINE_USERS_TABLE, mAllColumns, DatabaseHelper.OFFLINE_USERS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null );
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        OfflineUser offlineUser = cursorToOfflineUser(cursor);
        return offlineUser;
    }

    public void changeStatus(OfflineUser offlineUser, int _status)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.OFFLINE_USERS_USERNAME, offlineUser.getUserName());
        values.put(DatabaseHelper.OFFLINE_USERS_STATUS, _status);

        long updateId = mDatabase.update(DatabaseHelper.OFFLINE_USERS_TABLE, values, DatabaseHelper.OFFLINE_USERS_ID + " = " + offlineUser.getUserId(), null);
    }


}
