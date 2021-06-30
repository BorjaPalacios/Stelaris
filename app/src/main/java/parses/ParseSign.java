package parses;

import android.content.Context;

import com.example.stelaris.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.StringException;

public class ParseSign {

    public static void parseUserName(Context context, String username) throws StringException {

        if (username == null || username.trim().isEmpty()) {
            throw new StringException(context.getString(R.string.userNameEmpty));
        } else if (username.length() < 6 || username.length() > 12) {
            throw new StringException(context.getString(R.string.userNameLength));
        } else if (checkSpecialCharacter(username)) {
            throw new StringException(context.getString(R.string.userNameSpecials));
        }
    }

    private static boolean checkSpecialCharacter(String string) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9]");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }
}
