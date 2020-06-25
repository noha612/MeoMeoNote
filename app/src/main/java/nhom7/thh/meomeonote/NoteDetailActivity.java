package nhom7.thh.meomeonote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nhom7.thh.meomeonote.adapter.GridViewICatIconAdapter;
import nhom7.thh.meomeonote.dbhelper.DbHelper;
import nhom7.thh.meomeonote.entity.Attachment;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.util.BaseUtil;

public class NoteDetailActivity extends AppCompatActivity {
    Button btnBack;
    Button btnAvtChooser;
    Button btnReminder;
    Button btnAttach;
    Button btnLock;
    TextView pageName;
    Button btnEditable;
    ImageView attachImage;
    EditText title;
    EditText content;
    FrameLayout frameLayout;

    Note note;
    Attachment attachment;

    Bitmap bitmap;

    String timerDate;
    String timerTime;

    DbHelper dbHelper;

    static final int CAPTURE_IMAGE_REQUEST_CODE = 69;
    static final int GALLERY_IMAGE_REQUEST_CODE = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
        title = findViewById(R.id.note_title);
        content = findViewById(R.id.note_content);
        btnBack = findViewById(R.id.btn_back);
        btnAvtChooser = findViewById(R.id.btn_avt_chooser);
        btnEditable = findViewById(R.id.btn_editable);
        btnReminder = findViewById(R.id.btn_reminder);
        btnAttach = findViewById(R.id.btn_attach);
        btnLock = findViewById(R.id.btn_lock);
        attachImage = findViewById(R.id.attach_image);
        pageName = findViewById(R.id.page_name);
        frameLayout = findViewById(R.id.attach_image_layout);

        dbHelper = new DbHelper(getApplicationContext());

//        int noteId = getIntent().getIntExtra("note_ID",-1);
        note = (Note) getIntent().getSerializableExtra("note");
        attachment = new Attachment();


        frameLayout.setVisibility(View.INVISIBLE);
        frameLayout.setEnabled(false);

