package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class AdapterSmsListOut extends RecyclerView.Adapter<AdapterSmsListOut.Holder> {

    int position;
    private int lastPosition;

    public interface OnClickItemSmsList {
        void seenMessage(int position);

        void deleteMessage(int position);
    }

    private OnClickItemSmsList itemSmsList;
    private Context context;
    private ArrayList<StSmsBox> surce = new ArrayList<>();


    public AdapterSmsListOut(Context context, ArrayList<StSmsBox> surce, OnClickItemSmsList itemSmsList) {
        this.context = context;
        this.surce = surce;
        this.itemSmsList = itemSmsList;

    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtRsName;
        TextView txtCourseName;
        TextView txtDate;
        ImageView btnDelete;
        ImageView imgSeenSms;
        ImageView btnSeen;
        CardView item;

        public Holder(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.cvItemSmsOut);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemOut);
            txtRsName = (TextView) itemView.findViewById(R.id.txtRsNameItemOut);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemOut);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDeleteItemOut);
            btnSeen = (ImageView) itemView.findViewById(R.id.btnSeenItemOut);
            imgSeenSms = (ImageView) itemView.findViewById(R.id.imgSeenSmsOut);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sms_out, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final StSmsBox items = surce.get(position);
        this.position = position;
        if (items.seen == 0)
            holder.imgSeenSms.setVisibility(View.GONE);
        else
            holder.imgSeenSms.setVisibility(View.VISIBLE);
        holder.txtCourseName.setText(items.courseName);
        holder.txtRsName.setText(items.rsName);
        holder.txtDate.setText(items.date);
        holder.btnSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.seenMessage(position);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.seenMessage(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.deleteMessage(position);
            }
        });
        lastPosition = G.setListItemsAnimation(new View[]{holder.item}, new View[]{holder.imgSeenSms, holder.btnSeen}, position, lastPosition);

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
