package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.CurrentRoomobject;
import pmd.ubi.pt.objects.User;

public class WaitingActivity extends AppCompatActivity {

    private boolean status = false;
    private int game_Id;
    private User user;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        Intent i = getIntent();
        game_Id = (Integer) i.getSerializableExtra("game_id");
        user = (User) i.getSerializableExtra("user");
        color = (Integer) i.getSerializableExtra("color");

        checkForUpdateStatusGame();

    }


    private void checkForUpdateStatusGame() {


        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    int counter = 0;
                    while (!status) {
                        synchronized (this) {
                            Toast.makeText(getApplicationContext(), "Don't worry, pleas wait...", Toast.LENGTH_SHORT).show();
                            RequestParams param = new RequestParams();
                            param.put("game_id", game_Id);
                            invokeWSCheckAdmin(param);

                            if (counter == 20) {

                                Toast.makeText(getApplicationContext(), "Admin is not responding, try again", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            counter++;
                        }
                        if (status) {
                            finish();
                            Intent i = new Intent(getApplicationContext(), OnlineGameActivity.class);
                            i.putExtra("game_id", game_Id);
                            i.putExtra("user", user);
                            i.putExtra("color", color);
                            startActivity(i);
                        }


                    }


                } catch (Exception v) {
                    System.out.println(v);
                }

            }
        };

        Thread backgroud = new Thread(r);
        backgroud.start();
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
                    if (obj.getBoolean("status")) {
                        status = true;

                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), "Don't worry, pleas wait...", Toast.LENGTH_SHORT).show();
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

}



