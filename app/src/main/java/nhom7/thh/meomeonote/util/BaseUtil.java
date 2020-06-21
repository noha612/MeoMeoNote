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

    public static String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
}
