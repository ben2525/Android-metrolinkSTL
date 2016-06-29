package com.benalbritton.metrolinkapp2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //public ArrayList<Station> stations;

    ////////////////////////////////
    //private final long startTime = 50000;
    private final long interval = 1000;
    TextView tv;
    MyCounter metrolinkTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run();
    }

    private void run() {

        ArrivalTimes arrivalTimes = new ArrivalTimes(this);
        CurrentTime currentTime = new CurrentTime();

        long startTime = currentTime.currentTimeLongAsMillisecond();
        long endTime = Math.round(arrivalTimes.timesList().get(0) * 3600 * 1000);
        startTime = endTime - startTime;

        tv = (TextView) this.findViewById(R.id.timer);
        MyCounter metrolinkTimer = new MyCounter(startTime, interval);
        metrolinkTimer.start();
    }

    class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            cancelTimer();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            /*
            tv.setText("" + String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds
                                    (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            */

            tv.setText(String.format("%02d:%02d:%02d", millisUntilFinished / 3600/1000,
                    (millisUntilFinished % 3600) / 60/1000, (millisUntilFinished % 60)/1000));

                    //(millisUntilFinished/1000)+"");
            //System.out.println("Timer  : " + (millisUntilFinished/1000));
        }
    }

    void cancelTimer() {
        if(metrolinkTimer != null)
            metrolinkTimer.cancel();
    }

    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}