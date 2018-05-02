package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterGroupingListDialog extends RecyclerView.Adapter<AdapterGroupingListDialog.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private Holder selectedItem;

    public AdapterGroupingListDialog(Context context, ArrayList<StGrouping> source, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.source = source;
        this.onClickItemTabagheList = onClickItemTabagheList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtTabagheName;
        LinearLayout item;
        CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvGroupingItem);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemTabaghe);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtTabagheNameItem);
            item = (LinearLayout) itemView.findViewById(R.id.itemTabaghe);
        }
    }

    @Override
    public AdapterGroupingListDialog.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grouping_list_dialog, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingListDialog.Holder holder, final int position) {
        final StGrouping items = source.get(position);
        holder.txtTabagheName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/tabaghe/" + items.id + ".png")
                .error(R.drawable.defult)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItem(holder);
                onClickItemTabagheList.tabagheListItemClick(position);
            }
        });
    }

    public void setSelectedItem(Holder holder) {
        if (selectedItem != null) {
            selectedItem.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light));
            selectedItem.imgItem.setVisibility(View.VISIBLE);
            selectedItem.txtTabagheName.setTextColor(ContextCompat.getColor(context, R.color.dark_eq));

        }
        if (holder == null)
            return;
        selectedItem = holder;
        selectedItem.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_eq));
        selectedItem.txtTabagheName.setTextColor(ContextCompat.getColor(context, R.color.yellow_ios));
    }

    @Override
    public int getItemCount() {

        return source.size();
    }


}
