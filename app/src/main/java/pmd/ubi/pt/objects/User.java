package pmd.ubi.pt.objects;

import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Piotr on 30-Nov-16.
 */

public class User
{
    private String userName;
    private String email;
    private String hashedPassword;
    private Date creationDate;


    public User()
    {

    }

    public User(String _userName, String _email, String _password, Date _creationData)
    {
        this.userName = _userName;
        this.email = _email;
        this.hashedPassword = hashPassword(_password, _creationData);
        this.creationDate = _creationData;

    }

    //Utility finction
    private String bytesToHexString(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i< bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);

        }

        return sb.toString();
    }

    //Hashing Function
    private String hashPassword(String _password, Date _date)
    {

        String hash = "";

        String passwordDate = _password + _date;
        try
        {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(passwordDate.getBytes());

            hash = bytesToHexString(digest.digest());

        }
        catch(NoSuchAlgorithmException ex)
        {
            Log.v("Error", ex.getMessage().toString());
        }

        return hash;
    }

    //Getters
    public String getUserName()
    {
        return this.userName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getHashedPassword()
    {
        return this.hashedPassword;
    }

    //Setters
    public void setUserName(String _userName)
    {
        this.userName = _userName;
    }

    public void setEmail(String _email)
    {
        this.email = _email;
    }

    public void setHashedPassword(String _password, Date _currentDate)
    {
        this.hashedPassword = hashPassword(_password, _currentDate);
    }

    public String toString()
    {
        return this.userName + "\n" + this.email + "\n" + this.hashedPassword + "\n" + creationDate;
    }
}
