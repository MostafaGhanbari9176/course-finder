package ir.mahoorsoft.app.cityneed.view;

import android.widget.TextView;

/**
 * Created by M-gh on 09-Feb-18.
 */

public class CharCheck {

    public static String faCheck(String s) {
        char[] c = s.toCharArray();
        String s2 = "";
        for (int i = 0; i < s.length(); i++) {
        if(c[i] > 32 &&  c[i] <= 126 )
            continue;
            s2 = s2 + c[i];
        }
        return s2;
    }

    public static String numberCheck(String s) {
        char[] c = s.toCharArray();
        String s2 = "";
        for (int i = 0; i < s.length(); i++) {
            if(c[i] >= 48 &&  c[i] <= 57 )
                s2 = s2 + c[i];

        }
        return s2;
    }
}
