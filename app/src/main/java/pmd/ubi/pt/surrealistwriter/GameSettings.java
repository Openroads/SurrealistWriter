package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameSettings extends AppCompatActivity {


    EditText oNumPlayers;
    EditText oNumCharacters;
    ToggleButton oToggleButton;

    int iNumPlayers;
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
        iNumPlayers    = Integer.parseInt(oNumPlayers.getText().toString());
        iNumCharacters = Integer.parseInt(oNumCharacters.getText().toString());
        oToggleButton  = (ToggleButton) findViewById(R.id.toggleButtonGameMode);
        iGameMode      = getGameMode();

        Intent intent = new Intent(this, PlayerName.class);
        intent.putExtra("numPlayers",iNumPlayers);
        intent.putExtra("numCharacters", iNumCharacters);
        intent.putExtra("gameMode", iGameMode);
        startActivity(intent);
    }

    public int getGameMode(){
        if(oToggleButton.getText().toString().equals("Random"))
            return 0;
        else
            return 1;
    }

    public boolean checkEditText(){
        Boolean flag   = false;
        oNumPlayers    = (EditText) findViewById(R.id.ETNumPlayers);
        oNumCharacters = (EditText) findViewById(R.id.ETNumCharacters);
        if(oNumPlayers.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of players!", Toast.LENGTH_SHORT).show();
            return flag;
        }
        if(oNumCharacters.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of characters!", Toast.LENGTH_SHORT).show();
            return flag;
        }

        iNumPlayers = Integer.parseInt(oNumPlayers.getText().toString());
        iNumCharacters = Integer.parseInt(oNumCharacters.getText().toString());


        // Checking Number of Players

            if(iNumPlayers>0)
                if(iNumPlayers<=8)
                    flag = true;
                else
                    Toast.makeText(this,"Maximum of 8 players!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Minimum of 1 player!", Toast.LENGTH_SHORT).show();

        // Checking Number of Characters

            if(iNumCharacters >=iNumPlayers*2)
                if(iNumCharacters <=1024)
                    flag = true;
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
