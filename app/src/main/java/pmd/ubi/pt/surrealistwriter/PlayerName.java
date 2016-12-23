package pmd.ubi.pt.surrealistwriter;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class PlayerName extends AppCompatActivity {

    int iNumPlayers;
    int iNumCharacters;
    int iGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        iNumPlayers    = getIntent().getExtras().getInt("numPlayers");
        iNumCharacters = getIntent().getExtras().getInt("numCharacters");
        iGameMode      = getIntent().getExtras().getInt("gameMode");

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_player_name);
        RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText etPlayerNameSelection = new EditText(getApplicationContext());
        etPlayerNameSelection.setLayoutParams(rLayoutParams);
        rLayout.addView(etPlayerNameSelection);

    }
}
