package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdapterGroupingListHome extends RecyclerView.Adapter<AdapterGroupingListHome.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position, int groupId);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StGrouping> surce = new ArrayList<>();
    public int selectedPosition = -1;
    public int lastPosition = -1;


    public AdapterGroupingListHome(Context context, ArrayList<StGrouping> surce, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTabagheList = onClickItemTabagheList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtTabagheName;
        CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvGroupingItemHome);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemTabaghe);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtTabagheNameItem);

        }
    }

    @Override
    public AdapterGroupingListHome.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grouping_list_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingListHome.Holder holder, final int position) {
        final StGrouping items = surce.get(position);
        if (position == selectedPosition)
            selectItem(holder.cardView);
        else
            unSelectItem(holder.cardView);
        holder.txtTabagheName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/tabaghe/" + items.id + ".png")
                .centerCrop()
                .error(R.drawable.grouping)
                .into(holder.imgItem);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
                onClickItemTabagheList.tabagheListItemClick(position, items.id);
            }
        });
        lastPosition = G.setListItemsAnimation(new View[]{holder.cardView}, new View[]{holder.txtTabagheName}, position, lastPosition);
    }


    private void selectItem(View view) {
        if (view == null)
            return;
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_one));
    }

    public void unSelectItem(View view) {
        if (view == null)
            return;
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.light));
    }


    @Override
    public int getItemCount() {

        return surce.size();
    }


}
