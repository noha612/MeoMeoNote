package nhom7.thh.meomeonote.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import nhom7.thh.meomeonote.entity.User;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my_note";
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID = "id";
    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_PASSWORD = "password";
    private static final String USER_CREATED = "created";

    private static final String NOTE_TABLE_NAME = "note";
    private static final String NOTE_ID = "id";
    private static final String NOTE_PASSWORD = "password";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private static final String NOTE_CREATED = "created";
    private static final String NOTE_LAST_MODIFIED = "last_modified";
    private static final String NOTE_TIMER = "timer";
    private static final String NOTE_STATUS = "status";
    private static final String NOTE_USER_ID = "user_id";


    private static final String ATTACHMENT_TABLE_NAME = "attachment";
    private static final String ATTACHMENT_ID = "id";
    private static final String ATTACHMENT_LINK = "link";
    private static final String ATTACHMENT_TYPE = "type";
    private static final String ATTACHMENT_CREATED = "created";
    private static final String ATTACHMENT_LAST_MODIFIED = "last_modified";
    private static final String ATTACHMENT_STATUS = "status";
    private static final String ATTACHMENT_NOTE_ID = "note_id";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DB Manager", "DB Manager");
        this.context = context;
    }

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DbHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQueryUser = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_ID + " integer primary key, " +
                USER_PASSWORD + " TEXT," +
                USER_CREATED + " TEXT," +
                USER_PHONE_NUMBER + " TEXT)";
        db.execSQL(sqlQueryUser);
        String sqlQueryNote = "CREATE TABLE " + NOTE_TABLE_NAME + " (" +
                NOTE_ID + " integer primary key, " +
                NOTE_PASSWORD + " TEXT," +
                NOTE_CONTENT + " TEXT," +
                NOTE_TITLE + " TEXT," +
                NOTE_TIMER + " TEXT," +
                NOTE_CREATED + " TEXT," +
                NOTE_LAST_MODIFIED + " TEXT," +
                NOTE_STATUS + " integer," +
                NOTE_USER_ID + " integer)";
        db.execSQL(sqlQueryNote);
        String sqlQueryAtt = "CREATE TABLE " + ATTACHMENT_TABLE_NAME + " (" +
                ATTACHMENT_ID + " integer primary key, " +
                ATTACHMENT_LINK + " TEXT," +
                ATTACHMENT_STATUS + " integer," +
                ATTACHMENT_CREATED + " TEXT," +
                ATTACHMENT_LAST_MODIFIED + " TEXT," +
                ATTACHMENT_TYPE + " TEXT," +
                ATTACHMENT_NOTE_ID + " integer)";
        db.execSQL(sqlQueryAtt);
        Toast.makeText(context, "Create Database successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
        values.put(USER_PASSWORD, user.getPassword());
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, new String[]{USER_ID,
                        USER_ID, USER_PHONE_NUMBER, USER_PASSWORD}, USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User User = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return User;
    }

    public int update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
        values.put(USER_PASSWORD, user.getPassword());
        return db.update(USER_TABLE_NAME, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public int delete(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
        values.put(USER_PASSWORD, user.getPassword());
        return db.delete(USER_TABLE_NAME, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setPhoneNumber(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                listUser.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listUser;
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, new String[]{USER_ID,
                        USER_ID, USER_PHONE_NUMBER, USER_PASSWORD}, USER_PHONE_NUMBER + "=?",
                new String[]{String.valueOf(phoneNumber)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User User = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return User;
    }

}
