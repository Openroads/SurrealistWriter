package pmd.ubi.pt.surrealistwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pmd.ubi.pt.objects.User;

public class OnlineModMenuactivity extends AppCompatActivity
{
    private User user;
    TextView userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mod_menu);

        userView = (TextView)findViewById(R.id.userNameAndEmail);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        userView.setText("Wellcome " + user.toString());

    }

    public void rankingOnClick(View view)
    {
        Intent intent = new Intent(this,Ranking.class);
        startActivity(intent);
    }

    public void onlineModeOC(View view) {
        Intent intent = new Intent(this,OnlineMode.class);
        startActivity(intent);
    }

    public void settingsOnClick(View view)
    {
        Intent intent = new Intent(this, AllOfflineUsersActivity.class);
        startActivity(intent);
    }
}
