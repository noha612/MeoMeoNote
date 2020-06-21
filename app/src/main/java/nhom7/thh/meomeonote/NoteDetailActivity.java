package nhom7.thh.meomeonote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nhom7.thh.meomeonote.adapter.GridViewICatIconAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.util.BaseUtil;

public class NoteDetailActivity extends AppCompatActivity {
    Button btnBack;
    Button btnAvtChooser;
    EditText title;
    EditText content;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        note = new Note();
        note.setUser_id(9999);
        note.setCatName("snowball");
        note.setStatus(1);
        Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
        title = findViewById(R.id.note_title);
        content = findViewById(R.id.note_content);
        btnBack = findViewById(R.id.btn_back);
        btnAvtChooser = findViewById(R.id.btn_avt_chooser);
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
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    dbHelper.addNote(note);
                }
                finish();
            }
        });
        btnAvtChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.cat_avatar_gridview, null);
                GridView gridView = view1.findViewById(R.id.cat_avt_chooser);
                final List<Integer> avts = new ArrayList<>();
                String[] catNames = getResources().getStringArray(R.array.cat_name);
                for (String i : catNames) {
                    avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_" + i, "drawable", getPackageName()));
                }
                GridViewICatIconAdapter adapter = new GridViewICatIconAdapter(avts, NoteDetailActivity.this);
                gridView.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_detail_activity, menu);
        return true;
    }
}