package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterCommentList extends RecyclerView.Adapter<AdapterCommentList.Holder> {

    private int lastPosition;

    public interface OnClickItemCommentList {
        void likePresed(int position);

        void disLikePresed(int position);

        void feedBAckPresed(int position);
    }

    private OnClickItemCommentList onClickItemCommentList;
    private Context context;
    private ArrayList<StComment> surce = new ArrayList<>();
    private boolean licked = false;
    private boolean disLicked = false;
    private int lickedPosition = -1;


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
        TextView txtDisLikeNum;
        TextView txtLikeNum;
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
            txtDisLikeNum = (TextView) itemView.findViewById(R.id.txtDisLikeNum);
            txtLikeNum = (TextView) itemView.findViewById(R.id.txtLikeNum);
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
    public void onBindViewHolder(final AdapterCommentList.Holder holder, final int position) {
        final StComment items = surce.get(position);
        if(surce.get(position).courseName == null){
            holder.txtCourseName.setVisibility(View.GONE);
            holder.txtStartDate.setVisibility(View.GONE);
        }else{
            holder.txtCourseName.setVisibility(View.VISIBLE);
            holder.txtStartDate.setVisibility(View.VISIBLE);
            holder.txtCourseName.setText("بابت دوره : " + items.courseName);
            holder.txtStartDate.setText("تاریخ برگزاری دوره : " + items.startDate);
        }
        holder.txtUserName.setText(items.userName);
        holder.txtLikeNum.setText(items.likeNum + "");
        holder.txtDisLikeNum.setText(items.disLikeNum + "");
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
               if(lickedPosition != position){
                   licked = false;
                   disLicked = false;
                   lickedPosition = position;
               }
                if (!licked) {
                    onClickItemCommentList.likePresed(position);
                    items.likeNum += 1;
                    holder.txtLikeNum.setText(items.likeNum + "");
                    if (disLicked) {
                        items.disLikeNum -= 1;
                        holder.txtDisLikeNum.setText(items.disLikeNum + "");
                    }
                    licked = true;
                    disLicked = false;
                    surce.set(position, items);
                }

            }
        });

        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lickedPosition != position){
                    licked = false;
                    disLicked = false;
                    lickedPosition = position;
                }
                if (!disLicked) {
                    onClickItemCommentList.disLikePresed(position);
                    items.disLikeNum += 1;
                    holder.txtDisLikeNum.setText(items.disLikeNum + "");
                    if (licked) {
                        items.likeNum -= 1;
                        holder.txtLikeNum.setText(items.likeNum + "");
                    }
                    licked = false;
                    disLicked = true;
                    surce.set(position, items);
                }

            }
        });

        lastPosition = G.setListItemsAnimation(new View[]{holder.ratingBar, holder.txtUserName}, new View[]{holder.txtCommentText, holder.btnLike, holder.btnDisLike, holder.btnFeedBack},position, lastPosition);

    }
    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
    @Override
    public int getItemCount() {

        return surce.size();
    }


}
