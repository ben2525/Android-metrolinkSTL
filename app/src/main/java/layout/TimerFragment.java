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
import com.benalbritton.metrolinkapp2.Station;
import com.benalbritton.metrolinkapp2.StationListing;

import java.util.ArrayList;


public class TimerFragment extends Fragment {

    private TextView tv;
    private MyCounter metrolinkTimer = null;
    private ArrayList<Station> stationList;

    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final long interval = 1000;

        StationListing stationListing = new StationListing();
        stationList = stationListing.getStationsInfo(getActivity().getApplicationContext());

        ArrivalTimes arrivalTimes = new ArrivalTimes(getActivity().getApplicationContext());
        CurrentTime currentTime = new CurrentTime();

        long startTime = currentTime.currentTimeLongAsMillisecond();
        long endTime = Math.round(arrivalTimes.timesList().get(0) * 3600 * 1000);
        startTime = endTime - startTime;

        View timerFragmentView = inflater.inflate(R.layout.fragment_timer_2, container, false);
        tv = (TextView) timerFragmentView.findViewById(R.id.timer);
        metrolinkTimer = new MyCounter(startTime, interval);
        metrolinkTimer.start();

        return timerFragmentView;
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
            tv.setText(String.format("%02d:%02d:%02d", millisUntilFinished / 3600/1000,
                    ((millisUntilFinished / 1000) % 3600) / 60,
                    (((millisUntilFinished / 1000) % 3600) % 60)));
        }
    }

    void cancelTimer() {
        if(metrolinkTimer != null)
            metrolinkTimer.cancel();
    }

    public void onDestroyView() {
        super.onDestroy();
        cancelTimer();
    }


}
