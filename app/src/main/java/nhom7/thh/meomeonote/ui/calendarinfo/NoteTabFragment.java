package nhom7.thh.meomeonote.ui.calendarinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.util.Mapper;

public class NoteTabFragment extends Fragment {
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_one, container, false);
        ListView listView = root.findViewById(R.id.noteFragmentListview);
        List<LineNote> lineNotes = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(getContext());
        List<Note> notes = dbHelper.getNodeByUserIdAndDate(9999, date);
        for (Note i : notes) {
            lineNotes.add(Mapper.mapNoteEntityToLineNote(i, getActivity()));
        }
        LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, getActivity());
        listView.setAdapter(lineNoteAdapter);

        return root;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
