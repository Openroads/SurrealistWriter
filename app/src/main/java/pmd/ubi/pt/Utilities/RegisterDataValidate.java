package pmd.ubi.pt.Utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by daren on 28.12.16.
 */

public class RegisterDataValidate {
    public static boolean validateDataWithToast(String uname, String email, String password, Context context)
    {
        if(Utility.isNotNull(uname) && Utility.isNotNull(email) && Utility.isNotNull(password)){
            if(uname.length()<=6)
            {
                Toast.makeText(context, "User name must has at least 6 characters ", Toast.LENGTH_SHORT).show();
                return false;
            }else if(!EmailValidator.validateEmail(email))
            {
                Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(password.length() <=7)
            {
                Toast.makeText(context, "Password must has at least 7 characters ", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{
            Toast.makeText(context, "Please fill the form, don't leave any field blank. ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
