package nhom7.thh.meomeonote.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import nhom7.thh.meomeonote.CalendarInfoActivity;
import nhom7.thh.meomeonote.R;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getActivity(), CalendarInfoActivity.class);
                month++;
                String m = month > 10 ? month + "" : ("0" + month);
                String date = dayOfMonth + "/" + m + "/" + year;
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        return root;
    }
}