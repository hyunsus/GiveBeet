package kr.ac.hs.beet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolderPage>{

    private ArrayList<HomeQuestList> homeQuestLists;

    HomeAdapter(ArrayList<HomeQuestList> data) {
        this.homeQuestLists = data;
    }

    @NonNull
    @Override
    public HomeViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_home, parent, false);
        return new HomeViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolderPage holder, int position) {
        if(holder instanceof HomeViewHolderPage){
            HomeViewHolderPage viewHolder = (HomeViewHolderPage) holder;
            viewHolder.onBind(homeQuestLists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return homeQuestLists.size();
    }
}
