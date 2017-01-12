package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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
        Intent i2 = new Intent(this, OnlineGameActivity.class);
        i2.putExtra("game_id", game_Id);
        i2.putExtra("user", user);
        i2.putExtra("color", color);
        User u = (User) i2.getSerializableExtra("user");;
        Log.d("DEBUG","ID B4 : "+u.getId());
        startActivity(i2);
        checkForUpdateStatusGame();

    }

    void checkForUpdateStatusGame(){


    }


}


