package nhom7.thh.meomeonote.util;


import android.content.Context;

public class BaseUtil {
    public static int getIdResource(Context context, String name, String defType, String packageName) {
        return context.getResources().getIdentifier(name, defType, packageName);
    }
}
