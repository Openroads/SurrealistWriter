package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.User;

public class OnlineGameActivity extends AppCompatActivity {

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
    ArrayList<String> alPlayers;
    ArrayList<Integer> alColors;
    String[] allStrings;
    int[] allStart;
    int[] allEnd;
    int[] allColors;
    int[] playerScoreSum;
    int[] playerColor;
    JSONObject infoObject;
    String[] last2Words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);


        try {
            initialize();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvComment   = (TextView) findViewById(R.id.tvComment);
        tvLastWords = (TextView) findViewById(R.id.tvLastWords);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvNumChars  = (TextView) findViewById(R.id.tvNumChars);
        etWord      = (EditText) findViewById(R.id.etWord);
        btEndTurn   = (Button)   findViewById(R.id.btEndTurn);


        tvNumChars.setText("0/"+iNumCharacters);
        tvLastWords.setMovementMethod(new ScrollingMovementMethod());
        tvLastWords.setText(last2Words[0]+last2Words[1]);

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




    }

    public void initialize() throws JSONException {

        RequestParams params = new RequestParams();
        params.add("user_id",user_id);
        params.add("game_id",game_id);
        invokeWS();

    }

    public void invokeWS(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/game/whonextturn", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);

                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status"))
                    {
                        infoObject = obj;
                        iNumRounds = infoObject.getInt("max_round");
                        iNumCharacters = infoObject.getInt("max_characters");
                        last2Words = infoObject.getString("last_words").split(" ");
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), "Color is  not available!", Toast.LENGTH_SHORT).show();
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



    public String getLastTwoWords(String s){
        String[] parts = s.split(" ");
        if(parts.length == 0)
            return "";
        else if(parts.length == 1)
            return "" + parts[parts.length-1];

        else
            return "" + parts[parts.length - 2] + " " + parts[parts.length - 1];
    }

    public void onClickEndTurn(View view){

        if(checkWordIsValid(etWord.getText().toString())) {
            if(turn == 0)
                setColor(tvLastWords,
                        getLastTwoWords(etWord.getText().toString()) + "... ",
                        getLastTwoWords(etWord.getText().toString()) + "... ",
                        alColors.get(currentPlayer));
            else
                setColor(tvLastWords,
                        getLastTwoWords(etWord.getText().toString()) + "... ",
                        getLastTwoWords(etWord.getText().toString()) + "... ",
                        alColors.get(currentPlayer));

            // Updates the arrays that form the formatted data

            etWord.setText(etWord.getText().toString() + " ");
            allStrings[turn]  = etWord.getText().toString();
            Log.d("DEBUG","Current size chars: "+currentSizeChars);
            allStart[turn]   = currentSizeChars;
            Log.d("DEBUG","String size: "+etWord.getText().toString().length());
            allEnd[turn]     = etWord.getText().toString().length();
            Log.d("DEBUG","Color Int: "+alColors.get(currentPlayer));
            //Get total characters and sum it to the respective player
            playerScoreSum[currentPlayer] += allEnd[turn]-1;
            //Get color for each player
            playerColor[currentPlayer] = alColors.get(currentPlayer);
            allColors[turn]  = alColors.get(currentPlayer);
            currentSizeChars+=etWord.getText().toString().length();

            etWord.setText("");


            }

                tvRound.setText("Round " + round);
                etWord.getBackground().setColorFilter(alColors.get(currentPlayer), PorterDuff.Mode.SRC_IN);
                etWord.setTextColor(alColors.get(currentPlayer));
                btEndTurn.setBackgroundColor(alColors.get(currentPlayer));
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

