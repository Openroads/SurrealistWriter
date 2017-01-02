package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pmd.ubi.pt.objects.OfflineUser;
import pmd.ubi.pt.objects.User;

public class OnlineMode extends AppCompatActivity
{
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode);
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
    }

    public void createRoomOC(View view) {
        Intent crIntent = new Intent(this,CreateRoom.class);
        crIntent.putExtra("user", user);
        startActivity(crIntent);
    }

    public void joinRoomOC(View view) {
        Intent jIntent = new Intent(this,CurrentRoom.class);
        jIntent.putExtra("user", user);
        startActivity(jIntent);
    }
}
