package pmd.ubi.pt.LocalDatabase;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Piotr on 05-Dec-16.
 */

public class DateTimeConverter
{
    public static Date stringToDate(String dateString)
    {
        Date date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try{
            date = df.parse(dateString);
        }
        catch ( Exception ex ){
            Log.v("Error of conversion", ex.toString());
        }
        return date;
    }

    public static String dateToString(Date date)
    {
        String data = "";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try{
            data = df.format(date);
        }
        catch ( Exception ex ){
            Log.v("Error of conversion", ex.toString());
        }
        return data;
    }
}
