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

import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterTabagheList extends RecyclerView.Adapter<AdapterTabagheList.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StTabaghe> surce = new ArrayList<>();


    public AdapterTabagheList(Context context, ArrayList<StTabaghe> surce, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTabagheList = onClickItemTabagheList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtMasterName;
        TextView txtUnusable;
        TextView txtstartDate;
        TextView txtCourseName;
        LinearLayout llName;
        LinearLayout llStartDate;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemCourseList);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMasterNameItem);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItem);
            txtstartDate = (TextView) itemView.findViewById(R.id.txtStartDateItem);
            txtUnusable = (TextView) itemView.findViewById(R.id.txtUnusable);
            llName = (LinearLayout) itemView.findViewById(R.id.llNameCourseList);
            llStartDate = (LinearLayout) itemView.findViewById(R.id.llStartDateCourseList);
            item = (LinearLayout) itemView.findViewById(R.id.itemCourseList);
        }
    }

    @Override
    public AdapterTabagheList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterTabagheList.Holder holder, final int position) {
        StTabaghe items = surce.get(position);

        holder.llStartDate.setVisibility(View.GONE);
        holder.llName.setVisibility(View.GONE);
        holder.txtUnusable.setVisibility(View.GONE);

        holder.txtMasterName.setText(items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/tabaghe/" + items.id + ".png")
                .error(R.drawable.user)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemTabagheList.tabagheListItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
