package pmd.ubi.pt.surrealistwriter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GameSettings extends AppCompatActivity {


    EditText oNumPlayers;
    EditText oNumWords;

    int iNumPlayers;
    int iNumWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);


    }

    public void onClickPlay(View view){
        if(checkEditText()==false)
            return;
        oNumPlayers = (EditText) findViewById(R.id.ETNumPlayers);
        oNumWords   = (EditText) findViewById(R.id.ETNumWords);
        iNumPlayers = Integer.parseInt(oNumPlayers.getText().toString());
        iNumWords   = Integer.parseInt(oNumWords.getText().toString());


        Intent intent = new Intent(this, GamePlay.class);
        intent.putExtra("numPlayers",iNumPlayers);
        intent.putExtra("numWords",iNumWords);
        startActivity(intent);
    }

    public boolean checkEditText(){
        Boolean flag = false;
        oNumPlayers = (EditText) findViewById(R.id.ETNumPlayers);
        oNumWords   = (EditText) findViewById(R.id.ETNumWords);
        if(oNumPlayers.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of players!", Toast.LENGTH_SHORT).show();
            return flag;
        }
        if(oNumWords.getText().toString().isEmpty()){
            Toast.makeText(this,"You must specify the number of words!", Toast.LENGTH_SHORT).show();
            return flag;
        }

        iNumPlayers = Integer.parseInt(oNumPlayers.getText().toString());
        iNumWords = Integer.parseInt(oNumWords.getText().toString());


        // Checking Number of Players

            if(iNumPlayers>0)
                if(iNumPlayers<=8)
                    flag = true;
                else
                    Toast.makeText(this,"Maximum of 8 players!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Minimum of 1 player!", Toast.LENGTH_SHORT).show();

        // Checking Number of Words

            if(iNumWords>=iNumPlayers*2)
                if(iNumWords<=1024)
                    flag = true;
                else {
                    Toast.makeText(this, "Maximum of 1024 Words!", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            else {
                Toast.makeText(this, "Minimum of " + iNumPlayers * 2 + " words!", Toast.LENGTH_SHORT).show();
                flag = false;
            }

        return flag;
    }
}
