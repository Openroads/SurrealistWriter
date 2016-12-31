package pmd.ubi.pt.objects;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.jar.Pack200;

/**
 * Created by Piotr on 30-Nov-16.
 */

public class User implements Serializable
{
    private long id;
    private String userName;
    private String email;
    private String hashedPassword;
    private Date creationDate;


    public User()
    {

    }

    public User(long _id, String _userName, String _email, String _hashedPassword, Date _creationData)
    {
        this.id = _id;
        this.userName = _userName;
        this.email = _email;
        this.hashedPassword = _hashedPassword;
        this.creationDate = _creationData;

    }


    //Getters
    public long getId()
    {
        return this.id;
    }
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
    public void setId(long _id)
    {
        this.id = _id;
    }
    public void setUserName(String _userName)
    {
        this.userName = _userName;
    }

    public void setEmail(String _email)
    {
        this.email = _email;
    }

    public void setHashedPassword(String _password)
    {
        this.hashedPassword = _password;
    }

    public String toString()
    {
        return this.email;
    }
}
