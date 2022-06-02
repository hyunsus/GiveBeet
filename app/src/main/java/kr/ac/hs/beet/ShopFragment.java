package kr.ac.hs.beet;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private String TAG = ShopFragment.class.getSimpleName();

    private Toolbar toolbar; //툴바

    private GridView clothgridview = null;
    private ClothGridViewAdapter clothAdapter = null;
    private GridView objectgridview = null;
    private ObjectGridViewAdapter objectAdapter = null;

    TextView txt_beet;
    int Beet;

    //하단 버튼 없애기
    private View decorView;
    private int	uiOption;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        //툴바만들기
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
        actionBar.setDisplayShowCustomEnabled(true); //뒤로가기 버튼 생성
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_card_giftcard_24);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        //----------

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

        txt_beet = view.findViewById(R.id.txt_Beet);

        // 사용자의 비트 수를 가져오기 (DB생성 후 에는 select로 찾아오기)
        Beet = Integer.parseInt(txt_beet.getText().toString());

        clothgridview = (GridView) view.findViewById(R.id.ClothGrid);
        clothAdapter = new ShopFragment.ClothGridViewAdapter();

        objectgridview =(GridView) view.findViewById(R.id.ObjectGrid);
        objectAdapter = new ShopFragment.ObjectGridViewAdapter();


        //Adapter 안에 아이템의 정보 담기
        // 모자는 숫자(1,2,3...)로, 옷은 영어(a,b,c...)로 DB에 보내질 것이다.
        // 두잇이가 입는 옷은 " '숫자'-'영어'.png " 로 나타내 질 것임.

        clothAdapter.addItem(new ClothItem("10", "꿈모자","a", R.drawable.a));
        clothAdapter.addItem(new ClothItem("15", "꿈잠옷","_1", R.drawable._1));
        clothAdapter.addItem(new ClothItem("10", "선글라스","b", R.drawable.b));
        clothAdapter.addItem(new ClothItem("15", "가죽자켓","_2" , R.drawable._2));
        clothAdapter.addItem(new ClothItem("15", "헬멧", "c", R.drawable.c));
        clothAdapter.addItem(new ClothItem("20", "멜빵", "_3",R.drawable._3));

        //리스트뷰에 Adapter 설정
        clothgridview.setAdapter(clothAdapter);


        //Adapter 안에 아이템의 정보 담기
        objectAdapter.addItem(new ObjectItem("30", "붉은소파","sofa2", R.drawable.sofa2));
        objectAdapter.addItem(new ObjectItem("20", "푹신한소파", "sofa1",R.drawable.sofa1));
        objectAdapter.addItem(new ObjectItem("10", "안스리움", "vase1",R.drawable.vase1));
        objectAdapter.addItem(new ObjectItem("15", "안스리움꽃", "vase2",R.drawable.vase2));
        objectAdapter.addItem(new ObjectItem("10", "조명", "light1",R.drawable.light1));
        objectAdapter.addItem(new ObjectItem("10", "편안한조명", "light2",R.drawable.light2));
        objectAdapter.addItem(new ObjectItem("10", "아늑한조명", "light3",R.drawable.light3));

        objectgridview.setAdapter(objectAdapter);


        return view;
    }
    //툴바!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //의상 그리드
    class ClothGridViewAdapter extends BaseAdapter {
        ArrayList<ClothItem> items = new ArrayList<ClothItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ClothItem item) {
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final ClothItem clothItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);

                TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

                tv_price.setText(clothItem.getPrice());
                tv_name.setText(clothItem.getName());
                iv_icon.setImageResource(clothItem.getResId());
                Log.d(TAG, "getView() - [ "+position+" ] "+ clothItem.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event  (각 아이템 선택 시 팝업 창 뜨고 구매할 것인지 물은 후 회원 beet와 체크 후 구매하도록 하기
            // 구매가 되었다면 회원의 비트 차감하고 업데이트
            // 만약 storage에 물건이 이미 있다면 이미 구입한 물건임을 알림
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* Toast.makeText(context, clothItem.getPrice()+" 번 - "+clothItem.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();*/
                    int price = Integer.parseInt(clothItem.price);


                    Intent intent;
                    if( Beet < price) {
                        intent = new Intent(view.getContext(), ShopPopupNOKActivity.class);
                    }
                    else{
                        intent = new Intent(view.getContext(), ShopPopupOKActivity.class);
                        intent.putExtra("itemName", clothItem.getName());
                        intent.putExtra("imgName", clothItem.getImgName());
                        intent.putExtra("price", clothItem.getPrice());
                        intent.putExtra("category", clothItem.getCategory());
                    }
                    startActivity(intent);


                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    //오브젝트 그리드
    class ObjectGridViewAdapter extends BaseAdapter {
        ArrayList<ObjectItem> items = new ArrayList<ObjectItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ObjectItem item) {
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final ObjectItem objectItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);

                TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

                tv_price.setText(objectItem.getPrice());
                tv_name.setText(objectItem.getName());
                iv_icon.setImageResource(objectItem.getResId());
                Log.d(TAG, "getView() - [ "+position+" ] "+objectItem.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Toast.makeText(context, objectItem.getPrice()+" 번 - "+objectItem.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();
                     */

                    int price = Integer.parseInt(objectItem.price);


                    Intent intent;
                    if( Beet < price) {
                        intent = new Intent(view.getContext(), ShopPopupNOKActivity.class);

                    }
                    else{
                        intent = new Intent(view.getContext(), ShopPopupOKActivity.class);
                        intent.putExtra("itemName" , objectItem.getName());
                        intent.putExtra("imgName", objectItem.getImgName());
                        intent.putExtra("price", objectItem.getPrice());
                        intent.putExtra("category", objectItem.getCategory());
                    }
                    startActivity(intent);


                }
            });

            return convertView;  //뷰 객체 반환
        }
    }
}
