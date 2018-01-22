package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.activityFiles.file;




/*


  Created by M-gh on 07-Aug-17.



 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.Holder> {


    public interface OnClickLisenerItem {

        public void selectItem(int position);

    }

    OnClickLisenerItem onClickLisenerItem;

    private ArrayList<file> fileList = new ArrayList<>();
    private Context context;


    public FilesAdapter(Context context, ArrayList<file> fileList, OnClickLisenerItem onClickLisenerItem) {

        this.context = context;
        this.fileList = fileList;
        this.onClickLisenerItem = onClickLisenerItem;


    }


    class Holder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView fileName;
        ImageView img;


        Holder(View itemView) {
            super(itemView);

            fileName = (TextView) itemView.findViewById(R.id.txt_item_backup);
            img = (ImageView) itemView.findViewById(R.id.img_backup_item);
            item = (LinearLayout) itemView.findViewById(R.id.backupItem);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_backup, parent, false);

        Holder holder = new Holder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        final file custumer1 = fileList.get(position);

        holder.fileName.setText(custumer1.fileName);
        holder.img.setImageResource(custumer1.Image);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLisenerItem.selectItem(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return fileList.size();
    }



}
