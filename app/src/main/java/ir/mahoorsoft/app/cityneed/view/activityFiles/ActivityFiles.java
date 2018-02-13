package ir.mahoorsoft.app.cityneed.view.activityFiles;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.FilesAdapter;

public class ActivityFiles extends AppCompatActivity implements FilesAdapter.OnClickLisenerItem {

    RecyclerView list;
    FilesAdapter adapter;
    ArrayList<file> surce;
    ArrayList<File> folderList = new ArrayList<>();
    Stack<String> saveAddress = new Stack<>();
    Button btnBack;
    boolean flag = false;

    public ActivityFiles() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        btnBack = (Button) findViewById(R.id.btn_folder_list_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        surce = new ArrayList<>();
        adapter = new FilesAdapter(this, surce, this);
        list = (RecyclerView) findViewById(R.id.folderview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        String root = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        setSurce(root);

    }

    private void setSurce(String address) {

        surce.clear();
        folderList.clear();
        saveAddress.push(address);
        File dir = new File(address);
        File[] files = dir.listFiles();
        if (files == null) {
            if (flag) {
                Intent intent = getIntent();
                intent.putExtra("path", address);
                setResult(RESULT_OK, intent);
                this.finish();
            } else {
                Toast.makeText(ActivityFiles.this, "لطفا اجازه دسترسی به حافظه را فعال کنید و مجددا تلاش نمایید...", Toast.LENGTH_SHORT).show();
            }
        } else {

            for (int i = 0; i < files.length; i++) {

                file file = new file();
                file.fileName = files[i].getName();

                if (files[i].isDirectory()) {
                    file.Image = R.drawable.folder;
                    folderList.add(files[i]);
                    surce.add(file);
                }
                if (getIntent().getExtras() != null) {
                    if (getIntent().getExtras().getBoolean("isImage")) {
                        if (files[i].getName().toLowerCase().endsWith(".png") || files[i].getName().toLowerCase().endsWith(".jpeg") || files[i].getName().toLowerCase().endsWith(".jpg")) {
                            file.Image = R.drawable.file_orange_icon;
                            file.path = files[i].getAbsolutePath();
                            folderList.add(files[i]);
                            surce.add(file);
                        }
                    } else {
                        if (files[i].getName().toLowerCase().endsWith(".pdf")) {
                            file.Image = R.drawable.file_orange_icon;
                            file.path = files[i].getAbsolutePath();
                            folderList.add(files[i]);
                            surce.add(file);
                        }
                    }
                }

            }

        }


        adapter.notifyDataSetChanged();

    }


    @Override
    public void selectItem(int position) {
        flag = true;
        setSurce(folderList.get(position).getAbsolutePath());

    }

    @Override
    public void onBackPressed() {
        if (saveAddress.size() != 1) {
            saveAddress.pop();
            setSurce(saveAddress.pop());
        } else {

            finish();
            super.onBackPressed();
        }

    }
}