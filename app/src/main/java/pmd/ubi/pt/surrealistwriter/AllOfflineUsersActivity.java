package pmd.ubi.pt.surrealistwriter;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;
import pmd.ubi.pt.objects.OfflineUser;

public class AllOfflineUsersActivity extends AppCompatActivity
{

    private OfflineUserRepository offlineUserRepository; // Repository - here you have basic functions line creating and getting rows from table, find by id end etc.
    private List<OfflineUser> offlineUsers = new ArrayList<OfflineUser>();

    ListView offlineUsersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offline_users);

        this.offlineUserRepository = new OfflineUserRepository(this);
        offlineUsersListView = (ListView)findViewById(R.id.offlineUsersListView);
        registerForContextMenu(offlineUsersListView);
        AddSomeOfflineUsers();
        LoadDataFromDB();

    }

    /***************** Context Menu and deleting ********************/

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            //Delete offline user (change status from 1 to 0)
            case R.id.deleteUser:
                OfflineUser user = (OfflineUser) offlineUsersListView.getItemAtPosition(info.position);

                offlineUserRepository.changeStatus(user, 0); // Deleting (changing status) for the user
                LoadDataFromDB();
                return true;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_offline_users_contex, menu);
    }

    /****************************************************************/

    /***************** Add some fake users just to test ********************/
    public void AddSomeOfflineUsers()
    {
        try
        {
            offlineUserRepository.createOfflineUser("Piotr", 1); // Status 1 - means that user exist in database, status 0 - means that user has been deleted
            offlineUserRepository.createOfflineUser("Dariusz", 1);
        }
        catch (SQLException ex)
        {
            Toast.makeText(this, "SQL error " + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }

    /***************** Load users from database and show them in listview ********************/
    public void LoadDataFromDB()
    {
        offlineUsers.clear();
        offlineUsers = offlineUserRepository.getAllOfflineUsers(); // Get All users from database


        /************ Get only those users who has status 1 **********/
        List<OfflineUser> activeUsers = new ArrayList<OfflineUser>();

        for (OfflineUser offlineuser : offlineUsers)
        {
            if(offlineuser.getUserStatus() == 1)
            {
                activeUsers.add(offlineuser);
            }

        }

        ArrayAdapter<OfflineUser> offlineUserArrayAdapter = new ArrayAdapter<OfflineUser>(this, android.R.layout.simple_list_item_1, activeUsers);
        offlineUsersListView.setAdapter(offlineUserArrayAdapter);
    }
}
