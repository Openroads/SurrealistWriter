package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Vector;

import petrov.kristiyan.colorpicker.ColorPicker;
import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;
import pmd.ubi.pt.LocalDatabase.RankingRepository;


public class OfflinePlayerName extends AppCompatActivity {

    int iNumPlayers;
    int iNumRounds;
    int iNumCharacters;
    int iGameMode;
    int currentPos;

    private OfflineUserRepository offlineUserRepository;
    private RankingRepository rankingRepository;

    private EditText[] mEditTextPlayers;
    private Button[] mColorButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        this.offlineUserRepository = new OfflineUserRepository(this);
        this.rankingRepository     = new RankingRepository(this);

        iNumPlayers    = getIntent().getExtras().getInt("numPlayers");
        iNumRounds     = getIntent().getExtras().getInt("numRounds");
        iNumCharacters = getIntent().getExtras().getInt("numCharacters");
        iGameMode      = getIntent().getExtras().getInt("gameMode");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.editTextGroupLayout);
        generateEditTexts(linearLayout, iNumPlayers);

    }

    public void generateEditTexts(LinearLayout linearLayout, final int numPlayers){

        mEditTextPlayers          = new EditText[numPlayers];
        LinearLayout lineLayout[] = new LinearLayout[numPlayers];
        mColorButtons             = new Button[numPlayers];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        for(int i=0;i<numPlayers;i++){
            currentPos = i;
            lineLayout[i]       = new LinearLayout(this);
            lineLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            mEditTextPlayers[i] = new EditText(this);
            mEditTextPlayers[i].setLayoutParams(params);
            mEditTextPlayers[i].setGravity(Gravity.CENTER);
            mEditTextPlayers[i].setHint("Player "+(i+1));
            mEditTextPlayers[i].setMaxLines(1);
            mEditTextPlayers[i].setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            lineLayout[i].addView(mEditTextPlayers[i]);
            mColorButtons[i] = new Button(this);
            mColorButtons[i].setLayoutParams(params);
            mColorButtons[i].setId(i);
            mColorButtons[i].setHint("Set Color");
            mColorButtons[i].setBackgroundColor(0);
            mColorButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final View tempView = view;
                    ColorPicker colorPicker = new ColorPicker(OfflinePlayerName.this);
                    colorPicker.show();
                    Log.d("DEBUG","Prev color: "+mColorButtons[tempView.getId()].getBackground());
                    colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                        @Override
                        public void onChooseColor(int position,int color) {
                            boolean flag = true;
                            int btColor;
                            Drawable btBackground;
                            for(int i=0;i<numPlayers;i++){
                                btBackground = mColorButtons[i].getBackground();
                                btColor = ((ColorDrawable) btBackground).getColor();
                                if(color == btColor) {
                                    flag = false;
                                }
                            }
                            if(flag == false){
                                Toast.makeText(OfflinePlayerName.this, "Color already selected",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            mColorButtons[tempView.getId()].setBackgroundColor(color);
                            mColorButtons[tempView.getId()].setHint("Color Set");
                        }

                        @Override
                        public void onCancel(){
                            // put code
                        }
                    });
                }
            });
            lineLayout[i].addView(mColorButtons[i]);
            linearLayout.addView(lineLayout[i]);
        }

    }

    public void onClickPlayGame(View view){
        boolean flag = true;
        for(int i=0;i<iNumPlayers;i++)
            for(int j=0;j<iNumPlayers;j++){
                if(i==j)
                    continue;
                if(mEditTextPlayers[i].getText().toString().equals("")){
                    Toast.makeText(this, "Every player must have a name!", Toast.LENGTH_SHORT).show();
                    continue;
                }
                if(mEditTextPlayers[i].getText().toString()
                        .equals(mEditTextPlayers[j].getText().toString())) {
                    Toast.makeText(this, "Name already chosen for this game!", Toast.LENGTH_SHORT).show();
                    Log.d("Debug",""+ i+" "+j);
                    flag = false;
                }



            }

        for(int i=0;i<iNumPlayers;i++) {
            Drawable btBackground = mColorButtons[i].getBackground();
            int btColor = ((ColorDrawable) btBackground).getColor();

            if (btColor == 0) {
                Toast.makeText(this, "You must select a color!", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        }


            if(flag == false)
                Log.d("Debug","Won't jump to next activity!");
            else{
                Log.d("Debug","Will jump to next activity!");

                Vector<String>  players = new Vector<>();
                Vector<Integer> colors  = new Vector<>();
                for(int i=0; i<iNumPlayers; i++){
                    players.insertElementAt(mEditTextPlayers[i].getText().toString(),i);
                    Drawable btBackground = mColorButtons[i].getBackground();
                    colors.insertElementAt(((ColorDrawable) btBackground).getColor(),i);
                }

                for(int i=0;i<iNumPlayers;i++){
                    if(offlineUserRepository.checkUsernameExists(mEditTextPlayers[i].getText().toString())==false){
                        offlineUserRepository.createOfflineUser(players.get(i), 1);
                        rankingRepository.createRanking(
                                offlineUserRepository.getOfflineUserByString(players.get(i)).getUserId(),
                                0
                        );
                    }
                }

                Intent intent = new Intent(this, OfflineGamePlay.class);
                intent.putExtra("numPlayers",iNumPlayers);
                intent.putExtra("numRounds",iNumRounds);
                intent.putExtra("numCharacters", iNumCharacters);
                intent.putExtra("gameMode", iGameMode);
                intent.putExtra("players",players);
                intent.putExtra("colors",colors);
                startActivity(intent);
            }

    }
}
