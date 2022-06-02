package kr.ac.hs.beet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;


public class CalendarActivity extends AppCompatActivity{
    MyDbHelper myDbHelper;

    NewDiaryAdapter newdiaryAdapter;
    ListView callistView;
    ArrayList<NewDiaryList> newdiaryLists = new ArrayList<>();

    private Toolbar toolbar; //툴바
    private final static String TAG = ".CalendarActivity";


    String shot_Day;
    String getTime;


    //String time,kcal,menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        myDbHelper = new MyDbHelper(getApplicationContext());
        callistView = findViewById(R.id.callistView);

        newdiaryAdapter = new NewDiaryAdapter(getApplicationContext(), newdiaryLists);

        //툴바만들기
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //뒤로가기 버튼 생성
        actionBar.setDisplayShowTitleEnabled(false); //기본 제목을 없애줍니다.

        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2022, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        //String[] result = {"2022,03,18","2022,04,18","2022,05,18","2022,06,18"};

        //new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth()+ 1;
                int Day = date.getDay();
                long now = System.currentTimeMillis();
                Date date2 = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd", Locale.KOREAN);
                getTime = dateFormat.format(date2);

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                if(Month<10 && Day<10){
                    shot_Day = Year + "/0" + Month + "/0" + Day;
                }else{
                    shot_Day = Year + "/" + Month + "/" + Day;
                }
                //shot_Day = Year + "/0" + Month + "/0" + Day;

                Log.i("shot_Day reg_date2 test", shot_Day + "");
                Log.i("getTimetest", getTime + "");
                materialCalendarView.clearSelection();

                Toast.makeText(getApplicationContext(), getTime , Toast.LENGTH_SHORT).show();

                callistView.setAdapter(newdiaryAdapter);
                init();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays,CalendarActivity.this));
        }

    }

    public void init(){
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Diary.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                int diaryid2 = c.getInt(0);
                String sentence2 = c.getString(1);
                String date3 = c.getString(2);
                int image2 = c.getInt(3);
                Log.i(TAG, "DIARYID: " + diaryid2 +" sentence: " + sentence2 + " date2: " + date3 + " image: " + image2);
                Log.i(TAG, "shot_Day: " + shot_Day);

                if(shot_Day.equals(date3)) {
                    newdiaryLists.add(new NewDiaryList(sentence2, date3, image2));
                }else{
                    newdiaryLists.clear();
                    newdiaryLists.remove(getTime);
                }

            }while(c.moveToNext());
        }c.close();
        db.close();

    }
    /*@Override
    public void onResume() {
        super.onResume();
        newdiaryAdapter.notifyDataSetChanged();
    }*/
}