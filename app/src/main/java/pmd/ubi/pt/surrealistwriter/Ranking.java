package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;
import pmd.ubi.pt.LocalDatabase.RankingRepository;
import pmd.ubi.pt.objects.OfflineUser;
import pmd.ubi.pt.objects.RankDisplay;

public class Ranking extends AppCompatActivity {
    private ListView rankLV;
    private Button onlineRankBT,offlineRankBT;
    private RankingRepository rankingRepository;
    private OfflineUserRepository offlineUserRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        rankLV = (ListView) findViewById(R.id.ranking_listView);
        onlineRankBT  = (Button) findViewById(R.id.online_rank);
        offlineRankBT = (Button) findViewById(R.id.offline_button);
        rankingRepository = new RankingRepository(this);
        offlineUserRepository = new OfflineUserRepository(this);
    }


    public void offlineRankingOnClick(View view)
    {
        List<pmd.ubi.pt.objects.Ranking> ranking = new ArrayList<pmd.ubi.pt.objects.Ranking>();
        List<RankDisplay> usersInRanking = new ArrayList<RankDisplay>();
        ranking = rankingRepository.getAllRanking();

        for(pmd.ubi.pt.objects.Ranking rank:ranking)
        {
            if(rank != null)
            {
                OfflineUser offUser = offlineUserRepository.getOfflineUserById(rank.getUserId());
                RankDisplay rankDisp = new RankDisplay(offUser.getUserName(), rank.getScore());
                usersInRanking.add(rankDisp);
            }
        }

        Collections.sort(usersInRanking);
        ArrayAdapter<RankDisplay> offlineUserArrayAdapter = new ArrayAdapter<RankDisplay>(this, android.R.layout.simple_list_item_1, usersInRanking);
        rankLV.setAdapter(offlineUserArrayAdapter);


    }
}
