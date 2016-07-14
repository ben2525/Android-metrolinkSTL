package layout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ArrayList<String> arriveTimesList;

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
        arrivalTimes = new ArrivalTimes(getActivity().getApplicationContext());
        arriveTimesList = arrivalTimes.timesList();

        View timerFragmentView = inflater.inflate(R.layout.fragment_timer_2, container, false);
        tv = (TextView) timerFragmentView.findViewById(R.id.timer);

        ArrayAdapter timesAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arriveTimesList);
        ListView listView = (ListView)timerFragmentView.findViewById(R.id.timesListView);
        listView.setAdapter(timesAdapter);

        startTimer();

        return timerFragmentView;
    }
/*
    private void populateTimesList() {
        ArrayAdapter<String> timesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(timesAdapter);

    }
*/


    class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            metrolinkTimer.cancel();
            scheduleIterator++;
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


    private double timeAsHourDouble(String s) {
        String[] hourMin = s.split(":");
        double hours = Double.parseDouble(hourMin[0]);
        double minutesAsHour = Double.parseDouble(hourMin[1]) / 60.0;

        return hours + minutesAsHour;
    }


    public void startTimer() {
        startTime = currentTime.currentTimeLongAsMillisecond();
        double endTimeAsDouble = timeAsHourDouble(arriveTimesList.get(scheduleIterator));
        endTime = Math.round(endTimeAsDouble * 3600 * 1000);
        startTime = endTime - startTime;

            metrolinkTimer = new MyCounter(startTime, INTERVAL);

        metrolinkTimer.start();
    }

    public void cancelTimer() {
            metrolinkTimer.cancel();
    }
/*
    public void onDestroyView() {
        cancelTimer();
        super.onDestroy();

    }
*/
    public void onPause() {
        cancelTimer();
        super.onPause();

    }

    public void onResume() {
        //metrolinkTimer = null;
        super.onResume();
        startTimer();
    }


}
