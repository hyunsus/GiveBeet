package kr.ac.hs.beet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity<ResultProfileBinding> extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    //하단 버튼 없애기
    private View decorView;
    private int	uiOption;

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private ResultProfileBinding binding;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private ToDoFragment toDoFragment = new ToDoFragment();
    private DiaryFragment diaryFragment = new DiaryFragment();
    private ShopFragment shopFragment = new ShopFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //초기화면 지정
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.action_home:
                                Log.d(TAG, "onNavigationItemSelected: home button clicked");
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                                return true;
                            case R.id.action_todo:
                                Log.d(TAG, "onNavigationItemSelected: about button clicked");
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, toDoFragment).commit();
                                return true;
                            case R.id.action_diary:
                                Log.d(TAG, "onNavigationItemSelected: profile button clicked");
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, diaryFragment).commit();
                                return true;
                            case R.id.action_shop:
                                Log.d(TAG, "onNavigationItemSelected: profile button clicked");
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, shopFragment).commit();
                                return true;
                            default:
                                return false;
                        }
                    }
                }
        );


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
        //-----------
    }



}