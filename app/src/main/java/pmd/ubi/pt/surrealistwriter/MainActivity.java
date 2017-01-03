package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onlineModOnClick(View view){
        Intent onlineModeIntent = new Intent(this, LoginActivity.class);
        startActivity(onlineModeIntent);
    }

    public void offlineModeOnClick(View view){
        Intent offlineModeIntent = new Intent(this, OfflineModeMenuActivity.class);
        startActivity(offlineModeIntent);
    }

}
