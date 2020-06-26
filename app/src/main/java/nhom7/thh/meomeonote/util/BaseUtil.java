package nhom7.thh.meomeonote.util;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseUtil {
    public static int getIdResource(Context context, String name, String defType, String packageName) {
        return context.getResources().getIdentifier(name, defType, packageName);
    }

    public static boolean compareDate(String date, String timer) {
        if (date == null || timer == null || date.equals("") || timer.equals(""))
            return false;
        return timer.endsWith(date);
    }

    public static String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
