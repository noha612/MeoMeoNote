package nhom7.thh.meomeonote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import nhom7.thh.meomeonote.adapter.ChecklistDetailAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Checklist;
import nhom7.thh.meomeonote.entity.ChecklistDetail;
import nhom7.thh.meomeonote.util.BaseUtil;

public class ChecklistActivity extends AppCompatActivity {
    Button addChecklistDetail;
    ListView listViewChecklistDetail;
    EditText editText;
    Checklist c;
    DbHelper dbHelper;
    int checklistId;
    ChecklistDetailAdapter checklistDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        addChecklistDetail = findViewById(R.id.btnSaveChecklistActivity);
        listViewChecklistDetail = findViewById(R.id.listviewChecklistActivity);
        editText = findViewById(R.id.titleChecklistActivity);
        dbHelper = new DbHelper(ChecklistActivity.this);
        Intent intent = getIntent();
        c = (Checklist) intent.getSerializableExtra("checklist");
        checklistId = intent.getIntExtra("checklistId", -1);
        if (checklistId >= 0) {
            editText.setText("Fill content checklist here");
        } else {
            checklistId = c.getId();
            editText.setText(c.getTitle());
        }
        reloadDB();

        addChecklistDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChecklistActivity.this);
                LayoutInflater inflaterPw = getLayoutInflater();
                View v = inflaterPw.inflate(R.layout.dialog_add_checklist, null);
                builder.setView(v);
                final EditText content = v.findViewById(R.id.add_checklist_edt);
                Button ok = v.findViewById(R.id.add_checklist_ok_btn);
                Button cancel = v.findViewById(R.id.add_checklist_cancel_btn);
                TextView name = v.findViewById(R.id.textView2);
                name.setText("Add checklist");
                final AlertDialog dialog = builder.create();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getText().toString() != null) {
                            ChecklistDetail c = new ChecklistDetail();
                            c.setStatus(1);
                            c.setChecklist_id(checklistId);
                            c.setContent(content.getText().toString());
                            c.setCreated(BaseUtil.getCurrentTime());
                            c.setLast_modified(BaseUtil.getCurrentTime());
                            dbHelper.addChecklistDetail(c);
                        } else {
                            Toast.makeText(getApplicationContext(), "No content", Toast.LENGTH_LONG).show();
                        }
                        reloadDB();
                        dialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(700, 700);
            }
        });

    }

    public void reloadDB() {
        final List<ChecklistDetail> checklistDetailList = dbHelper.getChecklistDetailByChecklist(checklistId);
        checklistDetailAdapter = new ChecklistDetailAdapter(checklistDetailList, this);
        listViewChecklistDetail.setAdapter(checklistDetailAdapter);
    }


    @Override
    protected void onPause() {
        editText = findViewById(R.id.titleChecklistActivity);
        dbHelper = new DbHelper(this);
        Checklist c = dbHelper.getChecklistById(checklistId);
        c.setStatus(1);
        c.setLast_modified(BaseUtil.getCurrentTime());
        c.setTitle(editText.getText().toString());
        dbHelper.updateChecklist(c);
        super.onPause();
    }
}