package bjfu.it.jia.stopwatch;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        // Run the method here! OR Multiple runTimer() method is run, making time flowing times faster.
        runTimer();

        // Set phone status listening. TelephonyManager <- this.getSystemService(Context.TELEPHONY_SERVICE)
        TelephonyManager telephonyMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        running = wasRunning;
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        running = wasRunning;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

/*
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }
*/

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }
    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        TextView timeView = (TextView) findViewById(R.id.time_view);
        timeView.setText("0:00:00");
    }

    // An intent will activate onPause() -> onResume() lifecycle automatically.
    public void onClickIntent(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, R.string.intent_text);
        startActivity(intent);
    }

    public void onClickAlertDialog(View view) {
        FireMissileDialogFragment fireMissileDialogFragment = new FireMissileDialogFragment();
        // show() written in class FireMissileDialogFragment. Two anonymous call-back functions included.
        /* FATAL ERROR: cannot pass para @StringRes int. More details see FireMissileDialogFragment.java
         * 2018.10.24 */
        /* SOLVED: add package implementation to `build.gradle`, and import. Add context as para is must.
         * 2018.10.24 */
        fireMissileDialogFragment.show(this, R.string.dialog_fire_missile_title, R.string.dialog_fire_missile_message,
                new DialogInterface.OnClickListener() {
                    // Every call-back function should override onClick(DialogInterface dialog, int which) function.
                    // DialogInterface points to the dialog interacting with, which points the button selected.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.fire_result, Toast.LENGTH_SHORT).show();
                        onResume();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.cancel_result, Toast.LENGTH_SHORT).show();
                        onResume();
                    }
                }, getFragmentManager());
        onPause();
    }

    // Listen Phone Call State(extends PhoneStateListener)
    class TeleListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                // case nothing
                case TelephonyManager.CALL_STATE_IDLE: {
                    onResume();
                    break;
                }
                // case telephone ringing
                case TelephonyManager.CALL_STATE_RINGING: {
                    onPause();
                    break;
                }
                // case answer the call
                case TelephonyManager.CALL_STATE_OFFHOOK: {
                    // TODO
                    break;
                }
                default: break;
            }
        }
    }
}

