package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
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

public class AdapterTabagheList extends RecyclerView.Adapter<AdapterTabagheList.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position, int sourceNumber, int groupId);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StGrouping> surce = new ArrayList<>();


    public AdapterTabagheList(Context context, ArrayList<StGrouping> surce, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTabagheList = onClickItemTabagheList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtTabagheName;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemTabaghe);
            txtTabagheName = (TextView) itemView.findViewById(R.id.txtTabagheNameItem);
            item = (LinearLayout) itemView.findViewById(R.id.itemTabaghe);
        }
    }

    @Override
    public AdapterTabagheList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabaghe, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterTabagheList.Holder holder, final int position) {
        final StGrouping items = surce.get(position);
        holder.txtTabagheName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/tabaghe/" + items.id + ".png")
                .error(R.drawable.user)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemTabagheList.tabagheListItemClick(position, items.sourceNumber, items.id);
            }
        });
    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
