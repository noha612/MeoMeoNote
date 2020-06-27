package nhom7.thh.meomeonote.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Checklist;

public class ChecklistAdapter extends BaseAdapter {
    List<Checklist> checklistList;
    Activity activity;

    public ChecklistAdapter(List<Checklist> checklistList, Activity activity) {
        this.checklistList = checklistList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return checklistList.size();
    }

    @Override
    public Object getItem(int position) {
        return checklistList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.list_view_checklist, null);
        TextView content = convertView.findViewById(R.id.checklistContent);
        content.setText(checklistList.get(position).getContent());
        content.setTextColor(Color.BLACK);
        TextView created = convertView.findViewById(R.id.checklistDatecreated);
        created.setText((checklistList.get(position).getCreated().split("\\s+"))[1]);
        created.setTextColor(Color.RED);
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxChecklist);
        if (1 == checklistList.get(position).getStatus()) {
            checkBox.setChecked(false);
        } else if (checklistList.get(position).getStatus() == 2) {
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checklistList.get(position).setStatus(2);
                } else {
                    checklistList.get(position).setStatus(1);
                }
                new DbHelper(activity).updateChecklist(checklistList.get(position));
            }
        });
        return convertView;
    }
}
