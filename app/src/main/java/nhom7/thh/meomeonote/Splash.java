package nhom7.thh.meomeonote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);  //Delay of 3 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(Splash.this,
                            MainActivity.class);
//                    Intent i = new Intent(Splash.this,
//                            TestTimePicker.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}