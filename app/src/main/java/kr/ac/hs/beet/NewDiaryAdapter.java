package kr.ac.hs.beet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NewDiaryAdapter extends BaseAdapter  {
    private static final String TAG = ".NewDiaryAdapter";
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<NewDiaryList> newdiaryLists = new ArrayList<NewDiaryList>();

    TextView day_text;
    TextView context_text;
    ImageView emoji, del, edit;

    public NewDiaryAdapter(Context context, ArrayList<NewDiaryList> newdiaryLists){
        this.context = context;
        this.newdiaryLists = newdiaryLists;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.i(TAG,"diaryLists.size(): "+ newdiaryLists.size());
        return newdiaryLists.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG,"diaryLists.get(position): "+ newdiaryLists.get(position));
        return newdiaryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG,"diaryLists.getId(position): "+ position);
        return position;
    }

    //position에 위치한 데이터를 화면에 뿌려줄 view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.activity_calendar_list, null);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        //글 내용, 이모지, 삭제버튼, 편집버튼
        context_text =view.findViewById(R.id.context_text);
        emoji = view.findViewById(R.id.emoji);
        day_text = view.findViewById(R.id.day_text);
        del = view.findViewById(R.id.delete_img);

        //각 위젯에 데이터를 반영
        context_text.setText(newdiaryLists.get(position).getSentence());
        day_text.setText(newdiaryLists.get(position).getDate());
        emoji.setImageResource(newdiaryLists.get(position).getImage());

        return view;
    }

};



