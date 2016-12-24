package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OnlineMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode);
    }

    public void createRoomOC(View view) {
        Intent crIntent = new Intent(this,CreateRoom.class);
        startActivity(crIntent);
    }

    public void joinRoomOC(View view) {
        Intent jIntent = new Intent(this,CurrentRoom.class);
        startActivity(jIntent);
    }
}
