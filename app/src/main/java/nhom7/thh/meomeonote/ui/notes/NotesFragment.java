package nhom7.thh.meomeonote.ui.notes;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.util.BaseUtil;
import nhom7.thh.meomeonote.util.Mapper;

public class NotesFragment extends Fragment {
    List<LineNote> lineNotes;
    ListView listNotes;
    View root;

    private NotesViewModel notesViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        listNotes = root.findViewById(R.id.list_notes);

        loadNotes();

        listNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Deleted.", Snackbar.LENGTH_LONG).show();
                DbHelper dbHelper = new DbHelper(getActivity().getApplicationContext());
                Note temp = new Note();
                temp.setId(lineNotes.get(position).getId());
                dbHelper.deleteNote(temp);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(NotesFragment.this).attach(NotesFragment.this).commit();

                return true;
            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadNotes() {
        lineNotes = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(getActivity().getApplicationContext());
            List<Note> notes = dbHelper.getNodeByUserId(9999);
            Log.v("size", notes.size() + "");
            for (Note i : notes) {
                lineNotes.add(Mapper.mapNoteEntityToLineNote(i, getActivity()));
            }
            LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, getActivity());
            listNotes.setAdapter(lineNoteAdapter);
        } catch (Exception e) {

        }
    }
}