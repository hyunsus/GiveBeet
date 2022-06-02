package kr.ac.hs.beet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "DiaryAdapter";
    Context context;
    LayoutInflater layoutInflater;
    RelativeLayout rListView;

    private ListBtnClickListener listBtnClickListener;

    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    //어댑터에 추가된 데이터를 저장하기 위한 ArrayList
    ArrayList<DiaryList> diaryLists = new ArrayList<DiaryList>();
    ArrayList<NewDiaryList> newdiaryLists = new ArrayList<>();
    TextView day_text;
    TextView context_text;
    ImageView emoji, del, edit;


    public DiaryAdapter(Context context, ArrayList<DiaryList> diaryLists, ListBtnClickListener clickListener){
        this.context = context;
        this.diaryLists = diaryLists;
        this.listBtnClickListener = clickListener;
        layoutInflater = LayoutInflater.from(context);
    }

    public DiaryAdapter(Context context, ArrayList<NewDiaryList> newdiaryLists){
        this.context = context;
        this.newdiaryLists = newdiaryLists;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.i(TAG,"diaryLists.size(): "+ diaryLists.size());
        return diaryLists.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG,"diaryLists.get(position): "+ diaryLists.get(position));
        return diaryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG,"diaryLists.getId(position): "+ position);
        return position;
    }

    //position에 위치한 데이터를 화면에 뿌려줄 view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.activity_list_diary, null);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        //글 내용, 이모지, 삭제버튼, 편집버튼
        context_text =view.findViewById(R.id.context_text);
        emoji = view.findViewById(R.id.emoji);
        day_text = view.findViewById(R.id.day_text);
        del = view.findViewById(R.id.delete_img);
        edit = view.findViewById(R.id.edit_img);

        //각 위젯에 데이터를 반영
        context_text.setText(diaryLists.get(position).getSentence());
        day_text.setText(diaryLists.get(position).getDate());
        emoji.setImageResource(diaryLists.get(position).getImage());

        del.setTag(position);
        del.setOnClickListener(this);


        //String path =diaryLists.get(position).getImage();
        //Bitmap bitmap = BitmapFactory.decodeFile(path);
        //emoji.setImageBitmap(bitmap);


        //String path = context.getCacheDir() + "/" + diaryLists.get(position).getImage();
        //Drawable drawable = DrawableContainer.createFromPath(path);
        //Bitmap bitmap = BitmapFactory.decodeFile(path);
        //emoji.setImageDrawable(drawable);

        notifyDataSetChanged();

        return view;
    }

    @Override
    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }
}