        if (note == null) {
            btnEditable.setVisibility(View.INVISIBLE);
            btnEditable.setEnabled(false);

            note = new Note();
            note.setId(-1);
            note.setUser_id(9999);
            note.setCatName("snowball");
            note.setStatus(1);
        } else {
            pageName.setText(" View Note");
            title.setText(note.getTitle());
            content.setText(note.getContent());
            btnAvtChooser.setBackgroundResource(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_" + note.getCatName(), "drawable", getPackageName()));
            title.setEnabled(false);
            content.setEnabled(false);
            btnAvtChooser.setEnabled(false);

            if (note.getCreated() != null) {
                attachment.setLink(note.getCreated());
                frameLayout.setVisibility(View.VISIBLE);
                frameLayout.setEnabled(true);

                byte[] byteArray = Base64.decode(note.getCreated(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                attachImage.setImageBitmap(bitmap);
            }
        }
        btnEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setEnabled(true);
                content.setEnabled(true);
                btnAvtChooser.setEnabled(true);
                pageName.setText(" Edit Note");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String strTitle = title.getText().toString().trim();
                String strContent = content.getText().toString().trim();
                if (strTitle != null && !strTitle.equals("")) {
                    note.setTitle(strTitle);
                    note.setContent(strContent);
                    note.setLast_modified(BaseUtil.getCurrentTime());
                    note.setCreated(attachment.getLink());
                    if (timerDate != null || timerTime != null) {
                        note.setTimer(timerTime + " " + timerDate);

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.ENGLISH);
                        try {
                            cal.setTime(sdf.parse(note.getTimer()));// all done
                            Date date = cal.getTime();

//                            Toast.makeText(NoteDetailActivity.this, note.toString(), Toast.LENGTH_SHORT).show();

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            Intent i = new Intent(NoteDetailActivity.this, ReminderReceiver.class);
                            i.putExtra("title", note.getTitle());
                            i.putExtra("content", note.getContent());
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteDetailActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (note.getId() == -1) {
                        //TODO fake image
//                        note.setCreated(BaseUtil.getCurrentTime());
                        dbHelper.addNote(note);
                    } else {
                        dbHelper.updateNote(note);
                    }
                }
                finish();
            }
        });
        btnAvtChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.gridview_cat_icon, null);
                GridView gridView = view1.findViewById(R.id.cat_avt_chooser);
                final List<Integer> avts = new ArrayList<>();
                String[] catNames = getResources().getStringArray(R.array.cat_short_name);
                for (String i : catNames) {
                    avts.add(BaseUtil.getIdResource(NoteDetailActivity.this, "cat_avt_" + i, "drawable", getPackageName()));
                }
                GridViewICatIconAdapter adapter = new GridViewICatIconAdapter(avts, NoteDetailActivity.this);
                gridView.setAdapter(adapter);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();
                alertDialog.getWindow().setLayout(1000, 800);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnAvtChooser.setBackgroundResource(avts.get(position));
                        note.setCatName(getResources().getResourceEntryName(avts.get(position)).replace("cat_avt_", ""));
                        alertDialog.cancel();
                    }
                });
            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.dialog_reminder, null);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();
                alertDialog.getWindow().setLayout(600, 700);

                final TextView setDate = view1.findViewById(R.id.set_date);
                final TextView setTime = view1.findViewById(R.id.set_time);
                final Button clear = view1.findViewById(R.id.btn_clear_date_time);

                if (note.getId() != -1 && note.getTimer() != null) {
                    timerTime = note.getTimer().split(" ")[0];
                    setTime.setText(timerTime);
                    timerDate = note.getTimer().split(" ")[1];
                    setDate.setText(timerDate);
                }

                setDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear += 1;
                                String day = dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth + "";
                                String month = monthOfYear < 10 ? ("0" + monthOfYear) : monthOfYear + "";
                                setDate.setText(day + "/" + month + "/" + year);
                                timerDate = day + "/" + month + "/" + year;
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                setTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int h = calendar.get(Calendar.HOUR);
                        int m = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String h = hourOfDay < 10 ? ("0" + hourOfDay) : hourOfDay + "";
                                String m = minute < 10 ? ("0" + minute) : minute + "";
                                setTime.setText(h + ":" + m);
                                timerTime = h + ":" + m;
                            }
                        }, h, m, true);
                        timePickerDialog.show();
                    }
                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate.setText("-> Set Date <-");
                        setTime.setText("-> Set Time <-");
                        timerDate = null;
                        timerTime = null;
                    }
                });

            }
        });

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                builder.setTitle("Choose your profile picture");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Take Photo")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAPTURE_IMAGE_REQUEST_CODE);

                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, GALLERY_IMAGE_REQUEST_CODE);

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoteDetailActivity.this, FullAttachImageView.class);
                byte[] byteArray = Base64.decode(attachment.getLink(), Base64.DEFAULT);

                i.putExtra("image", byteArray);

                startActivity(i);
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.getPassword() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetailActivity.this);
                    builder.setTitle("Please enter password");

// Set up the input
                    final EditText input = new EditText(NoteDetailActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText().toString() != null && !input.getText().toString().trim().equals("")) {
                                note.setPassword(input.getText().toString());
                                Toast.makeText(NoteDetailActivity.this, "Set password success!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    note.setPassword(null);
                    Toast.makeText(NoteDetailActivity.this, "Unlocked!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case CAPTURE_IMAGE_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        Log.v("rc0", bitmap.toString());
                    }

                    frameLayout.setVisibility(View.VISIBLE);
                    frameLayout.setEnabled(true);
                    attachImage.setImageBitmap(bitmap);

                    break;
                case GALLERY_IMAGE_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Log.v("rc0", picturePath);


                                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 101);
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 2;
//                                Log.v("rc0", BitmapFactory.decodeFile(picturePath, options).toString());
                                bitmap = BitmapFactory.decodeFile(picturePath, options);
                                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                                cursor.close();

                                frameLayout.setVisibility(View.VISIBLE);
                                frameLayout.setEnabled(true);
                                attachImage.setImageBitmap(bitmap);
                            }
                        }

                    }
                    break;
            }
        }


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (Exception e) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        }
        byte[] byteArray = stream.toByteArray();
        String temp = Base64.encodeToString(byteArray, Base64.DEFAULT);
        attachment.setLink(temp);
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                NoteDetailActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            NoteDetailActivity.this,
                            new String[]{permission},
                            requestCode);
        } else {
            Toast
                    .makeText(NoteDetailActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
}