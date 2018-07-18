package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class AdapterMahoorAppDataItem extends RecyclerView.Adapter<AdapterMahoorAppDataItem.Holder> {

    public interface OnClickItemTabagheList {
        void tabagheListItemClick(int position, int sourceNumber, String url);
    }

    private OnClickItemTabagheList onClickItemTabagheList;
    private Context context;
    private ArrayList<StMahoorAppData> surce = new ArrayList<>();

    public AdapterMahoorAppDataItem(Context context, ArrayList<StMahoorAppData> surce, OnClickItemTabagheList onClickItemTabagheList) {
        this.context = context;
        this.surce = surce;
        this.onClickItemTabagheList = onClickItemTabagheList;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtName;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);

            imgItem = (ImageView) itemView.findViewById(R.id.imgItemAboutUS);
            txtName = (TextView) itemView.findViewById(R.id.txtNameItemAboutUs);
            item = (LinearLayout) itemView.findViewById(R.id.itemAboutUs);
        }
    }

    @Override
    public AdapterMahoorAppDataItem.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_about_us, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterMahoorAppDataItem.Holder holder, final int position) {
        final StMahoorAppData items = surce.get(position);
        holder.txtName.setText(items.appName);
        Glide.with(context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/MAP/" + items.pictureId + ".png")
                .fitCenter()
                .into(holder.imgItem);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickItemTabagheList.tabagheListItemClick(position, items.sourceNumber, items.url);
            }
        });
    }


    @Override
    public int getItemCount() {

        return surce.size();
    }


}
