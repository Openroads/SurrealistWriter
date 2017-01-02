package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;
import pmd.ubi.pt.LocalDatabase.RankingRepository;

public class OfflineGameEnd extends AppCompatActivity {

    TextView finalText;
    TextView gameScore;
    String fullString = "";
    Spannable strToSpan;
    private OfflineUserRepository offlineUserRepository;
    private RankingRepository rankingRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        this.offlineUserRepository = new OfflineUserRepository(this);
        this.rankingRepository     = new RankingRepository(this);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout scoreLayout = (LinearLayout) findViewById(R.id.scoreLayout);

        String  [] allStrings         = getIntent().getExtras().getStringArray("allStrings");
        int [] allStart               = getIntent().getExtras().getIntArray("allStart");
        int [] allEnd                 = getIntent().getExtras().getIntArray("allEnd");
        int [] allColors              = getIntent().getExtras().getIntArray("allColors");
        int [] playerScore            = getIntent().getExtras().getIntArray("playerScore");
        int [] playerColor            = getIntent().getExtras().getIntArray("playerColors");
        ArrayList<String> players     = getIntent().getExtras().getStringArrayList("players");
        int numTurns                  = getIntent().getExtras().getInt("numTurns");
        int numRounds                 = getIntent().getExtras().getInt("numRounds");

        int numPlayers = numTurns/numRounds;

        TextView [] scoreTextViewArray = new TextView[numPlayers];


        finalText = (TextView) findViewById(R.id.tvFinalText);
       // gameScore = (TextView) findViewById(R.id.tvScore);
        finalText.setText("");

        for(int i = 0; i<numTurns;i++)
            fullString+=allStrings[i];

        strToSpan = new SpannableString(fullString);

        for(int i = 0; i<numTurns;i++){
            setFinalTextColorInterval(allStart[i], allEnd[i], allColors[i]);
        }

        finalText.setText(strToSpan);

        for(int i=0; i<numPlayers;i++) {
            Log.d("DEBUG","I: "+i);
            Log.d("DEBUG","Player: "+players.get(i));
            //Log.d("DEBUG","User ID: "+offlineUserRepository.getOfflineUserByString(players.get(i)).getUserId());
                rankingRepository.updateRanking(
                        offlineUserRepository.getOfflineUserByString(players.get(i)).getUserId(),
                        playerScore[i]
                );

            strToSpan = new SpannableString(players.get(i)+": "+playerScore[i]);
            scoreTextViewArray[i] = new TextView(this);
            setFinalTextColorInterval(0,(players.get(i)+": "+playerScore[i]).length(),playerColor[i]);
            scoreTextViewArray[i].setText(strToSpan);
            scoreTextViewArray[i].setLayoutParams(lp);
            scoreTextViewArray[i].setId(i);
            scoreLayout.addView(scoreTextViewArray[i]);
        }
    }

    private int [] getPlayerScoreArray(int numTurns, int numPlayers, String [] strings){

        int currPlayer = 0;
        int [] playerScores = new int[numPlayers];

        for(int i=0;i<numTurns;i++,currPlayer++){
            if(i % numPlayers == 0)
                currPlayer = 0;
            playerScores[currPlayer] += strings[i].length()-1;
        }

        return playerScores;
    }

    private int [] getPlayerColorArray(int numPlayers, int [] colors){

        int [] playerColors = new int[numPlayers];

        for(int i=0; i<numPlayers; i++){
            playerColors[i] = colors[i];
        }

        return playerColors;
    }

    private int [] sortPlayerScoreArray(int [] array){
        int temp = 0;

        for(int i=0;i<array.length;i++)
            for(int j=0;j<(array.length-1);j++){
                if(array[j-1] < array[j]){
                    temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
            }

        return array;
    }

    private void setFinalTextColorInterval(int start, int stringSize, int color){

        strToSpan.setSpan(new ForegroundColorSpan(color), start, start+stringSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
