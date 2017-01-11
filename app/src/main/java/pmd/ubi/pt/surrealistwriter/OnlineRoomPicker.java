package pmd.ubi.pt.surrealistwriter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

import cz.msebera.android.httpclient.HttpResponse;
import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.User;

public class OnlineRoomPicker extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView [] tvRoomName;
    private TextView [] tvRoomOccupation;
    private String [] roomNames;
    private int [] roomIds;
    private int [] roomStatus;
    private int [] roomMax;
    private int [] currNumPlayers;
    private int numberOfRooms;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_room_picker);

        numberOfRooms = 3; // Waiting on Dariusz


        invokeWS(new RequestParams());
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");





    }

    public void invokeWS(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("DEBUG","INVOKE");

        client.get(ConstantVariables.ServiceConnectionString + "/room/getroom",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    final JSONObject obj = new JSONObject(str);
                    final JSONArray  objArray = obj.getJSONArray("RoomsArray");
                    final JSONObject objInfo  = obj.getJSONObject("Info");

                    Log.d("DEBUG","BEFORE IF");
                    // When the JSON response has status boolean value assigned with true
                    if(objInfo.getBoolean("status")) {
                        numberOfRooms = objInfo.getInt("room_amount");
                        Log.d("DEBUG",""+numberOfRooms);
                        roomIds        = new int [numberOfRooms];
                        roomNames      = new String[numberOfRooms]; //Waiting on Dariusz
                        roomStatus     = new int [numberOfRooms];   //Waiting on Dariusz
                        roomMax        = new int [numberOfRooms];   //Waiting on Dariusz
                        currNumPlayers = new int [numberOfRooms];   //Waiting on Dariusz
                        for (int i = 0; i < numberOfRooms; i++) {
                            Log.d("Debug","Inside status");
                            roomIds[i] = objArray.getJSONObject(i).getInt("game_id");
                            Log.d("DEBUG",""+roomIds[i]);
                            roomNames[i] = objArray.getJSONObject(i).getString("room_name");
                            Log.d("DEBUG",""+roomNames[i]);
                            roomStatus[i] = objArray.getJSONObject(i).getInt("mode");
                            Log.d("DEBUG",""+roomStatus[i]);
                            roomMax[i] = objArray.getJSONObject(i).getInt("max_places");
                            Log.d("DEBUG",""+roomMax[i]);
                            currNumPlayers[i] = objArray.getJSONObject(i).getInt("occupied_places");
                            Log.d("DEBUG",""+currNumPlayers[i]);


                            setDefaultValues();
                            navigateToLogActivity();
                        }

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.roomPickerLinearLayout);
                        final LinearLayout lineLayout[] = new LinearLayout[numberOfRooms];
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        ImageView imageView[]     = new ImageView[numberOfRooms];
                        tvRoomName       = new TextView[numberOfRooms];
                        tvRoomOccupation = new TextView[numberOfRooms];

                        for (int i=0;i<numberOfRooms;i++){
                            lineLayout[i]       = new LinearLayout(getApplicationContext());
                            lineLayout[i].setOrientation(LinearLayout.HORIZONTAL);

                            tvRoomName[i] = new TextView(getApplicationContext());
                            tvRoomName[i].setLayoutParams(params);
                            tvRoomName[i].setGravity(Gravity.CENTER);
                            Log.d("DEBUG", ""+roomNames[i]);
                            tvRoomName[i].setText(""+roomNames[i]);
                            tvRoomName[i].setTextSize(20);
                            tvRoomName[i].setTextColor(Color.BLACK);
                            lineLayout[i].addView(tvRoomName[i]);

                            tvRoomOccupation[i] = new TextView(getApplicationContext());
                            tvRoomOccupation[i].setLayoutParams(params);
                            tvRoomOccupation[i].setGravity(Gravity.CENTER);
                            tvRoomOccupation[i].setText(""+currNumPlayers[i]+"/"+roomMax[i]);
                            tvRoomOccupation[i].setTextSize(20);
                            tvRoomOccupation[i].setTextColor(Color.BLACK);
                            lineLayout[i].addView(tvRoomOccupation[i]);

                            imageView[i] = new ImageView(getApplicationContext());
                            imageView[i].setLayoutParams(params);
                            Log.d("DEBUG","Room Status: "+roomStatus[i]);
                            if(roomStatus[i]==0)
                                imageView[i].setImageResource(R.mipmap.lock_open);
                            if(roomStatus[i]==1)
                                imageView[i].setImageResource(R.mipmap.lock_closed);
                            imageView[i].getLayoutParams().height=70;
                            imageView[i].getLayoutParams().width=70;
                            lineLayout[i].addView(imageView[i]);
                            lineLayout[i].setId(i);
                            lineLayout[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(), "Room "+
                                            tvRoomName[view.getId()].getText().toString(),Toast.LENGTH_SHORT).show();
                                    int rid = -5;
                                    //Go to choose the color
                                    for (int i = 0; i < numberOfRooms; i++) {
                                        Log.d("Debug", "Inside status");
                                        try {
                                            if(objArray.getJSONObject(i).getString("room_name").equals( tvRoomName[view.getId()].getText().toString()))
                                                rid = objArray.getJSONObject(i).getInt("game_id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        Log.d("DEBUG", "" + ""+rid);
                                    }
                                    finish();
                                    Intent i = new Intent(getApplicationContext(), OnlineColorCheckActivity.class);
                                    i.putExtra("user", user);
                                    i.putExtra("game_id", String.valueOf(rid));
                                    i.putExtra("roomName", tvRoomName[view.getId()].getText().toString());
                                    startActivity(i);


                                }
                            });
                            linearLayout.addView(lineLayout[i]);
                        }
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        Log.d("DEBUG","ELSE");
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    Log.d("DEBUG","CATCH");
                    e.printStackTrace();
                }
            }

                @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if(statusCode == 404){
                    Log.d("DEBUG","Requested resource not found");
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Log.d("DEBUG","Something went wrong at server end");
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Log.d("DEBUG","Unexpected Error occcured!");
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                super.onPostProcessResponse(instance, response);
            }
        });
    }

    public void navigateToLogActivity(){
        /*finish();
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);*/
    }

    private void setDefaultValues(){
        /*mUserNameView.setText("");
        mEmailView.setText("");
        mPasswordView.setText("");*/
    }
}
