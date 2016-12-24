package pmd.ubi.pt.surrealistwriter;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GamePlay extends AppCompatActivity {

    int iNumPlayers;
    int iNumCharacters;
    int iGameMode;
    int turn=0;
    int currentPlayer = 0;
    TextView tvComment;
    TextView tvLastWords;
    TextView tvTurn;
    EditText etWord1;
    EditText etWord2;
    Button btEndTurn;
    ArrayList<String>  alPlayers;
    ArrayList<Integer> alColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        iNumPlayers    = getIntent().getExtras().getInt("numPlayers");
        iNumCharacters = getIntent().getExtras().getInt("numCharacters");
        iGameMode      = getIntent().getExtras().getInt("gameMode");
        alPlayers      = getIntent().getExtras().getStringArrayList("players");
        alColors       = getIntent().getExtras().getIntegerArrayList("colors");

        tvComment   = (TextView) findViewById(R.id.tvComment);
        tvLastWords = (TextView) findViewById(R.id.tvLastWords);
        tvTurn      = (TextView) findViewById(R.id.tvTurn);
        etWord1     = (EditText) findViewById(R.id.etWord1);
        etWord2     = (EditText) findViewById(R.id.etWord2);
        btEndTurn   = (Button)   findViewById(R.id.btEndTurn);

        etWord1.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        etWord2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


        if(iGameMode==1){
                tvLastWords.setText("");
                setColor(tvComment,
                        alPlayers.get(0) + " you're the first player. Insert two words to start the game!",
                        alPlayers.get(0),
                        alColors.get(0)
                        );
            etWord1.getBackground().setColorFilter(alColors.get(0), PorterDuff.Mode.SRC_IN);
            etWord2.getBackground().setColorFilter(alColors.get(0), PorterDuff.Mode.SRC_IN);
            etWord1.setTextColor(alColors.get(0));
            etWord2.setTextColor(alColors.get(0));
            btEndTurn.setBackgroundColor(alColors.get(0));

        }

        else{
                tvLastWords.setText("");
                currentPlayer = genRandInt();
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're the first player. Insert two words to start the game!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                etWord1.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord2.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord1.setTextColor(alColors.get(currentPlayer));
                etWord2.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
        }


    }

    public void onClickEndTurn(View view){

        if(checkWordIsValid(etWord1.getText().toString())
                && checkWordIsValid(etWord2.getText().toString())) {
            if(turn == 0)
                setColor(tvLastWords,
                        etWord1.getText().toString() + " " + etWord2.getText().toString() + " ... ",
                        etWord1.getText().toString()+" "+etWord2.getText().toString(),
                        alColors.get(currentPlayer));
            else
                setColor(tvLastWords,
                        " ... " + etWord1.getText().toString() + " " + etWord2.getText().toString() + " ... ",
                        etWord1.getText().toString()+" "+etWord2.getText().toString(),
                        alColors.get(currentPlayer));

            etWord1.setText("");
            etWord2.setText("");

            if (iGameMode == 1) {
                turn++;
                currentPlayer++;

                //Whenever the turn is a multiple of the number of players we reset the current player
                if ((turn) % iNumPlayers == 0)
                    currentPlayer = 0;

                //Changes player's name color on the comment text
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're up!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                tvTurn.setText("Turn " + (turn + 1));
                etWord1.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord2.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord1.setTextColor(alColors.get(currentPlayer));
                etWord2.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
            }

            else {
                turn++;
                currentPlayer = genRandInt();

                //Changes player's name color on the comment text
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're up!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                tvTurn.setText("Turn " + (turn + 1));
                etWord1.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord2.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord1.setTextColor(alColors.get(currentPlayer));
                etWord2.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
            }
        }

        else{
            Toast.makeText(this, "Invalid word!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkWordIsValid(String word){
        boolean flag = true;
        //Check if there's a word
        if(word.equals("")==true)
            flag = false;

        //Check if word is alphanumeric
        if(word.matches("^.*[^a-zA-Z0-9].*$")==true)
            flag = false;

        return flag;
    }


    private int genRandInt(){
        Random rm = new Random();
        return rm.nextInt(iNumPlayers);
    }

    private void setColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
