package kr.ac.hs.beet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShopPopupOKActivity extends Activity {
    final static String TAG = "ShopPopupActivity";
    Button yesBtn;
    Button noBtn;
    //하단 버튼 없애기
    private View decorView;
    private int	uiOption;


    Dbhelper dbHelper;

    TextView txt_Item;
    TextView txt_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop_popup);

        //버튼 가져오기
        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noBtn);


        //하단 버튼을 없애는 기능
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );
        //---------------------

        //DBHelper
        dbHelper = new Dbhelper(getApplicationContext());

        //데이터 가져오기
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String imgName = intent.getStringExtra("imgName");
        String category = intent.getStringExtra("category");
        int price = Integer.parseInt( intent.getStringExtra("price"));

        txt_Item = findViewById(R.id.txt_Item);
        txt_Price = findViewById(R.id.txt_Price);

        txt_Price.setText( price +"B");
        txt_Item.setText( itemName);


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB 작업하기
                //아이템 Storage에 삽입(insert) 후, 회원의 비트 수 차감하기(Update)
                Log.i(TAG, "Yes 버튼 클릭");

                ContentValues comm = new ContentValues();

                comm.put(Storage.COLUMN_IMAGE, imgName);
                comm.put(Storage.COLUMN_ITEM_NAME, itemName);
                comm.put(Storage.COLUMN_CATEGORY, category);

                Log.i(TAG, " 이미지 : " + imgName + " , 아이템이름 : " + itemName
                        + ", 카테고리 : " + category);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long newRowId = db.insert(Storage.TABLE_NAME, null, comm);
                Log.i(TAG, "new row id: " + newRowId);

                //회원의 비트 수 차감하기


                finish();
            }
        });



        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 액티비티(팝업 닫기)
                Log.i(TAG, "No 버튼 클릭");
                finish();
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}