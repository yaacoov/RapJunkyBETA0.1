package com.codeoasis.testwaves;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyService extends Service {
private String TAG="yanJunky";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:before ");
        useFileAsyncTask();
        Log.d(TAG, "onStartCommand: after");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void useFileAsyncTask() {
        FileWorkerAsyncTask task = new FileWorkerAsyncTask(this);
        task.execute();
    }

    private class FileWorkerAsyncTask extends AsyncTask<Void, Void, Void> {

        private Service myContextRef;
        private Resources resources;
        private String TAG = "yanyan";

        public FileWorkerAsyncTask(Service myContextRef) {
            this.myContextRef = myContextRef;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final int[] mSongs = new int[] { R.raw.sound0, R.raw.sound1, };
            for (int i = 0; i < mSongs.length; i++) {
                try {
                    String path = Environment.getExternalStorageDirectory() + "/RapJunky/"+"/default/";
                    File dir = new File(path);
                    if (dir.mkdirs() || dir.isDirectory()) {
                        String str_song_name = i + ".mp3";
                        CopyRAWtoSDCard(mSongs[i], path + File.separator + str_song_name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           

            
            Log.d("yanfile","done");
            return null;
        }





    }

    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}