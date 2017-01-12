package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.CurrentRoomobject;
import pmd.ubi.pt.objects.User;

public class WaitingActivity extends AppCompatActivity
{
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            RequestParams param = new RequestParams();
            param.put("game_id", String.valueOf(game_Id));
            invokeWSCheckAdmin(param);
            Toast.makeText(getApplicationContext(), "Don't worry, pleas wait...", Toast.LENGTH_SHORT).show();

        }
    };

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

        try {
            checkForUpdateStatusGame();
            //finish();
           /* Intent in = new Intent(getApplicationContext(), OnlineColorCheckActivity.class);
            i.putExtra("game_id", game_Id);
            i.putExtra("user", user);
            i.putExtra("color", color);
            startActivity(in);*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    private void checkForUpdateStatusGame() throws InterruptedException {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){

                while(!status)
                {

                    /*RequestParams param = new RequestParams();
                    param.put("game_id", String.valueOf(game_Id));
                    invokeWSCheckAdmin(param);
                    Toast.makeText(getApplicationContext(), "Don't worry, pleas wait...", Toast.LENGTH_SHORT).show();

                    */

                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(status)
                {
                    finish();
                    Intent i = new Intent(getApplicationContext(), OnlineGameActivity.class);
                    i.putExtra("game_id", game_Id);
                    i.putExtra("user", user);
                    i.putExtra("color", color);
                    startActivity(i);
                }


            }
        });
        thread.start();

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
                    //Toast.makeText(getApplicationContext(), "Don't worry, pleas wait...", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {


                Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                finish();
            }
        }

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            // When Http response code is '404'
            if (statusCode == 404) {
                Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                finish();
            }
            // When Http response code is '500'
            else if (statusCode == 500) {
                Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                finish();
            }
            // When Http response code other than 404, 500
            else {
                Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    });

}

}



