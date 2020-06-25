package nhom7.thh.meomeonote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i = new Intent(context, ReminderService.class);
//        Toast.makeText(context, "haaaaa", Toast.LENGTH_LONG).show();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Log.v("receiver", title);
        i.putExtra("title", title);
        i.putExtra("content", content);
        context.startService(i);
    }
}
