package pmd.ubi.pt.Utilities;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by daren on 28.12.16.
 */

public class Utility {

    public static boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true: false;
    }

    public static Date StringFromDBToDate(String str_date) throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.parse(str_date);
    }
    public static String DateToDBString(Date date)
    {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //function to hash password
    private static String bytesToHexString(byte[] bytes)
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
    public static String hashPassword(String _password, Date _date)
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
}
