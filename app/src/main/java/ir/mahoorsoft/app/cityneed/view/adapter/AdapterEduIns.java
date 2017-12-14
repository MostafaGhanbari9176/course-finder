package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import ir.mahoorsoft.app.cityneed.AdverFeature;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class AdapterEduIns extends RecyclerView.Adapter<AdapterEduIns.Holder> {

    public interface setOnClickItem {

        void itemClick(int id);

    }

    setOnClickItem setOnClickItem;
    Context context;
    ArrayList<AdverFeature> surce = new ArrayList<>();

    public AdapterEduIns(Context context, ArrayList<AdverFeature> surce
            , setOnClickItem setOnClickItem) {
        this.context = context;
        this.surce = surce;
        this.setOnClickItem = setOnClickItem;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView txtName;
        TextView txtKind;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);

            imgItem = (ImageView) itemView.findViewById(R.id.img_item);
            txtName = (TextView) itemView.findViewById(R.id.txt_name_item);
            txtKind = (TextView) itemView.findViewById(R.id.txt_kind_item);
            item = (LinearLayout) itemView.findViewById(R.id.item);
        }

        public void bindData(AdverFeature adverFeature, final int number) {
            Glide.with(context)
                    .load(adverFeature.image)
                    .centerCrop()
                    .into(imgItem);

            Typeface tf = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Far_Homa.ttf");
            txtName.setTypeface(tf);
            txtKind.setTypeface(tf);
            txtKind.setText(adverFeature.kind);
            txtName.setText(adverFeature.collegeName);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClickItem.itemClick(number);
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

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        AdverFeature adverFeature = surce.get(position);

        holder.bindData(adverFeature, position);
    }

    @Override
    public int getItemCount() {
        return surce.size();
    }
}
