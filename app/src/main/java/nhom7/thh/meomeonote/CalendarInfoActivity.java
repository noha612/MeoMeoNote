package nhom7.thh.meomeonote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

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
        final DbHelper dbHelper = new DbHelper(getApplicationContext());
        tabs.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tabs.getSelectedTabPosition();
                if (index == 0) {
                    final ListView listView = findViewById(R.id.lstViewCalendar);
                    List<LineNote> lineNotes = new ArrayList<>();
                    List<Note> notes = dbHelper.getNodeByUserIdAndDate(9999, date);
                    for (Note i : notes) {

                        lineNotes.add(Mapper.mapNoteEntityToLineNote(i, CalendarInfoActivity.this));
                    }
                    LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, CalendarInfoActivity.this);
                    listView.setAdapter(lineNoteAdapter);
                }
                if (index == 1) {
                    final ListView listView = findViewById(R.id.lstViewCalendar);
                    List<LineNote> lineNotes = new ArrayList<>();
                    LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, CalendarInfoActivity.this);
                    listView.setAdapter(lineNoteAdapter);
                }

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