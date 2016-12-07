package pmd.ubi.pt.surrealistwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import pmd.ubi.pt.objects.User;

public class CreateAcc extends AppCompatActivity
{

    //UI references
    private EditText mUserNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mRegisterButton;

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


    }

    public void registerClick(View v)
    {
        String username = mUserNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Date data = new Date();

        User newUser = new User(1, username, email, password, data);

        Toast.makeText(this, newUser.toString(), Toast.LENGTH_LONG).show();


    }
}
