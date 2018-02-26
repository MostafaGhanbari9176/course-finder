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
import ir.mahoorsoft.app.cityneed.model.struct.StUser;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterSdudentNameList extends RecyclerView.Adapter<AdapterSdudentNameList.Holder> {

    public interface OnClickItemSdutentNameList {
        void tabagheListItemClick(int position, int sourceNumber, int groupId);
    }

    private OnClickItemSdutentNameList onClickItemSdutentNameList;
    private Context context;
    private ArrayList<StUser> surce = new ArrayList<>();


    public AdapterSdudentNameList(Context context, ArrayList<StUser> surce, OnClickItemSdutentNameList onClickItemSdutentNameList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemSdutentNameList = onClickItemSdutentNameList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtItemStudentName);

        }
    }

    @Override
    public AdapterSdudentNameList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabaghe, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterSdudentNameList.Holder holder, final int position) {
        final StUser items = surce.get(position);
        holder.txtName.setText(items.name);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }


}
