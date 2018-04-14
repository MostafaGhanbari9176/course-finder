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

public class AdapterGroupingListName extends RecyclerView.Adapter<AdapterGroupingListName.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position, int groupId);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StGrouping> surce = new ArrayList<>();



    public AdapterGroupingListName(Context context, ArrayList<StGrouping> surce, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTabagheList = onClickItemTabagheList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTabagheName;
        LinearLayout item;
        public Holder(View itemView) {
            super(itemView);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtItemGroupingName);
            item = (LinearLayout) itemView.findViewById(R.id.itemGroupingName);
        }
    }

    @Override
    public AdapterGroupingListName.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grouping_name, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterGroupingListName.Holder holder, final int position) {
        final StGrouping items = surce.get(position);
        holder.txtTabagheName.setText(items.subject);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemTabagheList.tabagheListItemClick(position, items.id);
            }
        });
    }
    @Override
    public int getItemCount() {

        return surce.size();
    }


}
