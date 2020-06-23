package nhom7.thh.meomeonote.ui.checklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import nhom7.thh.meomeonote.R;

public class ChecklistFragment extends Fragment {

    private ChecklistViewModel checklistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        checklistViewModel =
                ViewModelProviders.of(this).get(ChecklistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checklist, container, false);
        final TextView textView = root.findViewById(R.id.text_checklist);
        checklistViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}