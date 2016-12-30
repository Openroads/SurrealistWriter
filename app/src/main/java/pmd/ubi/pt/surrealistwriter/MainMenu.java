package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;

public class MainMenu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void offlineMode(View view){
        Intent intent = new Intent(this, OfflineGameSettings.class);
        startActivity(intent);
    }
    public void rankingOnClick(View view)
    {
        Intent intent = new Intent(this,Ranking.class);
        startActivity(intent);
    }

    public void onlineModeOC(View view) {
        Intent intent = new Intent(this,OnlineMode.class);
        startActivity(intent);
    }

    public void settingsOnClick(View view)
    {
        Intent intent = new Intent(this, AllOfflineUsersActivity.class);
        startActivity(intent);
    }
}
