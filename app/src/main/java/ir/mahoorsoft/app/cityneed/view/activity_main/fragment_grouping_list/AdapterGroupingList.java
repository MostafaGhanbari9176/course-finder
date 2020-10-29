package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.Context;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterGroupingList extends RecyclerView.Adapter<AdapterGroupingList.Holder> {

    private int lastPsition;

    public interface OnClickItemGroupingList {
        void groupingListItemClick(int position);
    }

    private OnClickItemGroupingList onClickItemGroupingList;
    private Context context;
    private ArrayList<StGrouping> surce = new ArrayList<>();

    public AdapterGroupingList(Context context, ArrayList<StGrouping> surce, OnClickItemGroupingList onClickItemGroupingList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemGroupingList = onClickItemGroupingList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        LinearLayout item;


        public Holder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imgTeacherList);
            txt = (TextView) itemView.findViewById(R.id.txtTeacherList);
            item = (LinearLayout) itemView.findViewById(R.id.itemTeacherList);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            width = width - 250;
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(width / 2, width / 2);
            int dp = (250/4);
            params.setMargins(dp, dp, dp, dp);
            item.setLayoutParams(params);
        }
    }

    @Override
    public AdapterGroupingList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingList.Holder holder, final int position) {
        final StGrouping items = surce.get(position);
        holder.txt.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/tabaghe/" + items.id + ".png")
                .fitCenter()
                .error(R.drawable.grouping)
                .into(holder.img);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemGroupingList.groupingListItemClick(position);
            }
        });

      lastPsition = G.setListItemsAnimation(new View[] {holder.img, holder.item}, new View[]{holder.txt}, position, lastPsition);
    }

    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.item.clearAnimation();
        holder.itemView.clearAnimation();
    }


    @Override
    public int getItemCount() {
        return surce.size();
    }


}
