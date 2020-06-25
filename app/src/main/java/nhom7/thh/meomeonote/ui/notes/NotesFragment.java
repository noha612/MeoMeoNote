package nhom7.thh.meomeonote.ui.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.NoteDetailActivity;
import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.util.Mapper;

public class NotesFragment extends Fragment {
    DbHelper dbHelper;
    List<Note> notes;
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

                final Note temp = notes.get(position);
                final DbHelper dbHelper = new DbHelper(getActivity().getApplicationContext());
                final Snackbar snackbar = Snackbar.make(view, "Deleted.", Snackbar.LENGTH_LONG);
                snackbar.setAction("redo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.setStatus(1);
                        dbHelper.updateNote(temp);
                        snackbar.dismiss();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(NotesFragment.this).attach(NotesFragment.this).commit();
                    }
                });
                snackbar.show();
                dbHelper.deleteNote(temp);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(NotesFragment.this).attach(NotesFragment.this).commit();


                return true;
            }
        });

        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String password = notes.get(position).getPassword();
                if (password != null) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Please enter password");

// Set up the input
                    final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText().toString() != null && input.getText().toString().equals(password)) {
                                Intent i = new Intent(getActivity(), NoteDetailActivity.class);
                                i.putExtra("note", notes.get(position));
                                startActivityForResult(i, 0);
                            }
                            else{
                                Toast.makeText(getContext(), "Wrong password!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Locked!", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Intent i = new Intent(getActivity(), NoteDetailActivity.class);
                    i.putExtra("note", notes.get(position));
                    startActivityForResult(i, 0);
                }

            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadNotes() {
        lineNotes = new ArrayList<>();
        try {
            dbHelper = new DbHelper(getActivity().getApplicationContext());
            notes = dbHelper.getNodeByUserId(9999);
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