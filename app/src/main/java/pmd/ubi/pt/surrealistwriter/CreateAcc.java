package pmd.ubi.pt.surrealistwriter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import pmd.ubi.pt.Utilities.RegisterDataValidate;
import pmd.ubi.pt.Utilities.Utility;

public class CreateAcc extends AppCompatActivity
{

    //UI references
    private EditText mUserNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        //Set up registration form
        mUserNameView = (EditText) findViewById(R.id.reg_username);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.reg_email);
        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mErrorView = (TextView) findViewById(R.id.error_regist_TW);

    }

    public void registerClick(View v)
    {
        String username = mUserNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        Date date = new Date();
        String currdatestr = Utility.DateToDBString(date);

        if(RegisterDataValidate.validateDataWithToast(username,email,password,this)) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("email", email);
        String hashpassword = Utility.hashPassword(password,date);
        params.put("password", hashpassword);
        params.put("register_date", currdatestr);
        invokeWS(params);
        }


    }
    public void invokeWS(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.3.2:8080/SurrealistWriterRESTful/register/doregister",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        setDefaultValues();
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                        navigateToLogActivity();
                    }
                    // Else display error message
                    else{
                        mErrorView.setText(obj.getString("error_msg"));
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
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void navigateToLogActivity(){
        finish();
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);
    }

    private void setDefaultValues(){
        mUserNameView.setText("");
        mEmailView.setText("");
        mPasswordView.setText("");
    }

    public void signInOC(View view) {
        navigateToLogActivity();
    }
}
