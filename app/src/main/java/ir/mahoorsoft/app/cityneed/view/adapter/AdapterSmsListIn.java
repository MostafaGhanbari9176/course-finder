package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
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

public class AdapterSmsListIn extends RecyclerView.Adapter<AdapterSmsListIn.Holder> {


    private int lastPosition;

    public interface OnClickItemSmsList {
        void seenMessage(int position);

        void deleteMessage(int position);

        void reportSms(int position);
    }

    private OnClickItemSmsList itemSmsList;
    private Context context;
    private ArrayList<StSmsBox> surce = new ArrayList<>();


    public AdapterSmsListIn(Context context, ArrayList<StSmsBox> surce, OnClickItemSmsList itemSmsList) {
        this.context = context;
        this.surce = surce;
        this.itemSmsList = itemSmsList;

    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtTsName;
        TextView txtCourseName;
        TextView txtDate;
        ImageView imgSeenSms;
        /*
        TextView txtTsName2;
        TextView txtCourseName2;
        TextView txtDate2;
*/
        ImageView btnDelete;
        ImageView btnReportSms;
        ImageView btnSeen;
        CardView item;

        public Holder(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.cvItemSmsIn);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseNameItemIn);
            txtTsName = (TextView) itemView.findViewById(R.id.txtTsNameItemIn);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemIn);
            imgSeenSms = (ImageView) itemView.findViewById(R.id.imgSeenSms);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDeleteItemIn);
            btnSeen = (ImageView) itemView.findViewById(R.id.btnSeenItemIn);
            btnReportSms = (ImageView) itemView.findViewById(R.id.btnReportItemSms);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sms_in, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final StSmsBox items = surce.get(position);
        if (items.seen == 0)
            holder.imgSeenSms.setVisibility(View.GONE);
        else
            holder.imgSeenSms.setVisibility(View.VISIBLE);

        holder.txtCourseName.setText(items.courseName);
        holder.txtTsName.setText(items.tsName);
        holder.txtDate.setText(items.date);
        holder.btnReportSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSmsList.reportSms(position);
            }
        });
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
