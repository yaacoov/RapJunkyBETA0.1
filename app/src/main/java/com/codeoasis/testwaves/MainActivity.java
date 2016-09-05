package com.codeoasis.testwaves;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codeoasis.testwaves.utils.NavigationDrawer.NavigationDrawerCallbacks;
import com.codeoasis.testwaves.utils.NavigationDrawer.NavigationDrawerFragment;
import com.codeoasis.testwaves.utils.SongsManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {
    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private int currentSongIndex = 0;

   /* private Call<File> getFilesCall;
    private RestClient restClient;*/
    private SongsManager songsManager;
    private String TAG = "RapJunky";
    private String url;
    private Resources resources;


    //Todo: toolbar,navigation drawer, loading
// init ->,soundpoolmanager


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songsManager = new SongsManager();
        songsManager.createFolder();
        url="https://api.backendless.com/78E983A5-BBF1-3B4D-FF1B-420A24AD8E00/v1/files/RapJunky/rapbeatsample.mp3";




        /*restClient= RestClient.getRestClient();*/


        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

     /*   //backendless api
        String appVersion= "v1";
        String appID ="78E983A5-BBF1-3B4D-FF1B-420A24AD8E00";
        Backendless.initApp( this, appID, "3EEA829B-1082-5E4D-FF41-DA466A6CFE00", appVersion );
        getFiles();*/
        showMainFragment();

    }

    private void makeMusic() {
        File file = new File(this.getFilesDir() + File.separator +"/RapJunky/" + "DefaultProperties.mp3");


        try {

            InputStream inputStream =resources.openRawResource(R.raw.sound0);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte buf[]=new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0) {
                fileOutputStream.write(buf,0,len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {}
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songIndex");
            // play selected song
        //    playSong(currentSongIndex);
        }

    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                



                break;

         case 1:
         Intent i = new Intent(MainActivity.this,AlbumActivity.class);
                startActivity(i);
             break;


            case 2:
                Fragment wordSettingsFragment = new WordSettignsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, wordSettingsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            /*case 3:
                Fragment FragmentAlbum = new FragmentAlbum();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, FragmentAlbum);
                transaction.addToBackStack(null);
                transaction.commit();

                break;*/

        }
        }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }






    private void showMainFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("songId", currentSongIndex);
        MainFragment fragment_main = new MainFragment();
        fragment_main.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
               // .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.container, fragment_main)
                .commit();
    }
    private void getFiles(){
       /* getFilesCall=restClient.getFiles("RapJunky","rapbeatsample");
        getFilesCall.enqueue(new Callback<File>() {
            @Override
            public void onResponse(Call<File> call, Response<File> response) {
                String fileName = response.body().getName();
                try {
                    InputStream input = new FileInputStream(response.body());
                    File path = Environment.getExternalStorageDirectory();
                    File file = new File(path+"RapJunky/default",fileName);
                    BufferedOutputStream output = new BufferedOutputStream(
                            new FileOutputStream(file));
                    byte data[] = new byte[1024];

                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }

                    output.flush();

                    output.close();
                } catch (IOException e) {
                    String logTag = "TEMPTAG";
                    Log.e(logTag, "Error while writing file!");
                    Log.e(logTag, e.toString());
                }

            }

            @Override
            public void onFailure(Call<File> call, Throwable t) {

            }
        });*/

    }




}