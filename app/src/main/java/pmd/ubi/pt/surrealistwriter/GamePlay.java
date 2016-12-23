package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GamePlay extends AppCompatActivity {

    int iNumPlayers;
    int iNumCharacters;
    int iGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        iNumPlayers    = getIntent().getExtras().getInt("numPlayers");
        iNumCharacters = getIntent().getExtras().getInt("numCharacters");
        iGameMode      = getIntent().getExtras().getInt("gameMode");

    }
}
