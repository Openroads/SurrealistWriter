package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OfflineMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);
    }

    public void gameplay(View v){
        Intent gameplayIntent = new Intent(this, Gameplay.class);
        startActivity(gameplayIntent);


    }
}
