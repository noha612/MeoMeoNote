package nhom7.thh.meomeonote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.adapter.GridViewICatIconAdapter;
import nhom7.thh.meomeonote.util.BaseUtil;

public class NoteDetailActivity extends AppCompatActivity {
    Button btnBack;
    Button btnAvtChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
        btnBack = findViewById(R.id.btn_back);
        btnAvtChooser = findViewById(R.id.btn_avt_chooser);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDetailActivity.this.finish();
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
                avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_ms_fortune", "drawable", getPackageName()));
                avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_chocola", "drawable", getPackageName()));
                avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_guy", "drawable", getPackageName()));
                avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_jeeves", "drawable", getPackageName()));
                avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_snowball", "drawable", getPackageName()));
                GridViewICatIconAdapter adapter = new GridViewICatIconAdapter(avts, NoteDetailActivity.this);
                gridView.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnAvtChooser.setBackgroundResource(avts.get(position));
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