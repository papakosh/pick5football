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

public class SelectedPicksAsyncService extends AsyncTask<Void, Integer, String> {

    public static final String SAVE_PICKS = "Save Picks";
    public static final String LOAD_PICKS = "Load Picks";
    public static final String SUBMIT_PICKS = "Submit Picks";

    private String actionType;
    private String savedSelections;
    private String submitSelections;
    private String matchWeek;
    private AlertDialog alertDialog;
    private Context context;

    public SelectedPicksAsyncService(Context context, String actionType, String savedSelections,
                                     String submitSelections, String matchWeek) {
        this.actionType = actionType;        
        this.savedSelections = savedSelections;
        this.submitSelections = submitSelections;
        this.matchWeek = matchWeek;
        this.context = context;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Done");
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
        File matchFile = createMatchFile();
        if (SAVE_PICKS.equals(actionType)) {
            alertDialog.setTitle("Saving Picks...");
            try {
                savePicksToFile(matchFile);
            } catch (IOException e) {
                Log.e("Saving Picks", e.getMessage(), e);
            }
        }else if (LOAD_PICKS.equals(actionType)) {
            StringBuffer tempSelections = new StringBuffer();
            alertDialog.setTitle("Loading Picks...");
           try {
               if (matchFile.canRead()) {
                   FileReader filereader = new FileReader(matchFile);
                   BufferedReader in = new BufferedReader(filereader);
                   String line;
                   while ((line = in.readLine()) != null) {
                       tempSelections.append(line.trim());
                       tempSelections.append(";");
                   }
               }else
                   alertDialog.setMessage("Can't Find Picks");
           }catch (Exception e){
               Log.e("Loading Picks", e.getMessage(),e);
           }
            savedSelections = tempSelections.toString();
        }
        else if (SUBMIT_PICKS.equals(actionType)) {
            alertDialog.setTitle("Submitting  Picks...");
            try {
                savePicksToFile(matchFile);

                String emailSubject = matchWeek +" NFL Picks";
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

    private void savePicksToFile(File matchFile) throws IOException {
        FileWriter filewriter = new FileWriter(matchFile);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(savedSelections);
        out.close();
    }

    protected void onPostExecute(String result) {
        alertDialog.show();
    }

    private File createMatchFile(){
        String tempWeek = matchWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
        File exstDir = Environment.getExternalStorageDirectory();
        return new File(exstDir.getPath() + "/Pick5FootballData/" + tempWeek + "-saved-picks.txt");
    }
}