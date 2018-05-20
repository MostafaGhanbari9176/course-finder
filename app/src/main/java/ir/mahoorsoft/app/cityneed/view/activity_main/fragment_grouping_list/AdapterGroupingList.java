package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterGroupingList extends RecyclerView.Adapter<AdapterGroupingList.Holder> {

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
        ImageView imgItem;
        TextView txtName;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.imgGroupingList);
            txtName = (TextView) itemView.findViewById(R.id.txtGroupingList);
            item = (LinearLayout) itemView.findViewById(R.id.itemGroupingList);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            width = (width-400)/2;
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(width, width);
            int dp = G.dpToPx(16);
            params.setMargins(dp, dp, dp, dp);
            item.setLayoutParams(params);
        }
    }

    @Override
    public AdapterGroupingList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grouping_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingList.Holder holder, final int position) {
        final StGrouping items = surce.get(position);
        holder.txtName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/tabaghe/" + items.id + ".png")
                .fitCenter()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemGroupingList.groupingListItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return surce.size();
    }


}
