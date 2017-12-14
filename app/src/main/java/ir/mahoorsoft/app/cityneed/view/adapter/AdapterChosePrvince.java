package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;

/**
 * Created by MAHNAZ on 10/28/2017.
 */

public class AdapterChosePrvince extends RecyclerView.Adapter<AdapterChosePrvince.Holder> {

    public interface ItemClick {

        void showCity(int position);

    }

    View view;
    ItemClick itemClick;
    Context context;
    ArrayList<StCity> surce;

    public AdapterChosePrvince(Context context, ArrayList<StCity> surce, ItemClick itemClick) {
        this.context = context;
        this.surce = surce;
        this.itemClick = itemClick;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txt;
        LinearLayout item;
        ImageView img;

        public Holder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txtItemProvince);
            item = (LinearLayout) view.findViewById(R.id.itemChoseProvince);
            img = (ImageView) itemView.findViewById(R.id.imgChosePrvince);
        }

        public void bindData(String city, final int position) {
            img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    itemClick.showCity(position);
                    return false;
                }
            });
            txt.setText(city);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).
                inflate(R.layout.item_chose_province, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        String city = surce.get(position).name;
        holder.bindData(city, position);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }


}
