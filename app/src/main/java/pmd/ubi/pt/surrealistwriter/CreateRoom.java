package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateRoom extends AppCompatActivity {

    EditText roomNameET;
    EditText maxNumPlayersET;
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
        numCharactersET =   (EditText) findViewById(R.id.num_chars_ET);
        roomModeTogButt = (ToggleButton) findViewById(R.id.roomModeTG);

        String roomName = roomNameET.getText().toString();
        int maxNumPlayers = Integer.parseInt(maxNumPlayersET.getText().toString());
        int numCharacters = Integer.parseInt(numCharactersET.getText().toString());



    }
    public boolean checkData(String name,int iNumPlayers, int iNumCharacters) {
        if (name.isEmpty()) {
            Toast.makeText(this, "You must enter name of room!", Toast.LENGTH_SHORT).show();
            return false;
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
        return true;
    }
}
