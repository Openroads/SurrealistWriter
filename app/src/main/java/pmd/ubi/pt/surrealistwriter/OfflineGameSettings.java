package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class OfflineGameSettings extends AppCompatActivity {


    EditText oNumPlayers;
    EditText oNumCharacters;
    EditText oNumRounds;
    ToggleButton oToggleButton;

    int iNumPlayers;
    int iNumRounds;
    int iNumCharacters;
    int iGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);


    }

    public void onClickPlay(View view){
        if(checkEditText()==false)
            return;



        oNumPlayers    = (EditText) findViewById(R.id.ETNumPlayers);
        oNumCharacters = (EditText) findViewById(R.id.ETNumCharacters);
        oNumRounds     = (EditText) findViewById(R.id.ETNumRounds);
        iNumPlayers    = Integer.parseInt(oNumPlayers.getText().toString());
        iNumRounds     = Integer.parseInt(oNumRounds.getText().toString());
        iNumCharacters = Integer.parseInt(oNumCharacters.getText().toString());
        oToggleButton  = (ToggleButton) findViewById(R.id.toggleButtonGameMode);
        iGameMode      = getGameMode();

        Intent intent = new Intent(this, OfflinePlayerName.class);
        intent.putExtra("numPlayers",iNumPlayers);
        intent.putExtra("numRounds",iNumRounds);
        intent.putExtra("numCharacters", iNumCharacters);
        intent.putExtra("gameMode", iGameMode);
        startActivity(intent);
        finish();
    }

    public int getGameMode(){
        if(oToggleButton.getText().toString().equals("Random"))
            return 0;
        else
            return 1;
    }

    public boolean checkEditText(){
        Boolean flag   = true;
        oNumPlayers    = (EditText) findViewById(R.id.ETNumPlayers);
        oNumCharacters = (EditText) findViewById(R.id.ETNumCharacters);
        oNumRounds     = (EditText) findViewById(R.id.ETNumRounds);
        if(oNumPlayers.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of players!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(oNumRounds.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of rounds!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(oNumCharacters.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of characters!", Toast.LENGTH_SHORT).show();
            return false;
        }


        iNumPlayers    = Integer.parseInt(oNumPlayers.getText().toString());
        iNumRounds     = Integer.parseInt(oNumRounds.getText().toString());
        iNumCharacters = Integer.parseInt(oNumCharacters.getText().toString());



        // Checking Number of Players

            if(iNumPlayers>0)
                if(iNumPlayers<=8);
                else {
                    Toast.makeText(this, "Maximum of 8 players!", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            else {
                Toast.makeText(this, "Minimum of 1 player!", Toast.LENGTH_SHORT).show();
                flag = false;
            }

        // Checking Number of Rounds

            if(iNumRounds>0)
                if(iNumRounds<=20);
                else {
                    Toast.makeText(this, "Maximum of 20 rounds!", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            else {
                Toast.makeText(this, "Minimum of 1 round!", Toast.LENGTH_SHORT).show();
                flag = false;
            }

        // Checking Number of Characters

            if(iNumCharacters >=iNumPlayers*2)
                if(iNumCharacters <=1024);
                else {
                    Toast.makeText(this, "Maximum of 1024 Characters!", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            else {
                Toast.makeText(this, "Minimum of " + iNumPlayers * 2 + " characters!", Toast.LENGTH_SHORT).show();
                flag = false;
            }

        return flag;
    }
}
