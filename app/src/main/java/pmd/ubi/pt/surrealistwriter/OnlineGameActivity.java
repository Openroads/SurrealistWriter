package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import pmd.ubi.pt.Utilities.ConstantVariables;
import pmd.ubi.pt.objects.User;

public class OnlineGameActivity extends AppCompatActivity {


    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            RequestParams param = new RequestParams();
            param.put("game_id", String.valueOf(gameID));
            param.put("user_id", String.valueOf(user.getId()));

            invokeWS(param);


        }
    };




    private boolean isMyTurn;
    private User user;
    private int gameID;
    private int color;


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
    int prevColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");
        gameID = (Integer)i.getSerializableExtra("game_id");
        color = (Integer)i.getSerializableExtra("color");

        Log.d("DEBUG","ID AFTER : "+user.getId());

        try {
            initialize();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvComment   = (TextView) findViewById(R.id.tvComment2);
        tvLastWords = (TextView) findViewById(R.id.tvLastWords2);
        tvRound     = (TextView) findViewById(R.id.tvRound2);
        tvNumChars  = (TextView) findViewById(R.id.tvNumChars2);
        etWord      = (EditText) findViewById(R.id.etWord2);
        btEndTurn   = (Button)   findViewById(R.id.btEndTurn2);






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
        Intent i = getIntent();
        User u = (User) i.getSerializableExtra("user");;
        Log.d("DEBUG","TEST ID: "+u.getId());

        checkIfMyTurn();

        /*
        params.add("user_id",String.valueOf(u.getId()));
        params.add("game_id",String.valueOf(getIntent().getExtras().getInt("game_id")));
        invokeWS(params);

        */

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
                        isMyTurn = true;
                        Log.d("DEBUG","ENTERED STATUS");
                        infoObject = obj;
                        iNumRounds = infoObject.getInt("max_round");
                        iNumCharacters = infoObject.getInt("max_characters");
                        String s = infoObject.getString("last_words");
                        Log.d("DEBUG","STRING "+s);
                        if(s.length()==0 || s == null) {
                            last2Words[0] = "";
                            last2Words[1] = "";
                        }
                        else
                            last2Words = s.split(" ");
                        prevColor = infoObject.getInt("color");
                        tvNumChars.setText("0/"+iNumCharacters);
                        tvLastWords.setMovementMethod(new ScrollingMovementMethod());
                        tvLastWords.setText(last2Words[0]+last2Words[1]);
                        Log.d("DEBUG","Last: "+last2Words[0]+" "+last2Words[1]);
                        etWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                        etWord.setFilters(new InputFilter[] {new InputFilter.LengthFilter(iNumCharacters)});

                        tvLastWords.setVisibility(View.VISIBLE);
                        etWord.setVisibility(View.VISIBLE);
                        btEndTurn.setVisibility(View.VISIBLE);
                        tvNumChars.setVisibility(View.VISIBLE);
                        tvRound.setVisibility(View.VISIBLE);
                    }
                    // Else display error message
                    else {
                        Log.d("DEBUG","DIDNT ENTER STATUS");
                        tvComment.setText("Please wait for your turn");
                        tvLastWords.setVisibility(View.INVISIBLE);
                        etWord.setVisibility(View.INVISIBLE);
                        btEndTurn.setVisibility(View.INVISIBLE);
                        tvNumChars.setVisibility(View.INVISIBLE);
                        tvRound.setVisibility(View.INVISIBLE);

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
            RequestParams params = new RequestParams();
            Intent i = getIntent();
            User u = (User) i.getSerializableExtra("user");;
            Log.d("DEBUG","TEST ID: "+u.getId());
            params.add("user_id",String.valueOf(u.getId()));
            params.add("game_id",String.valueOf(getIntent().getExtras().getInt("game_id")));
            params.put("color",  String.valueOf(getIntent().getExtras().getInt("color")));
            params.put("words",etWord.getText().toString());
            invokeWSForEndTurn(params);

            //Change turn



            //Waiting for your turn
            checkIfMyTurn();


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


    private void checkIfMyTurn()
    {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){

                while(!isMyTurn)
                {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(isMyTurn)
                {

                }


            }
        });
        thread.start();
    }

    public void invokeWSForEndTurn(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstantVariables.ServiceConnectionString + "/game/nextturn", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);

                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {

                    }
                    // Else display error message
                    else {

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


