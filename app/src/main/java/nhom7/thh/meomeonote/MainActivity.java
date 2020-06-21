package nhom7.thh.meomeonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.util.BaseUtil;

public class MainActivity extends AppCompatActivity {
    List<LineNote> lineNotes;
    ListView listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listNotes = findViewById(R.id.list_notes);
        lineNotes = new ArrayList<>();

        lineNotes.add(
                new LineNote(
                        "BTL Android",
                        "Tạo git, phác thảo giao diện, làm listview + gridview...",
                        "20/06/20",
                        BaseUtil.getIdResource(this, "cat_avt_ms_fortune", "drawable", getPackageName())
                )
        );

        lineNotes.add(
                new LineNote(
                        "BTL Nhúng",
                        "Đợi Hoa chốt đề tài",
                        "20/06/20",
                        BaseUtil.getIdResource(this, "cat_avt_willow", "drawable", getPackageName())
                )
        );

        lineNotes.add(
                new LineNote(
                        "Cô Ngọc",
                        "Đọc lại tài liệu thứ 2 solo",
                        "21/06/20",
                        BaseUtil.getIdResource(this, "cat_avt_hermeowne", "drawable", getPackageName())
                )
        );

        LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, this);
        listNotes.setAdapter(lineNoteAdapter);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

    }
}
