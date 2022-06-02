package kr.ac.hs.beet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class StorageActivity extends AppCompatActivity {
    private String TAG = StorageActivity.class.getSimpleName();


    private GridView cltStrgridview = null;
    private CltStrGridViewAdapter cltStrAdapter = null;
    private GridView objStrgridview = null;
    private ObjStrGridViewAdapter objStrAdapter = null;

    //하단 버튼 없애기
    private View decorView;
    private int	uiOption;

    Dbhelper dbHelper;

    ArrayList<ClothStrItem> clothItemList;
    ArrayList<ObjectStrItem> objectItemList;

    Button offHead;
    Button offBody;
    Button offAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        //툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        // Button
        offHead = findViewById(R.id.offHead);
        offBody = findViewById(R.id.offBody);
        offAll = findViewById(R.id.offAll);

        //DBHelper
        dbHelper = new Dbhelper(getApplicationContext());

        cltStrgridview= (GridView) findViewById(R.id.ClothStrGrid);

        objStrgridview =(GridView) findViewById(R.id.ObjectStrGrid);

        //Adapter 안에 아이템의 정보 담기  ( Storage 테이블 의 readableDB 사용해서 읽어오기 )
        firstClothInit();

        //리스트뷰에 Adapter
        if(!clothItemList.isEmpty()) {
            cltStrAdapter = new CltStrGridViewAdapter(clothItemList);
            cltStrgridview.setAdapter(cltStrAdapter);
        }

        //Adapter 안에 아이템의 정보 담기 ( Storage 테이블 의 readableDB 사용해서 읽어오기 )
        firstObjectInit();

        if(!objectItemList.isEmpty()) {
            objStrAdapter = new ObjStrGridViewAdapter(objectItemList);
            objStrgridview.setAdapter(objStrAdapter);
        }

        // 두잇이 모자 해제하기
        offHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "offHead onClick");

                ContentValues headVal = new ContentValues();
                headVal.put(Customer.COLUMN_HEAD, "z");

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update(Customer.TABLE_NAME, headVal, Customer.COLUMN_ID + " == 'DoIt' ", null);

                db.close();
            }
        });

        //
        offHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "offHead onClick");

                ContentValues headVal = new ContentValues();
                headVal.put(Customer.COLUMN_HEAD, "z");

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update(Customer.TABLE_NAME, headVal, Customer.COLUMN_ID + " == 'DoIt' ", null);

                db.close();

                Toast.makeText(getApplicationContext(), "모자를 해제했습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        offBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "offBody onClick");

                ContentValues bodyVal = new ContentValues();
                bodyVal.put(Customer.COLUMN_BODY, "_0");

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update(Customer.TABLE_NAME, bodyVal, Customer.COLUMN_ID + " == 'DoIt' ", null);

                db.close();

                Toast.makeText(getApplicationContext(), "모자를 해제했습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        offAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "offAll onClick");

                ContentValues allVal = new ContentValues();
                allVal.put(Customer.COLUMN_HEAD, "z");
                allVal.put(Customer.COLUMN_BODY, "_0");


                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update(Customer.TABLE_NAME, allVal, Customer.COLUMN_ID + " == 'DoIt' ", null);

                db.close();

                Toast.makeText(getApplicationContext(), "모자를 해제했습니다.", Toast.LENGTH_SHORT).show();

            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //ClothItem 초기화 함수
    public void firstClothInit(){
        clothItemList = new ArrayList<ClothStrItem>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Storage.TABLE_NAME +
                " WHERE " +  Storage.COLUMN_CATEGORY + " = 'cloth'" , null );

        if (c.moveToFirst()) {
            do{
                String itemName = c.getString(0);
                String imgName = c.getString(1);

                clothItemList.add(new ClothStrItem(itemName, imgName));
                Log.i(TAG, "READ item :" + itemName+ "img : " + imgName);
            }while (c.moveToNext());
        }
        c.close();
        db.close();

    }


    class CltStrGridViewAdapter extends BaseAdapter {
        ArrayList<ClothStrItem> items = null;

        public CltStrGridViewAdapter(ArrayList<ClothStrItem> mList) {
            this.items = mList;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ClothStrItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final ClothStrItem clothItem = items.get(position);

            if(convertView1 == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView1 = inflater.inflate(R.layout.storage_list_item, viewGroup, false);

                TextView tv_name = (TextView) convertView1.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView1.findViewById(R.id.iv_icon);

                tv_name.setText(clothItem.getName());

                String resName = "@drawable/" + clothItem.getImgName();

                int resId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());

                iv_icon.setImageResource(resId);
                Log.d(TAG, "getView() - [ "+position+" ] "+ clothItem.getName() + resId);

            } else {
                View view = new View(context);
                view = (View) convertView1;
            }

            //각 아이템 선택 event // 선택시 토스트 띄우고, 회원 테이블을 업데이트 메인화면으로 돌아가면 옷이 바뀌어 있도록
            convertView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, clothItem.getName() + "을 입었습니다.", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "READ item :" + clothItem.getName()+ "img : " + clothItem.getImgName());

                    boolean checkDB = true;

                    Dbhelper dbHelp = new Dbhelper(getApplicationContext());


                    SQLiteDatabase db1 = dbHelp.getReadableDatabase();


                    // Customer 테이블이 존재하지 않는 다면 생성.
                    Cursor c = db1.rawQuery("SELECT * FROM " + Customer.TABLE_NAME, null );
                    while(c.moveToNext()){
                        if((c.getString(0).equals("DoIt")))
                        {
                            checkDB = false;
                            break;
                        }
                    }
                    c.close();

                    SQLiteDatabase db2 = dbHelp.getWritableDatabase();
                    //checkDB = true 면 아이디가 DoIt인 튜플이 생성되지 않았단 의미.
                    if(checkDB){
                        ContentValues values = new ContentValues();
                        values.put(Customer.COLUMN_ID, "DoIt");
                        values.put(Customer.COLUMN_HEAD, "z");
                        values.put(Customer.COLUMN_BODY, "_0");

                        long newRowId = db2.insert(Customer.TABLE_NAME,null, values);
                        Log.i(TAG, "new row id : " + newRowId);
                    }

                    ContentValues clothVal = new ContentValues();

                    String cloth = clothItem.getImgName();

                    switch(cloth){
                        case "a" :
                        case "b" :
                        case "c" :
                            clothVal.put(Customer.COLUMN_HEAD, cloth);
                            break;
                        case "_1" :
                        case "_2" :
                        case "_3" :
                            clothVal.put(Customer.COLUMN_BODY, cloth);
                            break;
                        default:
                            clothVal.put(Customer.COLUMN_HEAD,"z");
                            clothVal.put(Customer.COLUMN_BODY,"_0");
                    }

                    SQLiteDatabase db3 = dbHelp.getWritableDatabase();
                    db3.update(Customer.TABLE_NAME, clothVal, Customer.COLUMN_ID + " == 'DoIt' ", null);

                    db1.close();
                    db2.close();
                    db3.close();
                }
            });

            return convertView1;  //뷰 객체 반환
        }
    }



    //ClothItem 초기화 함수
    public void firstObjectInit(){
        objectItemList = new ArrayList<ObjectStrItem>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Storage.TABLE_NAME +
                " WHERE " +  Storage.COLUMN_CATEGORY + " = 'object'" , null );

        if (c.moveToFirst()) {
            do{
                String itemName = c.getString(0);
                String imgName = c.getString(1);

                objectItemList.add(new ObjectStrItem(itemName, imgName));
                Log.i(TAG, "READ item :" + itemName+ "img : " + imgName);
            }while (c.moveToNext());
        }
        c.close();
        db.close();

    }


    class ObjStrGridViewAdapter extends BaseAdapter {
        ArrayList<ObjectStrItem> items = null;


        public ObjStrGridViewAdapter(ArrayList<ObjectStrItem> mList) {
            this.items = mList;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ObjectStrItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final ObjectStrItem objectItem = items.get(position);

            if(convertView1 == null) { 
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView1 = inflater.inflate(R.layout.storage_list_item, viewGroup, false);

                TextView tv_name = (TextView) convertView1.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView1.findViewById(R.id.iv_icon);

                tv_name.setText(objectItem.getName());
                int resId = getResources().getIdentifier(objectItem.getImgName(), "drawable", "kr.ac.hs.beet");

                iv_icon.setImageResource(resId);
                Log.d(TAG, "getView() - [ "+position+" ] "+objectItem.getName() + resId + objectItem.getImgName());

            } else {
                View view = new View(context);
                view = (View) convertView1;
            }

            //각 아이템 선택 event
            convertView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, objectItem.getName() + objectItem.getImgName() + " 입니당! ", Toast.LENGTH_SHORT).show();

                }
            });

            return convertView1;  //뷰 객체 반환
        }
    }
}