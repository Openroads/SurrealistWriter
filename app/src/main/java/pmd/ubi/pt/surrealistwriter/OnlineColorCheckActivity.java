package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import petrov.kristiyan.colorpicker.ColorPicker;
import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.CurrentRoomobject;
import pmd.ubi.pt.objects.Game;
import pmd.ubi.pt.objects.User;

public class OnlineColorCheckActivity extends AppCompatActivity
{

    private CurrentRoomobject currentRoomobject;
    private User user;
    private String color;
    private int colorPicked;
    private LinearLayout linearLayout;
    private boolean isColorAvailable = false;
    private int checkedColor = 0;
    private boolean isAdmin = false;
    TextView userMail;
    TextView roomName;
    private Integer gameId;
    private int game_id;
    private String roomNameS;


    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_color_check);

        Intent i = getIntent();
        currentRoomobject = (CurrentRoomobject) i.getSerializableExtra("currentRoom");

        user = (User) i.getSerializableExtra("user");

        userMail = (TextView)findViewById(R.id.userMail);
        userMail.setText(user.getEmail());
        linearLayout = (LinearLayout)findViewById(R.id.linearButton);
        roomName = (TextView)findViewById(R.id.roomName);
        roomNameS = (String)i.getSerializableExtra("roomName");

        if(currentRoomobject != null && currentRoomobject.getAdminId() > 0)
        {
            isAdmin = true;
            roomName.setText(currentRoomobject.getName());
        }
        else
        {
            gameId = (Integer)i.getSerializableExtra("game_id");
            roomName.setText(roomNameS);
        }

        //ColorPicker

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        mButton = new Button(this);
        mButton.setLayoutParams(params);
        mButton.setId(0);
        mButton.setHint("Set your color");
        mButton.setBackgroundColor(0);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View tempView = view;
                ColorPicker colorPicker = new ColorPicker(OnlineColorCheckActivity.this);
                colorPicker.show();
                Log.d("DEBUG", "Prev color: " + mButton.getBackground());
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        //boolean flag = true;
                        int btColor;
                        Drawable btBackground;
                        btBackground = mButton.getBackground();
                        btColor = ((ColorDrawable) btBackground).getColor();
                        //Checking if somebody has already choosen the same color
                        RequestParams params = new RequestParams();
                        if(currentRoomobject != null)
                        {
                            params.put("game_id", currentRoomobject.getGameID());
                        }
                        else
                        {
                            params.put("game_id", gameId);
                        }


                        params.put("color", color);

                        invokeWS(params);

                        //tutaj dodaje
                        checkedColor = color;
                        mButton.setBackgroundColor(color);
                        mButton.setHint("Color Set");
                        //linearLayout.removeView(mButton);
                        //linearLayout.addView(mButton);

                    }

                    @Override
                    public void onCancel() {
                        // put code
                    }
                });
            }

        });
        linearLayout.addView(mButton);

    }

    /* REST SERVER */
    public void invokeWS(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/color/checkcolor", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status"))
                    {
                        //Get Responde from Server if color is available
                        isColorAvailable = true;

                        Toast.makeText(getApplicationContext(), "Color is available!", Toast.LENGTH_SHORT).show();
                        //Tutaj zmienilem


                    }
                    // Else display error message
                    else {
                        isColorAvailable = false;
                        Toast.makeText(getApplicationContext(), "Color is  not available!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    isColorAvailable = false;
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                isColorAvailable = false;
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

    //Send Everything to the server
    public void invokeWSK(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/gameuser/creategameuser", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(), "Color has been set!", Toast.LENGTH_SHORT).show();
                        //goToNextActivity();

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


    public void getInOnClick(View view)
    {
        String game_Id = "";
        String userId = "";
        if(isColorAvailable)
        {
            if(currentRoomobject != null)
            {
                game_Id = String.valueOf(currentRoomobject.getGameID());
                userId = String.valueOf(currentRoomobject.getAdminId());
            }
            else
            {
                game_Id = String.valueOf(gameId);
                game_id = gameId;
                userId = String.valueOf(user.getId());
            }


            String color = String.valueOf(checkedColor);
            RequestParams params = new RequestParams();
            params.put("game_id", game_Id);
            params.put("user_id", userId);
            params.put("color", color);

            invokeWSK(params);
            RequestParams params2 = new RequestParams();
            params2.put("game_id", game_Id);
            invokeWSCheckAdmin(params2);
            //Waiting for flag
            goToNextActivity();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Change your color to another!", Toast.LENGTH_SHORT).show();
        }

    }



    public void invokeWSCheckAdmin(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/game/gamestatus", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status"))
                    {


                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), "Game has yet to start!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    isColorAvailable = false;
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                isColorAvailable = false;
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

    public void invokeWSKCheckAdmin(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/game/startgame", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(), "Color has been set!", Toast.LENGTH_SHORT).show();
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

    private void goToNextActivity()
    {
        if(isAdmin)
        {
            RequestParams params = new RequestParams();
            params.put("game_id", game_id);
            invokeWSKCheckAdmin(params);
            finish();
            Intent i = new Intent(this, CurrentRoom.class);
            i.putExtra("currentRoom", currentRoomobject);
            startActivity(i);
        }
        else
        {

            //finish();

            /*********** Here put the code to move to the activity when you join the room **********************/
            Toast.makeText(getApplicationContext(), "Waiting for Admin to join", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), WaitingActivity.class);
            i.putExtra("game_id", game_id);
            i.putExtra("user", user);
            i.putExtra("color", checkedColor);
            startActivity(i);
        }
    }
}
