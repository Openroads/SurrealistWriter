package pmd.ubi.pt.surrealistwriter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateRoom extends AppCompatActivity {

    EditText roomNameET;
    EditText maxNumPlayersET;
    EditText numRoundsET;
    EditText numCharactersET;
    ToggleButton roomModeTogButt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
    }

    public void create_tableOnClick(View view) {
        roomNameET =        (EditText) findViewById(R.id.room_name_ET);
        maxNumPlayersET =   (EditText) findViewById(R.id.num_players_ET);
        numRoundsET =       (EditText) findViewById(R.id.num_rounds_ET);
        numCharactersET =   (EditText) findViewById(R.id.num_chars_ET);
        roomModeTogButt = (ToggleButton) findViewById(R.id.roomModeTG);

        String roomName = roomNameET.getText().toString();
        String maxNumPlayers = maxNumPlayersET.getText().toString();
        String numRounds = numRoundsET.getText().toString();
        String numCharacters = numCharactersET.getText().toString();
        String gameMode = roomModeTogButt.getText().toString();
        //valid data to use
        if(checkData(roomName,maxNumPlayers,numCharacters,numRounds))
        {

            Toast.makeText(this, "przechodzi"+gameMode, Toast.LENGTH_SHORT).show();
        }



    }
    public boolean checkData(String name,String NumPlayers, String NumCharacters,String NumRounds) {
        int iNumPlayers = 0;
        int iNumCharacters = 0;
        int iNumRounds = 0;
        if (name.isEmpty()) {
            Toast.makeText(this, "You must enter name of room!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (NumPlayers.isEmpty()) {
            Toast.makeText(this, "You must enter number of players!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (NumCharacters.isEmpty()) {
            Toast.makeText(this, "You must enter number of characters!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (NumRounds.isEmpty()) {
            Toast.makeText(this, "You must enter number of rounds!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            iNumPlayers = Integer.parseInt(NumPlayers);
            iNumRounds = Integer.parseInt(NumRounds);
            iNumCharacters = Integer.parseInt(NumCharacters);
        }
        // Checking Number of Players

        if (iNumPlayers > 0) {
            if (iNumPlayers >= 8) {
                Toast.makeText(this, "Maximum of 8 players!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Minimum of 1 player!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Checking Number of Characters

        if (iNumCharacters >= iNumPlayers * 2) {
            if (iNumCharacters >= 1024) {
                Toast.makeText(this, "Maximum of 1024 Characters!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Minimum of " + iNumPlayers * 2 + " characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Checking Number of Rounds

        if (iNumRounds > 0) {
            if (iNumRounds >= 20) {
                Toast.makeText(this, "Maximum of 20 rounds!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Minimum of 1 round!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
