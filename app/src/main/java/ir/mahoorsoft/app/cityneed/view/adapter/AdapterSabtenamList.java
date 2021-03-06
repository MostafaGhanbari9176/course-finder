package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class AdapterSabtenamList extends RecyclerView.Adapter<AdapterSabtenamList.Holder> {

    private int lastPosition;

    public interface OnClickItemCourseList {
        void courseListItemClick(int position);

        void courseDeletedClick(int position);

        void btnItemMenuPressed(int position);
    }

    private OnClickItemCourseList onClickItemCourseList;
    private Context context;
    private ArrayList<StCourse> surce = new ArrayList<>();


    public AdapterSabtenamList(Context context, ArrayList<StCourse> surce, OnClickItemCourseList onClickItemCourseList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemCourseList = onClickItemCourseList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtMasterName;
        TextView txtstartDate;
        TextView txtCourseName;
        TextView txtDeleted;
        LinearLayout item;
        RelativeLayout rlDeletedMessage;
        TextView btnOk;
        ImageView btnMenu;
        CardView cardView;


        public Holder(View itemView) {
            super(itemView);
            btnOk = (TextView) itemView.findViewById(R.id.btnOkItemSabtenam);
            cardView = (CardView) itemView.findViewById(R.id.CVSabtenamListItem);
            btnMenu = (ImageView) itemView.findViewById(R.id.btnMenuItemSabtenam);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemCourseList);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMasterNameItem);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItem);
            txtstartDate = (TextView) itemView.findViewById(R.id.txtStartDateItem);
            txtDeleted = (TextView) itemView.findViewById(R.id.txtDeletedCourseMessage);
            item = (LinearLayout) itemView.findViewById(R.id.itemCourseList);
            rlDeletedMessage = (RelativeLayout) itemView.findViewById(R.id.rlDeletedCourseMessage);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sabtenam_list, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StCourse items = surce.get(position);
        if (items.isDeleted == 1 || items.isCanceled == 1 || items.vaziat == 0) {
            holder.rlDeletedMessage.setVisibility(View.VISIBLE);
            if (items.isCanceled == 1){
                holder.txtDeleted.setText("???????? ???????? ?????? ???? ?????? ???????? ?????? ?????? ?????? ?????? ??????");
                holder.btnOk.setVisibility(View.VISIBLE);
            }
            else if (items.vaziat == 0) {
                holder.txtDeleted.setText("?????? ?????? ?????? ???? ???????????? ?????????? ???????? ??????");
                holder.btnOk.setVisibility(View.GONE);
            }
        }
        else
            holder.rlDeletedMessage.setVisibility(View.GONE);

        holder.txtCourseName.setText(items.CourseName);
        holder.txtstartDate.setText(items.startDate);
        holder.txtMasterName.setText(items.MasterName);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/course/" + items.id + ".png")
                .error(R.drawable.books)
                .centerCrop()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.isDeleted == 1 || items.isCanceled == 1)
                    onClickItemCourseList.courseDeletedClick(position);
                else
                    onClickItemCourseList.courseListItemClick(position);
            }
        });
        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(items.isDeleted == 1 || items.isCanceled == 1))
                    onClickItemCourseList.btnItemMenuPressed(position);
            }
        });

       lastPosition = G.setListItemsAnimation(new View[]{holder.cardView}, new View[]{holder.btnMenu}, position, lastPosition);
    }

    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.item.clearAnimation();
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {

        return surce.size();
    }


}
