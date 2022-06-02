package kr.ac.hs.beet;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DiaryFragment extends Fragment implements DiaryAdapter.ListBtnClickListener{
    private static final String TAG = "DiaryFragment";

    MyDbHelper myDbHelper;

    private FragmentManager fragmentManager;
    private Toolbar toolbar; //툴바

    private DiaryAdapter diaryAdapter;
    private ListView listView;
    RelativeLayout rlistView;

    private ArrayList<DiaryList> diaryListArrayList;

    //하단 버튼 없애기
    private View decorView;
    private int	uiOption;

    ImageView edit_img; //수정버튼
    ImageView delete_img; //삭제버튼
    //ImageView emoji; //이모티콘버튼
    ImageView emoji1, emoji2, emoji3, emoji4, emoji5, emoji6, emoji7;
    TextView diary_date; //일기 날짜
    EditText diarycontent; //일기 내용
    Button save_button; //저장 버튼
    String reg_date; // 날짜

    int em1, em2, em3, em4, em5, em6, em7;


    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREAN);
        String getTime = dateFormat.format(date);
        return getTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        fragmentManager = getFragmentManager();
        myDbHelper = new MyDbHelper(getActivity().getApplicationContext());

        rlistView = (RelativeLayout) view.findViewById(R.id.rListView);
        diarycontent = view.findViewById(R.id.diarycontent);
        save_button = view.findViewById(R.id.save_button);
        diary_date = view.findViewById(R.id.diary_date);

        diarycontent.getText().toString();
        edit_img = view.findViewById(R.id.edit_img);
        delete_img = view.findViewById(R.id.delete_img);
        //emoji = view.findViewById(R.id.emoji);

        emoji1 = view.findViewById(R.id.emoji1);
        emoji2 = view.findViewById(R.id.emoji2);
        emoji3 = view.findViewById(R.id.emoji3);
        emoji4 = view.findViewById(R.id.emoji4);
        emoji5 = view.findViewById(R.id.emoji5);
        emoji6 = view.findViewById(R.id.emoji6);
        emoji7 = view.findViewById(R.id.emoji7);
        diary_date.setText(getTime());

        //툴바만들기
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
        //actionBar.setDisplayShowCustomEnabled(true); //뒤로가기 버튼 생성
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_calendar_month_24);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

        //하단 버튼을 없애는 기능
        decorView = getActivity().getWindow().getDecorView();
        uiOption = getActivity().getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );
        //---------------------


        emoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em1 = getResources().getIdentifier("emotion1","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.KOREAN);
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em1);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em2 = getResources().getIdentifier("emotion2","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em2);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em3 = getResources().getIdentifier("emotion3","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em3);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em4 = getResources().getIdentifier("emotion4","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em4);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em5 = getResources().getIdentifier("emotion5","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em5);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em6 = getResources().getIdentifier("emotion6","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em6);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });
        emoji7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em7 = getResources().getIdentifier("emotion7","drawable",getActivity().getPackageName());
                //버튼 누르면 글 내용, 날짜 저장
                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        java.sql.Date date;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        now = System.currentTimeMillis();
                        date = new java.sql.Date(now);
                        reg_date = sdf.format(date);
                        String editText_diary_content=diarycontent.getText().toString();;

                        ContentValues values = new ContentValues();
                        values.put(Diary.SENTENCE, editText_diary_content);
                        values.put(Diary.DATE, reg_date);
                        values.put(Diary.IMAGE, em7);
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        long newRowId = db.insert(Diary.TABLE_NAME, null, values);

                        Log.i(TAG, "new row ID: " + newRowId);
                    }
                });
            }
        });

        // 리스트 아이템 초기화
        firstInit(view);

        //
        DiaryInit();

        //어댑터 생성
        diaryAdapter = new DiaryAdapter(getActivity().getApplicationContext(), diaryListArrayList,this);
        listView.setAdapter(diaryAdapter);
        diaryAdapter.notifyDataSetChanged();



        /*edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "edit", Toast.LENGTH_LONG).show();
                int checked ;
                int count = diaryAdapter.getCount() ;
                String text = diarycontent.getText().toString();
                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listView.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        Cursor c = db.rawQuery("SELECT * FROM " + Diary.TABLE_NAME, null);
                        if(c.moveToPosition(checked)){
                            int diaryid = c.getInt(0);
                            String sentence = c.getString(1);
                            String date = c.getString(2);
                            int image = c.getInt(3);
                            Log.i(TAG, "DIARYID: " + diaryid +" sentence: " + sentence + " date: " + date + " image: " + image);

                            String sql3 = "UPDATE " + Diary.TABLE_NAME + " SET " + Diary.SENTENCE + " = " + text + " ; ";
                            db.execSQL(sql3);

                        }c.close();
                        db.close();
                            // listview 갱신
                        diaryAdapter.notifyDataSetChanged();
                    }
                }
            }
        });*/

        return view;
    }
    @Override
    public void onListBtnClick(int position) {

        //int check = diaryAdapter.getItemId(position);
        diaryListArrayList.remove(position);
        listView.clearChoices();
        diaryAdapter.notifyDataSetChanged();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Diary.TABLE_NAME, null);
        if(c.moveToPosition(position)){
                int diaryid = c.getInt(0);
                String sentence = c.getString(1);
                String date = c.getString(2);
                int image = c.getInt(3);
                Log.i(TAG, "DIARYID: " + diaryid +" sentence: " + sentence + " date: " + date + " image: " + image);
                //diaryListArrayList.remove(diaryid);
                String sql3 = "DELETE FROM " + Diary.TABLE_NAME + " WHERE " + Diary.DIARY_ID + " = " + diaryid + " ; ";
                db.execSQL(sql3);

        }c.close();
        db.close();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.diary_toolbar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.action_calendar :
                Toast.makeText(getActivity().getApplicationContext(), "Calender action", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void firstInit(View v){
        listView = v.findViewById(R.id.listView);
        diaryListArrayList = new ArrayList<>();
    }

    public void DiaryInit(){

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Diary.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                int diaryid = c.getInt(0);
                String sentence = c.getString(1);
                String date = c.getString(2);
                int image = c.getInt(3);
                Log.i(TAG, "DIARYID: " + diaryid +" sentence: " + sentence + " date: " + date + " image: " + image);

                diaryListArrayList.add(new DiaryList(sentence, date, image));

            }while(c.moveToNext());
        }c.close();
        db.close();
    }



}
