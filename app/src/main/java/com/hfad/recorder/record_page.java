package com.hfad.recorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;



public class record_page extends AppCompatActivity {
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordpage);
        listView=(ListView)findViewById(R.id.listview1);


        ArrayList<String> MyFiles = new ArrayList<>();
        String path =  Environment.getExternalStorageDirectory() + File.separator
                      + Environment.DIRECTORY_MUSIC + File.separator + "RECORDINGS/";
        File f = new File(path);
        f.mkdirs();
        File[] files = f.listFiles();
        for (int i=0; i<files.length; i++)
            MyFiles.add(files[i].getName());


        listView.setAdapter((ListAdapter) new MyCustomAdapter(MyFiles, getApplicationContext()));


    }
}