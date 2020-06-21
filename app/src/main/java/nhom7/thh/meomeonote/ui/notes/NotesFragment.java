package nhom7.thh.meomeonote.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.R;
import nhom7.thh.meomeonote.adapter.LineNoteAdapter;
import nhom7.thh.meomeonote.model.LineNote;
import nhom7.thh.meomeonote.util.BaseUtil;

public class NotesFragment extends Fragment {
    List<LineNote> lineNotes;
    ListView listNotes;
    View root;

    private NotesViewModel notesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        listNotes = root.findViewById(R.id.list_notes);
        listNotes = root.findViewById(R.id.list_notes);
        lineNotes = new ArrayList<>();

        lineNotes.add(
                new LineNote(
                        "BTL Android",
                        "Tạo git, phác thảo giao diện, làm listview + gridview...",
                        "20/06/20",
                        BaseUtil.getIdResource(getActivity(), "cat_avt_ms_fortune", "drawable", getActivity().getPackageName())
                )
        );

        lineNotes.add(
                new LineNote(
                        "BTL Nhúng",
                        "Đợi Hoa chốt đề tài",
                        "20/06/20",
                        BaseUtil.getIdResource(getActivity(), "cat_avt_willow", "drawable", getActivity().getPackageName())
                )
        );

        lineNotes.add(
                new LineNote(
                        "Cô Ngọc",
                        "Đọc lại tài liệu thứ 2 solo",
                        "21/06/20",
                        BaseUtil.getIdResource(getActivity(), "cat_avt_hermeowne", "drawable", getActivity().getPackageName())
                )
        );

        LineNoteAdapter lineNoteAdapter = new LineNoteAdapter(lineNotes, getActivity());
        listNotes.setAdapter(lineNoteAdapter);
        return root;
    }
}