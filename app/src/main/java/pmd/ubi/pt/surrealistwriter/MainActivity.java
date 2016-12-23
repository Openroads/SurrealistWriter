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

    public void login(View view){
        Intent loginIntent = new Intent(this, MainMenu.class);
        startActivity(loginIntent);
    }

    public void createAcc(View view){
        Intent loginIntent = new Intent(this, CreateAcc.class);
        startActivity(loginIntent);
    }

}
