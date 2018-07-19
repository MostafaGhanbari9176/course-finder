package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class AdapterCourseListTeacher extends RecyclerView.Adapter<AdapterCourseListTeacher.Holder> {

    private int lastPosition;

    public interface OnClickItemCourseList {
        void courseListItemClick(int position);
        void changeImage(int position);
    }

    private OnClickItemCourseList onClickItemCourseList;
    private Context context;
    private ArrayList<StCourse> surce = new ArrayList<>();


    public AdapterCourseListTeacher(Context context, ArrayList<StCourse> surce, OnClickItemCourseList onClickItemCourseList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemCourseList = onClickItemCourseList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtCapacity;
        TextView txtnumberOfStudent;
        TextView txtCourseName;
        LinearLayout item;
        LinearLayout helpMessage;
        RelativeLayout unValidMessage;

        public Holder(View itemView) {
            super(itemView);
            unValidMessage = (RelativeLayout) itemView.findViewById(R.id.rlUnValidCourse);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemCourseListTeacher);
            txtCapacity = (TextView) itemView.findViewById(R.id.txtCapacityItemCourseListTeacher);
            txtnumberOfStudent = (TextView) itemView.findViewById(R.id.txtNumberOfStudentItemCourseListTeacher);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemCourseListTeacher);
            item = (LinearLayout) itemView.findViewById(R.id.itemCourseListTeacher);
            helpMessage = (LinearLayout) itemView.findViewById(R.id.llHelpMessage);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list_teacher, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StCourse items = surce.get(position);
        if(position == 0 && !Pref.getBollValue(PrefKey.courseImageHelp,false)){
            holder.helpMessage.setVisibility(View.VISIBLE);

        }
        if (items.vaziat == 0)
            holder.unValidMessage.setVisibility(View.VISIBLE);
        else
            holder.unValidMessage.setVisibility(View.GONE);
        holder.txtCourseName.setText(items.CourseName);
        holder.txtnumberOfStudent.setText(items.numberOfWaitingStudent +"");
        holder.txtCapacity.setText(items.capacity + "");
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + items.id + ".png")
                .error(R.drawable.books)
                .centerCrop()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.vaziat == 1)
                    onClickItemCourseList.courseListItemClick(position);
            }
        });
        holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.vaziat == 1)
                    onClickItemCourseList.courseListItemClick(position);
            }
        });
        holder.imgItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Pref.saveBollValue(PrefKey.courseImageHelp,true);
                onClickItemCourseList.changeImage(position);
                return false;
            }
        });

        lastPosition = G.setListItemsAnimation(new View[]{holder.item}, new View[]{holder.item}, position, lastPosition);
    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
