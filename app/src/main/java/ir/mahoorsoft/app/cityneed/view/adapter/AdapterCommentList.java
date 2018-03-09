package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterCommentList extends RecyclerView.Adapter<AdapterCommentList.Holder> {

    public interface OnClickItemCommentList {
        void likePresed(int position);
        void disLikePresed(int position);
        void feedBAckPresed(int position);
    }

    private OnClickItemCommentList onClickItemCommentList;
    private Context context;
    private ArrayList<StComment> surce = new ArrayList<>();


    public AdapterCommentList(Context context, ArrayList<StComment> surce, OnClickItemCommentList onClickItemCommentList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemCommentList = onClickItemCommentList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView btnLike;
        ImageView btnDisLike;
        ImageView btnFeedBack;
        TextView txtCommentText;
        TextView txtUserName;
        TextView txtCommentDate;
        TextView txtCourseName;
        TextView txtStartDate;
        RatingBar ratingBar;
        /*LinearLayout item;*/

        public Holder(View itemView) {
            super(itemView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratBarItemComment);
            btnLike = (ImageView) itemView.findViewById(R.id.btnLikeItemComment);
            btnDisLike = (ImageView) itemView.findViewById(R.id.btnDisLikeItemComment);
            btnFeedBack = (ImageView) itemView.findViewById(R.id.btnFeedBackItemComment);
            txtCommentDate = (TextView) itemView.findViewById(R.id.txtCommentDateItemComment);
            txtCommentText = (TextView) itemView.findViewById(R.id.txtCommentTextItemComment);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemComment);
            txtStartDate = (TextView) itemView.findViewById(R.id.txtStartDateItemComment);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserNameItemComment);
            /*item = (LinearLayout) itemView.findViewById(R.id.itemTabaghe);*/
        }
    }

    @Override
    public AdapterCommentList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterCommentList.Holder holder, final int position) {
        final StComment items = surce.get(position);
        holder.txtUserName.setText(items.userName);
        holder.txtStartDate.setText(items.startDate);
        holder.txtCourseName.setText(items.courseName);
        holder.txtCommentDate.setText(items.date);
        holder.txtCommentText.setText(items.commentText);
        holder.ratingBar.setRating(items.teacherRat);
        holder.btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemCommentList.feedBAckPresed(position);
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemCommentList.likePresed(position);
            }
        });

        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemCommentList.disLikePresed(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
