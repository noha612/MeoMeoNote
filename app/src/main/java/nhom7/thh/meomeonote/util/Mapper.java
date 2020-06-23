package nhom7.thh.meomeonote.util;

import android.app.Activity;
import android.content.Context;

import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.model.LineNote;

public class Mapper {
    public static LineNote mapNoteEntityToLineNote(Note note, Activity activity) {
        return
                new LineNote(
                        note.getId(),
                        note.getTitle(),
                        note.getContent().length() > 30 ? note.getContent().substring(0, 30) + "..." : note.getContent(),
                        note.getLast_modified().substring(9),
                        BaseUtil.getIdResource(activity, "cat_avt_" + note.getCatName(), "drawable", activity.getPackageName())
                );
    }
}
