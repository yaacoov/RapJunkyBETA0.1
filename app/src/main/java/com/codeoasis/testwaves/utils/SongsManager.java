package com.codeoasis.testwaves.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {

	// SDCard Path
	final String MEDIA_PATH = Environment.getExternalStorageDirectory()+"/RapJunky/";
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private Context context;
    private String filesName;
	
	// Constructor
	public SongsManager(){
        this.context=context;
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(String name){
		File home = new File(MEDIA_PATH+name);

		if (home.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : home.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());

				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}

    public void createFolder() {

        File f = new File(MEDIA_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        File f1 = new File(MEDIA_PATH+"default");
        if (!f1.exists()) {
            f1.mkdirs();
        }
    }

    /**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
    
}
