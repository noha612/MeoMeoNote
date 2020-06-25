package nhom7.thh.meomeonote.ui.calendarinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import nhom7.thh.meomeonote.R;

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
//        ListView listView = root.findViewById(R.id.checklistFragmentListview);
//        List<LineNote> lineNotes = new ArrayList<>();
//        DbHelper dbHelper = new DbHelper(getContext());
//        List<Note> notes = dbHelper.getNodeByUserIdAndDate(9999, date);
//        for (Note i : notes) {
//            lineNotes.add(Mapper.mapNoteEntityToLineNote(i, getActivity()));
//        }
//        LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, getActivity());
//        listView.setAdapter(lineNoteAdapter);

        return root;
    }
}
