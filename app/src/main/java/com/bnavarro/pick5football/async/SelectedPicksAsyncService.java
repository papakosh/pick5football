package com.bnavarro.pick5football.async;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by navman on 11/10/2015.
 */
public class SelectedPicksAsyncService extends AsyncTask<Void, Integer, String> {

    private String actionType;
    private String savedSelections;
    private String submitSelections;
    private String matchWeek;
    private AlertDialog alertDialog;
    private Context context;

    public SelectedPicksAsyncService(Context context, String actionType, String savedSelections, String submitSelections, String matchWeek) {
        this.actionType = actionType;
        this.savedSelections = savedSelections;
        this.submitSelections = submitSelections;
        this.matchWeek = matchWeek;
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Done");
        builder.setTitle("Picks Action Status");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (actionType.equals("Save Picks")) {
            // Prepare file details - for the value of week strip out spaces and make lower
            // case, then create the file
            String tempWeek = matchWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
            File exstDir = Environment.getExternalStorageDirectory();
            File matchFile = new File(exstDir.getPath() + "/Pick5FootballData/" + tempWeek + "-saved-picks.txt");
            try {
                FileWriter filewriter = new FileWriter(matchFile);

                //writes to local file
                BufferedWriter out = new BufferedWriter(filewriter);
                out.write(savedSelections);
                out.close();
                alertDialog.setMessage("Picks Saved");
            } catch (IOException e) {
                Log.e("Saving Picks", e.getMessage(), e);
            }
        }else if (actionType.equals("Load Picks")) {
            String tempWeek = matchWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
            File exstDir = Environment.getExternalStorageDirectory();
            File matchFile = new File(exstDir.getPath() + "/Pick5FootballData/" + tempWeek + "-saved-picks.txt");
            StringBuffer tempSelections = new StringBuffer();
           try {
               if (matchFile.canRead()) {
                   FileReader filereader = new FileReader(matchFile);
                   BufferedReader in = new BufferedReader(filereader);
                   String line;
                   while ((line = in.readLine()) != null) {
                       tempSelections.append(line.trim());
                       tempSelections.append(";");
                   }
                   alertDialog.setMessage("Picks Loaded");
               }else
                   alertDialog.setMessage("Can't Find Picks");

           }catch (Exception e){
               Log.e("Loading Picks", e.getMessage(),e);
           }
                    savedSelections = tempSelections.toString();
        }
        else if (actionType.equals("Submit Picks")) {

            String tempWeek = matchWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
            File exstDir = Environment.getExternalStorageDirectory();
            File matchFile = new File(exstDir.getPath() + "/Pick5FootballData/" + tempWeek + "-saved-picks.txt");

            try {
                // Save picks
                FileWriter filewriter = new FileWriter(matchFile);

                //writes to local file
                BufferedWriter out = new BufferedWriter(filewriter);
                out.write(savedSelections);
                out.close();

                // Submit picks
                String emailSubject = tempWeek +"NFL Picks";
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message / rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                i.putExtra(Intent.EXTRA_TEXT, submitSelections);

                context.startActivity(Intent.createChooser(i, "Send mail..."));
            }
            catch (Exception e ) {
              Log.e("Submit Picks", e.getMessage(), e);
            }
        }
        return savedSelections;
    }

    protected void onPostExecute(String result) {

        alertDialog.show();
    }
}