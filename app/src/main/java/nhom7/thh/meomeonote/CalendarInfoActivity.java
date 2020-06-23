package nhom7.thh.meomeonote;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.ui.main.SectionsPagerAdapter;
import nhom7.thh.meomeonote.util.Mapper;

public class CalendarInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_info);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tabs.getSelectedTabPosition();
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                Toast.makeText(getApplicationContext(), index + "", Toast.LENGTH_LONG).show();
                ListView listView = findViewById(R.id.lstViewCalendar);
                List<LineNote> lineNotes = new ArrayList<>();
                List<Note> notes;
                if (index == 0) {
                    notes = dbHelper.getNodeByUserId(9999);
                    lineNotes = new ArrayList<>();
                    for (Note i : notes) {
                        lineNotes.add(Mapper.mapNoteEntityToLineNote(i, CalendarInfoActivity.this));
                    }
                }
                if (index == 1) {
                    listView.setAdapter(null);
                    lineNotes = new ArrayList<>();
                }
                LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, CalendarInfoActivity.this);

                listView.setAdapter(lineNoteAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}