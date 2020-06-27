package nhom7.thh.meomeonote.ui.calendarinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.adapter.ChecklistDetailAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.ChecklistDetail;
import nhom7.thh.meomeonote.util.BaseUtil;

public class ChecklistTabFragment extends Fragment {
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_two, container, false);
        ListView listChecklist = root.findViewById(R.id.checklistFragmentListview);
        final DbHelper dbHelper = new DbHelper(getContext());
        final List<ChecklistDetail> checklistDetails = dbHelper.getChecklistDetailByUserIdAndDate(9999, date);
        ChecklistDetailAdapter checklistDetailAdapter = new ChecklistDetailAdapter(checklistDetails, getActivity());
        listChecklist.setAdapter(checklistDetailAdapter);
        listChecklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflaterPw = getLayoutInflater();
                View v = inflaterPw.inflate(R.layout.dialog_add_checklist, null);
                builder.setView(v);
                final EditText content = v.findViewById(R.id.add_checklist_edt);
                content.setText(checklistDetails.get(position).getContent());
                Button ok = v.findViewById(R.id.add_checklist_ok_btn);
                Button cancel = v.findViewById(R.id.add_checklist_cancel_btn);
                TextView name = v.findViewById(R.id.textView2);
                name.setText("Modify checklist");
                final AlertDialog dialog = builder.create();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getText().toString() != null) {
                            ChecklistDetail c = checklistDetails.get(position);
                            c.setContent(content.getText().toString());
                            dbHelper.updateChecklistDetail(c);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ChecklistTabFragment.this).attach(ChecklistTabFragment.this).commit();
                        }
                        dialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(700, 700);
            }
        });
        listChecklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ChecklistDetail c = checklistDetails.get(position);
                c.setDateRemove(BaseUtil.getCurrentTime());
                dbHelper.deleteChecklistDetail(c);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(ChecklistTabFragment.this).attach(ChecklistTabFragment.this).commit();
                return true;
            }
        });
        return root;
    }
}
