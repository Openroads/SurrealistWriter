package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.CurrentRoomobject;
import pmd.ubi.pt.objects.User;

public class CreateRoom extends AppCompatActivity
{

    EditText roomNameET;
    EditText maxNumPlayersET;
    EditText numRoundsET;
    EditText numCharactersET;
    EditText passwordET;
    ToggleButton roomModeTogButt;

    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        roomNameET        = (EditText) findViewById(R.id.room_name_ET);
        maxNumPlayersET   = (EditText) findViewById(R.id.num_players_ET);
        numRoundsET       = (EditText) findViewById(R.id.num_rounds_ET);
        numCharactersET   = (EditText) findViewById(R.id.num_chars_ET);
        passwordET        = (EditText) findViewById(R.id.password_ET);
        roomModeTogButt = (ToggleButton) findViewById(R.id.roomModeTG);


        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");


    }

    public void create_tableOnClick(View view) {

        String roomName = roomNameET.getText().toString();
        String maxNumPlayers = maxNumPlayersET.getText().toString();
        String numRounds = numRoundsET.getText().toString();
        String numCharacters = numCharactersET.getText().toString();
        String password = passwordET.getText().toString();
        //valid data to use
        if(checkData(roomName,maxNumPlayers,numCharacters,numRounds, password))
        {
            RequestParams params = new RequestParams();
            params.put("room_name", roomName);
            params.put("max_num_players", maxNumPlayers);
            params.put("num_characters", numCharacters);
            params.put("num_rounds", numRounds);
            params.put("password", password);
            params.put("user_id", user.getId());

            invokeWS(params);
        }



    }
    public boolean checkData(String name,String NumPlayers, String NumCharacters,String NumRounds, String password) {
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

        //Checking Password

        boolean on = roomModeTogButt.isChecked();

        if(on)
        {
            if(password == null || password.isEmpty())
            {
                Toast.makeText(this, "Please enter a password!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(password.contains(" "))
            {
                Toast.makeText(this, "Password cannot contain white spaces!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(password.length() < 5 && !password.isEmpty())
            {
                Toast.makeText(this, "Password cannot be shorter than 5 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    /* REST SERVER */
    public void invokeWS(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/room/docreateroom", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        //setDefaultValues();
                        Toast.makeText(getApplicationContext(), "Room has been successfully created!", Toast.LENGTH_LONG).show();
                        navigateToCurrentRoomActivity();
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public void navigateToCurrentRoomActivity(){
        finish();
        CurrentRoomobject curr = new CurrentRoomobject(roomNameET.getText().toString(), Integer.parseInt(maxNumPlayersET.getText().toString()), Integer.parseInt(numRoundsET.getText().toString()), Integer.parseInt(numCharactersET.getText().toString()), 1, passwordET.getText().toString(), user.getId());
        Intent currentRoomIntent = new Intent(getApplicationContext(),CurrentRoom.class);
        startActivity(currentRoomIntent);
    }

    private void setDefaultValues()
    {
        roomNameET.setText("");
        maxNumPlayersET.setText("");
        numRoundsET.setText("");
        numCharactersET.setText("");
        roomModeTogButt.setText("");
    }


    public void signInOC(View view) {
        navigateToCurrentRoomActivity();

    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on)
        {
            passwordET.setVisibility(View.VISIBLE);
        } else {
            passwordET.setText("");
            passwordET.setVisibility(View.GONE);
        }
    }
}
