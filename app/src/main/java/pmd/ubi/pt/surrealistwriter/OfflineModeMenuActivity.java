package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pmd.ubi.pt.objects.*;

public class OfflineModeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode_menu);
    }

    public void offlineModeOnClick(View view)
    {
        Intent i = new Intent(this, OfflineGameSettings.class);
        startActivity(i);
    }

    public void rankingOnClick(View view)
    {
        Intent i = new Intent(this, Ranking.class);
        startActivity(i);
    }
}
