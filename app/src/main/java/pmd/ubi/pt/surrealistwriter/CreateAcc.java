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

import java.nio.charset.StandardCharsets;
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
    // Progress Dialog Object
    private ProgressDialog prgDialog;
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
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currdatestr = sdf.format(date);

        if(RegisterDataValidate.validateDataWithToast(username,email,password,this)) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        // Put Http parameter username with value of Email Edit View control
        params.put("email", email);
        // Put Http parameter password with value of Password Edit View control
        String hashpassword = Utility.hashPassword(password,date);
        params.put("password", hashpassword);
        // Put current date of creating account
        params.put("register_date", currdatestr);
        // Invoke RESTful Web Service with Http parameters
        invokeWS(params);
        }


    }
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.3.2:8080/SurrealistWriterRESTful/register/doregister",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // Hide Progress Dialog
                // prgDialog.hide();
                try {
                    // JSON Object
                    String str = new String(responseBody);
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        setDefaultValues();
                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
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
                // Hide Progress Dialog
                //prgDialog.hide();
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
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    //to login activity
   /* public void navigatetoRegisterActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),CreateAcc.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }*/
    private void setDefaultValues(){
        mUserNameView.setText("");
        mEmailView.setText("");
        mPasswordView.setText("");
    }
}
