package com.bnavarro.pick5football;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class MatchupWeek {
	private String name;
	private Boolean picksSubmitted;
	
	final static private String APP_KEY = "1t3c5oggvr0hnhe";
	final static private String APP_SECRET = "1zh1mvowilxj04d";
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	
	
	public static void submitPicks (Matchup[] matchups, Activity mainActivity, DropboxAPI<AndroidAuthSession> mDBApi) throws FileNotFoundException, DropboxException{
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		
		mDBApi.getSession().startOAuth2Authentication(mainActivity);
		
		File file = new File("working-draft.txt");
		FileInputStream inputStream = new FileInputStream(file);
		Entry response = mDBApi.putFile("/magnum-opus.txt", inputStream,
		                                file.length(), null, null);
		Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
	}
	

}
