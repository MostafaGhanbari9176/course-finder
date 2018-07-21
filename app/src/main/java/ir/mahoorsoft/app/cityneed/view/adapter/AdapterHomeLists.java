package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class AdapterHomeLists extends RecyclerView.Adapter<AdapterHomeLists.Holder> {

    private int lastPosition;

    public interface setOnClickItem {

        void itemClick(int id, String teacherId, View view);

        void moreCourse(int groupingId, String groupName);

    }

    setOnClickItem setOnClickItem;
    Context context;
    ArrayList<StCourse> surce = new ArrayList<>();
    String groupName = "";

    public AdapterHomeLists(Context context, ArrayList<StCourse> surce
            , setOnClickItem setOnClickItem) {
        this.context = context;
        this.surce = surce;
        this.setOnClickItem = setOnClickItem;
    }

    public AdapterHomeLists(Context context, ArrayList<StCourse> surce
            , setOnClickItem setOnClickItem, String groupName) {
        this.context = context;
        this.surce = surce;
        this.setOnClickItem = setOnClickItem;
        this.groupName = groupName;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView txtMasterName;
        TextView txtCourseName;
        LinearLayout item;
        LinearLayout endOfList;

        public Holder(View itemView) {
            super(itemView);

            imgItem = (ImageView) itemView.findViewById(R.id.img_item);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMasterNameHomeItem);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtNameCourseHomeItem);
            item = (LinearLayout) itemView.findViewById(R.id.itemHome);


            endOfList = (LinearLayout) itemView.findViewById(R.id.llCustomColorItemHome);
        }

        public void bindData(final StCourse items, int position) {

            if (items.endOfList == 0) {
                endOfList.setVisibility(View.GONE);
                Glide.with(context)
                        .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + items.id + ".png")
                        .error(R.drawable.books)
                        .centerCrop()
                        .into(imgItem);
                txtCourseName.setText(items.CourseName);
                txtMasterName.setText("آموزشگاه " + items.MasterName);
                item.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(context, "تاریخ برگزاری " + items.startDate, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            } else {
                endOfList.setVisibility(View.VISIBLE);
            }
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (items.endOfList == 0)
                        setOnClickItem.itemClick(items.id, items.idTeacher, view);
                    else
                        setOnClickItem.moreCourse(items.idTabaghe, groupName);
                }
            });

           lastPosition = G.setListItemsAnimation(new View[]{item}, new View[]{txtCourseName, txtMasterName, item}, position, lastPosition);
        }
    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_home, parent, false);
        view.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        StCourse items = surce.get(position);

        holder.bindData(items, position);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }
}
