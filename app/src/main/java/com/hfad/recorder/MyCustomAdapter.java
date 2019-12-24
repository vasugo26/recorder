package com.hfad.recorder;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;


class MyCustomAdapter  extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout, null);
        }

        final Button itmbttn= view.findViewById(R.id.btn1);
        Button editbtn= view.findViewById(R.id.btn2);
        itmbttn.setText(list.get(position));

        itmbttn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                String file_name = (String) itmbttn.getText();
                String path = Environment.getExternalStorageDirectory() + File.separator
                        + Environment.DIRECTORY_MUSIC + File.separator + "RECORDINGS/" + file_name;

                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(context, "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                }
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String file_name = (String) itmbttn.getText();
                String old_name =  File.separator
                        + Environment.DIRECTORY_MUSIC + File.separator + "RECORDINGS/" + file_name;


                String name="New Name  "+list.get(position);
                String new_name=File.separator
                        + Environment.DIRECTORY_MUSIC + File.separator + "RECORDINGS/" + name;


                File location = Environment.getExternalStorageDirectory();
                File from = new File(location,old_name);
                File to = new File(location,new_name);
                from.renameTo(to);
                Toast.makeText(context, "Kindly go back and reopen this page to refresh names\n" +
                        "The file with changed name will go to the bottom of the list", Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }

}
