package kr.ac.hs.beet;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewHolderPage extends RecyclerView.ViewHolder{
    private TextView quest;

    HomeQuestList data;

    public HomeViewHolderPage(View itemView) {
        super(itemView);
        quest = itemView.findViewById(R.id.quest);
    }

    public void onBind(HomeQuestList data){
        this.data = data;
        quest.setText(data.getQuesttext());

    }
}
