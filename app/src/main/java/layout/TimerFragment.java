package layout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benalbritton.metrolinkapp2.ArrivalTimes;
import com.benalbritton.metrolinkapp2.CurrentTime;
import com.benalbritton.metrolinkapp2.R;

import java.util.ArrayList;


public class TimerFragment extends Fragment {

    private TextView tv;
    private MyCounter metrolinkTimer = null;

    private CurrentTime currentTime;
    private ArrivalTimes arrivalTimes;
    private ArrayList<Double> arriveTimesList;

    private long startTime;
    private long endTime;
    private int scheduleIterator = 0;
    private final long INTERVAL = 1000;

    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentTime = new CurrentTime();
        //startTime = currentTime.currentTimeLongAsMillisecond();

        arrivalTimes = new ArrivalTimes(getActivity().getApplicationContext());
        arriveTimesList = arrivalTimes.timesList();



        //endTime = Math.round(arrivalTimes.timesList().get(0) * 3600 * 1000);

        //startTime = endTime - startTime;

        View timerFragmentView = inflater.inflate(R.layout.fragment_timer_2, container, false);
        tv = (TextView) timerFragmentView.findViewById(R.id.timer);

        //metrolinkTimer = new MyCounter(5000, interval);
        //metrolinkTimer = new MyCounter(startTime, interval);

        startTimer();
        //metrolinkTimer.start();


        return timerFragmentView;
    }


    class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {

            startTime = currentTime.currentTimeLongAsMillisecond();
            endTime = Math.round(arriveTimesList.get(scheduleIterator) * 3600 * 1000);
            startTime = endTime - startTime;
            scheduleIterator++;

            // Maybe eliminate 2 lower lines
            //cancelTimer();
            metrolinkTimer = null;
            startTimer();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText(String.format("%02d:%02d:%02d", millisUntilFinished / 3600/1000,
                    ((millisUntilFinished / 1000) % 3600) / 60,
                    (((millisUntilFinished / 1000) % 3600) % 60)));
        }
    }

    public void startTimer() {
        if(metrolinkTimer == null) {
            /////////////////////////////////////////////////////////
            metrolinkTimer = new MyCounter(startTime, INTERVAL);
        }
        metrolinkTimer.start();
    }

    public void cancelTimer() {
        if(metrolinkTimer != null)
            metrolinkTimer.cancel();
    }

    public void onDestroyView() {
        cancelTimer();
        super.onDestroy();

    }

    public void onPause() {
        cancelTimer();
        super.onPause();

    }

    public void onResume() {
        super.onResume();
        //startTimer();
    }


}
