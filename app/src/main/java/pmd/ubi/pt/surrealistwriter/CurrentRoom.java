package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.CurrentRoomobject;
import pmd.ubi.pt.objects.OfflineUser;
import pmd.ubi.pt.objects.User;

import com.google.gson.JsonArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_room);
        userListView = (ListView)findViewById(R.id.currentPlayersListView);
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

        RequestParams params = new RequestParams();
        params.put("game_id", gameId);
        invokeWS(params);
    }

    /* REST SERVER */
    public void invokeWS(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient(); //room instead createroom
        client.get(ConstantVariables.ServiceConnectionString + "/room/playersinroom", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true

                    JSONObject objInfo = obj.getJSONObject("Info");

                    if (objInfo.getBoolean("status"))
                    {


                        //JSONArray jsonArray = obj.getJSONArray("UsersArray");
                        String jsonArrayString = obj.getString("UsersArray");

                        Type type = new TypeToken<List<User>>(){}.getType();
                        List<User> users =  new Gson().fromJson(jsonArrayString, type);

                        for (User user : users)
                        {
                            Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                        }

                        ArrayAdapter<User> offlineUserArrayAdapter = new ArrayAdapter<User>(getApplicationContext(), android.R.layout.simple_list_item_1, users);
                        userListView.setAdapter(offlineUserArrayAdapter);


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

    private void LoadActiveUsersToGage()
    {

    }
}
