package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pmd.ubi.pt.objects.CurrentRoomobject;

public class CurrentRoom extends AppCompatActivity
{

    private CurrentRoomobject currentRoomobject;
    private int gameId;

    private int numberOfCurrentPlayers; // Can be update by the Server

    TextView roomName;
    TextView maxNumPlayers;
    TextView numOfRounds;
    TextView numOfCharacters;
    TextView numOfCurrentPlayers;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_room);
        roomName = (TextView)findViewById(R.id.roomNameTextView);
        maxNumPlayers = (TextView)findViewById(R.id.maxNumPlayersTextView);
        numOfRounds = (TextView)findViewById(R.id.numOfRoundsTextView);
        numOfCharacters = (TextView)findViewById(R.id.numOfCharactersTextView);
        numOfCurrentPlayers = (TextView)findViewById(R.id.numOfCurrentPlayersTextView);
        password = (TextView)findViewById(R.id.passwordTextView);
        Intent i = getIntent();
        currentRoomobject = (CurrentRoomobject) i.getSerializableExtra("currentRoom");

        numberOfCurrentPlayers = currentRoomobject.getNumberOfCurrentPlayers();
        GetInfoAboutRoom(currentRoomobject);
    }

    private void GetInfoAboutRoom(CurrentRoomobject currentRoomobject)
    {
        roomName.setText(currentRoomobject.getName());
        maxNumPlayers.setText("Maximum number of players: " + currentRoomobject.getMaxNumbersOfPlayers());
        numOfRounds.setText("Number of rounds: " + currentRoomobject.getNumberOfRounds());
        numOfCharacters.setText("Number of characters: " + currentRoomobject.getNumberOfCharacters());
        numOfCurrentPlayers.setText("Number of current players: " + numberOfCurrentPlayers);

        if(currentRoomobject.getHashedPassword().isEmpty())
        {
            password.setText("Public Game");
        }
        else
        {
            password.setText("Password to room: " + currentRoomobject.getHashedPassword());
        }

        gameId = currentRoomobject.getGameID();
    }
}
