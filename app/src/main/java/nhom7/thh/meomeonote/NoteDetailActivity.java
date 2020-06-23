package nhom7.thh.meomeonote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nhom7.thh.meomeonote.adapter.GridViewICatIconAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.util.BaseUtil;

public class NoteDetailActivity extends AppCompatActivity {
    Button btnBack;
    Button btnAvtChooser;
    Button btnReminder;
    Button btnReminder2;
    TextView pageName;
    Button btnEditable;
    EditText title;
    EditText content;
    Note note;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
        title = findViewById(R.id.note_title);
        content = findViewById(R.id.note_content);
        btnBack = findViewById(R.id.btn_back);
        btnAvtChooser = findViewById(R.id.btn_avt_chooser);
        btnEditable = findViewById(R.id.btn_editable);
        btnReminder = findViewById(R.id.btn_reminder);
        btnReminder2 = findViewById(R.id.btn_reminder2);
        pageName = findViewById(R.id.page_name);

        dbHelper = new DbHelper(getApplicationContext());

//        int noteId = getIntent().getIntExtra("note_ID",-1);
        note = (Note) getIntent().getSerializableExtra("note");

        if (note == null) {
            btnEditable.setVisibility(View.INVISIBLE);
            btnEditable.setEnabled(false);
            note = new Note();
            note.setId(-1);
            note.setUser_id(9999);
            note.setCatName("snowball");
            note.setStatus(1);
        } else {
            pageName.setText(" View Note");
            title.setText(note.getTitle());
            content.setText(note.getContent());
            btnAvtChooser.setBackgroundResource(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_" + note.getCatName(), "drawable", getPackageName()));
            title.setEnabled(false);
            content.setEnabled(false);
            btnAvtChooser.setEnabled(false);
        }
        btnEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setEnabled(true);
                content.setEnabled(true);
                btnAvtChooser.setEnabled(true);
                pageName.setText(" Edit Note");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String strTitle = title.getText().toString().trim();
                String strContent = content.getText().toString().trim();
                if (strTitle != null && !strTitle.equals("")) {
                    note.setTitle(strTitle);
                    note.setContent(strContent);
                    note.setLast_modified(BaseUtil.getCurrentTime());
                    if (note.getId() == -1) {
                        note.setCreated(BaseUtil.getCurrentTime());
                        dbHelper.addNote(note);
                    } else {
                        dbHelper.updateNote(note);
                    }
                }
                finish();
            }
        });
        btnAvtChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.gridview_cat_icon, null);
                GridView gridView = view1.findViewById(R.id.cat_avt_chooser);
                final List<Integer> avts = new ArrayList<>();
                String[] catNames = getResources().getStringArray(R.array.cat_short_name);
                for (String i : catNames) {
                    avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_" + i, "drawable", getPackageName()));
                }
                GridViewICatIconAdapter adapter = new GridViewICatIconAdapter(avts, NoteDetailActivity.this);
                gridView.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();
                alertDialog.getWindow().setLayout(1000, 800);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnAvtChooser.setBackgroundResource(avts.get(position));
                        note.setCatName(getResources().getResourceEntryName(avts.get(position)).replace("cat_avt_", ""));
                        alertDialog.cancel();
                    }
                });
            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("time", year+" "+monthOfYear+" "+dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnReminder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int h = calendar.get(Calendar.HOUR);
                int m = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(NoteDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                }, h, m, true);
                timePickerDialog.show();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.note_detail_activity, menu);
//        return true;
//    }
}