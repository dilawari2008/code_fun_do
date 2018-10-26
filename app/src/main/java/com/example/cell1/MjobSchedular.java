package com.example.cell1;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class MjobSchedular extends JobService {


    private static final boolean TODO = true;
    private MjobExecuter mjobExecuter;
    String ss;


    @Override
    public boolean onStartJob(final JobParameters params) {

        mjobExecuter = new MjobExecuter() {

            @Override
            protected void onPostExecute(String s) {



                ToDoActivity.getInstance().configure_button();


             //      Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                //Toast.makeText(MjobSchedular.this,"\n " + location.getLongitude() + " " + location.getLatitude(),Toast.LENGTH_LONG).show();
                jobFinished(params, true);
            }
        };

        mjobExecuter.execute();

        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        mjobExecuter.cancel(true);
        return true;
    }
}



