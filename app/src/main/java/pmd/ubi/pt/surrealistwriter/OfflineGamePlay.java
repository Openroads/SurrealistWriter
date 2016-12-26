package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class OfflineGamePlay extends AppCompatActivity {

    int iNumPlayers;
    int iNumRounds;
    int iNumCharacters;
    int iGameMode;
    int turn=0;
    int subTurn=0;
    int round=1;
    int currentPlayer       = 0;
    int currentSizeChars    = 0;
    int sizeOfCurrentString = 0;
    String playerSequence = "";
    TextView tvComment;
    TextView tvLastWords;
    TextView tvRound;
    TextView tvNumChars;
    TextView finalText;
    EditText etWord;
    EditText etWord2;
    Button btEndTurn;
    ArrayList<String>  alPlayers;
    ArrayList<Integer> alColors;
    String[] allStrings;
    int[] allStart;
    int[] allEnd;
    int[] allColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        iNumPlayers    = getIntent().getExtras().getInt("numPlayers");
        iNumRounds     = getIntent().getExtras().getInt("numRounds");
        iNumCharacters = getIntent().getExtras().getInt("numCharacters");
        iGameMode      = getIntent().getExtras().getInt("gameMode");
        alPlayers      = getIntent().getExtras().getStringArrayList("players");
        alColors       = getIntent().getExtras().getIntegerArrayList("colors");

        allStrings = new String[iNumPlayers*iNumRounds];
        allStart   = new int[iNumPlayers*iNumRounds];
        allEnd     = new int[iNumPlayers*iNumRounds];
        allColors  = new int[iNumPlayers*iNumRounds];

        tvComment   = (TextView) findViewById(R.id.tvComment);
        tvLastWords = (TextView) findViewById(R.id.tvLastWords);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvNumChars  = (TextView) findViewById(R.id.tvNumChars);
        etWord      = (EditText) findViewById(R.id.etWord);
        btEndTurn   = (Button)   findViewById(R.id.btEndTurn);


        tvNumChars.setText("0/"+iNumCharacters);
        tvLastWords.setMovementMethod(new ScrollingMovementMethod());

        etWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        etWord.setFilters(new InputFilter[] {new InputFilter.LengthFilter(iNumCharacters)});

        etWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sizeOfCurrentString = etWord.getText().toString().length();
                tvNumChars.setText(""+sizeOfCurrentString+"/"+iNumCharacters);
            }
        });

        if(iGameMode==1){
                tvLastWords.setText("");
                setColor(tvComment,
                        alPlayers.get(0) + " you're the first player. Insert two words to start the game!",
                        alPlayers.get(0),
                        alColors.get(0)
                        );
            etWord.getBackground().setColorFilter(alColors.get(0), PorterDuff.Mode.SRC_IN);
            etWord.setTextColor(alColors.get(0));
            btEndTurn.setBackgroundColor(alColors.get(0));

        }

        else{
                tvLastWords.setText("");
                playerSequence = genPlayerOrder();
                currentPlayer = Integer.parseInt(""+playerSequence.charAt(subTurn));;
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're the first player. Insert two words to start the game!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                etWord.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
        }


    }

    public void onClickEndTurn(View view){

        if(checkWordIsValid(etWord.getText().toString())) {
            if(turn == 0)
                setColor(tvLastWords,
                        etWord.getText().toString() + " ... ",
                        etWord.getText().toString(),
                        alColors.get(currentPlayer));
            else
                setColor(tvLastWords,
                        etWord.getText().toString(),
                        etWord.getText().toString(),
                        alColors.get(currentPlayer));

            // Updates the arrays that form the formatted data

            etWord.setText(etWord.getText().toString() + " ");
            allStrings[turn]  = etWord.getText().toString();
            Log.d("DEBUG","Current size chars: "+currentSizeChars);
            allStart[turn]   = currentSizeChars;
            Log.d("DEBUG","String size: "+etWord.getText().toString().length());
            allEnd[turn]     = etWord.getText().toString().length();
            Log.d("DEBUG","Color Int: "+alColors.get(currentPlayer));
            allColors[turn]  = alColors.get(currentPlayer);
            currentSizeChars+=etWord.getText().toString().length();

            etWord.setText("");

            if (iGameMode == 1) {
                turn++;
                subTurn++;
                currentPlayer++;

                //Whenever the turn is a multiple of the number of players we reset the current player
                //and increase the current round
                if ((turn) % iNumPlayers == 0) {
                    currentPlayer = 0;
                    subTurn = 0;
                    round++;
                }
                //Changes player's name color on the comment text
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're up!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                tvRound.setText("Round " + round);
                etWord.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
            }

            else {
                turn++;
                subTurn++;
                currentPlayer++;

                if ((turn) % iNumPlayers == 0) {
                    playerSequence = genPlayerOrder();
                    subTurn = 0;
                    round++;
                }



                currentPlayer = Integer.parseInt(""+playerSequence.charAt(subTurn));
                Log.d("Position: ",""+(turn/round));

                //Changes player's name color on the comment text
                setColor(tvComment,
                        alPlayers.get(currentPlayer) + " you're up!",
                        alPlayers.get(currentPlayer),
                        alColors.get(currentPlayer)
                );
                tvRound.setText("Round " + round);
                etWord.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
            }
        }

        else{
            Toast.makeText(this, "Invalid word!", Toast.LENGTH_SHORT).show();
        }

        if(round > iNumRounds) {
            Log.d("Debug", "Game ended!");
            Intent intent = new Intent(this, OfflineGameEnd.class);
            intent.putExtra("allStrings",allStrings);
            intent.putExtra("allStart",allStart);
            intent.putExtra("allEnd",allEnd);
            intent.putExtra("allColors",allColors);
            intent.putExtra("numTurns",turn);
            startActivity(intent);
        }


    }

    private boolean checkWordIsValid(String word){
        boolean flag = true;
        //Check if there's a word
        if(word.equals("")==true)
            flag = false;

        //Check if word is alphanumeric and only uses basic punctuation
        if(word.matches("^.*[^a-zA-Z0-9 ,.!?\"-\'()].*$")==true)
            flag = false;

        return flag;
    }


    private int genRandInt(){
        Random rm = new Random();
        return rm.nextInt(iNumPlayers);
    }


    private String genPlayerOrder(){
        String s = "";
        int generatedInt;
        Random rm = new Random();
        for(int i=0;i<iNumPlayers;i++){
            do {
                generatedInt = rm.nextInt(iNumPlayers);
            }while(s.contains(""+generatedInt));
            s+=generatedInt;
            Log.d("Generated Int: ",""+generatedInt);
        }

        return s;
    }


    private void setColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setFinalTextColorInterval(String string, int start, int end, int color){
        finalText.setText(finalText.getText().toString()+string);
        Spannable str = (Spannable) finalText.getText();
        str.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        currentSizeChars = finalText.getText().toString().length();
    }
}
