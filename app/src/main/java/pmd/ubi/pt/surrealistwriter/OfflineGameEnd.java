package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

public class OfflineGameEnd extends AppCompatActivity {

    TextView finalText;
    String fullString = "";
    Spannable strToSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        String  [] allStrings = getIntent().getExtras().getStringArray("allStrings");
        int [] allStart   = getIntent().getExtras().getIntArray("allStart");
        int [] allEnd     = getIntent().getExtras().getIntArray("allEnd");
        int [] allColors  = getIntent().getExtras().getIntArray("allColors");
        int numTurns = getIntent().getExtras().getInt("numTurns");

        finalText = (TextView) findViewById(R.id.tvFinalText);

        finalText.setText("");

        for(int i = 0; i<numTurns;i++)
            fullString+=allStrings[i];

        strToSpan = new SpannableString(fullString);

        for(int i = 0; i<numTurns;i++){
            setFinalTextColorInterval(allStart[i], allEnd[i], allColors[i]);
        }

        finalText.setText(strToSpan);
    }

    private void setFinalTextColorInterval(int start, int stringSize, int color){

        strToSpan.setSpan(new ForegroundColorSpan(color), start, start+stringSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
