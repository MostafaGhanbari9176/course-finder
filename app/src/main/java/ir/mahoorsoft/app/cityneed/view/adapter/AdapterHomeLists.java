package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
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

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class AdapterHomeLists extends RecyclerView.Adapter<AdapterHomeLists.Holder> {

    public interface setOnClickItem {

        void itemClick(int id, String teacherId);

    }

    setOnClickItem setOnClickItem;
    Context context;
    ArrayList<StCourse> surce = new ArrayList<>();

    public AdapterHomeLists(Context context, ArrayList<StCourse> surce
            , setOnClickItem setOnClickItem) {
        this.context = context;
        this.surce = surce;
        this.setOnClickItem = setOnClickItem;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView txtMasterName;
        TextView txtCourseName;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);

            imgItem = (ImageView) itemView.findViewById(R.id.img_item);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMasterNameHomeItem);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtNameCourseHomeItem);
            item = (LinearLayout) itemView.findViewById(R.id.itemHome);
        }

        public void bindData(final StCourse items) {
            Glide.with(context)
                    .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + items.id + ".png")
                    .error(R.drawable.user)
                    .centerCrop()
                    .into(imgItem);

            Typeface tf = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Far_Homa.ttf");
            txtMasterName.setTypeface(tf);
            txtCourseName.setTypeface(tf);
            txtCourseName.setText(items.CourseName);
            txtMasterName.setText("آموزشگاه "+items.MasterName);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClickItem.itemClick(items.id, items.idTeacher);
                }
            });
            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
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

        holder.bindData(items);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }
}
