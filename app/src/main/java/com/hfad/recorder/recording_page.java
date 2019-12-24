package com.hfad.recorder;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.content.ContentValues.TAG;

public class recording_page {


    private static MediaRecorder recorder;


    public static void onRecord(boolean start) {
        if (start) {
          //  Log.d(TAG,"i am starting recording");
            startRecording();
        } else {
            //Log.d(TAG,"i am stoping recording");
            stopRecording();
        }
    }





    private static void startRecording() {
       //Log.d(TAG,"i am in starting recording");
        String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
            String path =  Environment.getExternalStorageDirectory() + File.separator
                    + Environment.DIRECTORY_MUSIC + File.separator + "RECORDINGS";
            path =path+ "/"+out+".3gp";


         //   Log.d(TAG, path);
            recorder=new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(path);


        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.d(TAG, "prepare() failed");
        }

        recorder.start();
    }

    private static void stopRecording() {
        //Log.d(TAG,"i am in stop recording");
        recorder.stop();
        recorder.release();
        recorder = null;
    }

}


