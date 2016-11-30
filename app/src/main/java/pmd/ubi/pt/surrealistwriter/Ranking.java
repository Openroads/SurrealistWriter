package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class Ranking extends AppCompatActivity {
    private ListView rankLV;
    private Button onlineRankBT,offlineRankBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        rankLV = (ListView) findViewById(R.id.ranking_listView);
        onlineRankBT  = (Button) findViewById(R.id.online_rank);
        offlineRankBT = (Button) findViewById(R.id.offline_button);
    }
}
