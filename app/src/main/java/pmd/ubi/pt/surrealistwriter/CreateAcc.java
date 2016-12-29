package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pmd.ubi.pt.LocalDatabase.DateTimeConverter;
import pmd.ubi.pt.LocalDatabase.GameRepository;
import pmd.ubi.pt.LocalDatabase.GameUserRepository;
import pmd.ubi.pt.LocalDatabase.OfflineUserRepository;
import pmd.ubi.pt.objects.Game;
import pmd.ubi.pt.objects.OfflineUser;
import pmd.ubi.pt.objects.User;

public class CreateAcc extends AppCompatActivity
{

    //UI references
    private EditText mUserNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mRegisterButton;

    //Repository
    private OfflineUserRepository offlineUserRepository;
    private GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        //Set up registration form
        mUserNameView = (EditText) findViewById(R.id.reg_username);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.reg_email);
        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mRegisterButton = (Button) findViewById(R.id.registration_button);

        this.offlineUserRepository = new OfflineUserRepository(this);
        this.gameRepository = new GameRepository(this);

    }

    public void registerClick(View v)
    {
        /********** Testowanie **********/

        /*
        String username = mUserNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Date data = new Date();

        User newUser = new User(1, username, email, password, data);

        OfflineUser offUser = offlineUserRepository.createOfflineUser("Piotr", 0);

        Toast.makeText(this, offUser.toString(), Toast.LENGTH_LONG).show();
        Date date = new Date();

        Game game = gameRepository.createGame(3, "Dupa", DateTimeConverter.dateToString(data), 5);
        //Toast.makeText(this, game.toString(), Toast.LENGTH_LONG).show();

        List<Game> games = new ArrayList<Game>(gameRepository.getAllGames());

        for(Game gami : games) {
            Toast.makeText(this, gami.toString(), Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(this, newUser.toString(), Toast.LENGTH_LONG).show();
*/

    }
}
