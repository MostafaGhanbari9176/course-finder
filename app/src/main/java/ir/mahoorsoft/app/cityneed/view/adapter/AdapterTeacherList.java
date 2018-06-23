package ir.mahoorsoft.app.cityneed.view.adapter;

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
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class AdapterTeacherList extends RecyclerView.Adapter<AdapterTeacherList.Holder> {

    public interface OnClickItemTeacherList {
        void teacherListItemClick(int position);
    }

    private OnClickItemTeacherList onClickItemTeacherList;
    private Context context;
    private ArrayList<StTeacher> surce = new ArrayList<>();


    public AdapterTeacherList(Context context, ArrayList<StTeacher> surce, OnClickItemTeacherList onClickItemCourseList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTeacherList = onClickItemCourseList;

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
            width = (width-10)/2;
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(width,(int)(width/1.5));
            item.setLayoutParams(params);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StTeacher items = surce.get(position);
        holder.txt.setText("آموزشگاه "+items.subject);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + items.pictureId + ".png")
                .error(R.drawable.university)
                .centerCrop()
                .into(holder.img);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemTeacherList.teacherListItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
