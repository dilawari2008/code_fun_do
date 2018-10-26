package com.example.cell1;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.*;
import static com.squareup.okhttp.internal.Platform.get;

public class ToDoActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {


    public String latitude ,longitude,magnitude;
    public double xx,yy,cc;
    public


    //private static final String LOG_TAG = EarthquakeActivity.class.getName();
    String limit = "1";
    String minmag = "0";
    String minlongi = "-180";//-180       '-' means west and south
    String minlati = "-90";//-90          '+' means east and north
    String maxlongi = "180";//+180
    String maxlati = "90";//180

    public  String phonephone="";
    public  String namename="";

    private final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=" + minmag + "&limit=" + limit + "&minlongitude=" + minlongi + "&maxlongitude=" + maxlongi + "&minlatitude=" + minlati + "&maxlatitude=" + maxlati;

    private static final int EARTHQUAKE_LOADER_ID = 1;
    public ListView earthquakeListView;
    public EarthquakeAdapter mAdapter;

    private LocationManager locationManager;
    private LocationListener listener;
   // public String lati="0.0" ;
   // public String longi="0.0";
    public void locator() {
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
    }
    private TextView mEmptyStateTextView;

    private static final int JOB_ID=101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    private Button b;
    private static ToDoActivity instance;
    //private MobileServiceClient mClient;

    //Item Ite=new Item();
   // private TextView t;
    String ss="";

    private MobileServiceClient mClient;
    private MobileServiceTable<ToDoItem> mToDoTable;
    //private MobileServiceSyncTable<ToDoItem> mToDoTable;
    private ToDoItemAdapter mAdapter1;
    private EditText mTextNewToDo;
    private EditText mTextNewToDo1;
    private EditText mTextNewToDo2;
    private EditText mTextNewToDo3;

    private ProgressBar mProgressBar;

    Button bbbb;
    public View view;
    public ToDoItem item;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        instance=this;


         bbbb=(Button)findViewById(R.id.btn3);
         mAdapter1=ToDoItemAdapter.getInstances();

        bbbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbbb.setText("Details Entered");

                if (namename=="" || phonephone=="")
                {
                    Intent intent=new Intent(ToDoActivity.this,FirstPage.class);
                    startActivity(intent);

                }





              //  Toast.makeText(ToDoActivity.this,namename+" "+phonephone,Toast.LENGTH_SHORT).show();

            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        namename=sharedPreferences.getString("Name","");
        phonephone=sharedPreferences.getString("Phone","");

        //SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);

      //  Toast.makeText(ToDoActivity.this,sharedPreferences.getString("Name","")+" "+sharedPreferences.getString("Phone",""),Toast.LENGTH_SHORT).show();


        earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        locator();

        earthquakeListView.setAdapter(mAdapter);
       /* if(cc>=0)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ToDoActivity.this);
            builder1.setMessage("A fatal earthquake has been detected here. Are you safe?");
            builder1.setTitle("Are you SAFE?");
            builder1 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(ToDoActivity.this,"alllaaaa",Toast.LENGTH_SHORT).show();
                    //do things
                }
            });
            builder1.create().show();
        }*/





        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake currentEarthquake = mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        b = (Button) findViewById(R.id.button);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //  t.append("\n " + location.getLongitude() + " " + location.getLatitude());
           //     Toast.makeText(ToDoActivity.this, "\n " + location.getLongitude() + " " + location.getLatitude(), Toast.LENGTH_LONG).show();
                // ss+="\n " + location.getLongitude() + " " + location.getLatitude();

              //  Toast.makeText(ToDoActivity.this,"grth",Toast.LENGTH_LONG).show();
                xx = location.getLatitude();
                yy = location.getLongitude();
               // Log.d("vinay", "a=" + xx + " " + "b=" + yy);
                //lati = a;

                ///longi = b;

                String lll=""+xx;
                String lllo=""+yy;

                longitude = mAdapter.longitude;
                latitude = mAdapter.latitude;
                magnitude=mAdapter.formattedMagnitude;





                double aa = Double.parseDouble(latitude.toString());
                double bb = Double.parseDouble(longitude.toString());
                cc=Double.parseDouble(magnitude.toString());

                //if(xx<=aa+0.5 && xx>= aa-0.5 && yy<=bb+0.5 && yy>= bb-0.5 ){

                if (xx<=aa+0.5 && xx>= aa-0.5 && yy<=bb+0.5 && yy>= bb-0.5) {
                    if (mClient == null) {
                        return;
                    }

                    item = new ToDoItem();
                    view=(View)getCurrentFocus();

                    item.setText("" + lll);
                    item.setText1("" + lllo);
                    item.setText2("" + namename);
                    item.setText3("" + phonephone);

                    //item.setComplete(true);

                    // Toast.makeText(ToDoActivity.this,""+namename+" "+phonephone,Toast.LENGTH_SHORT).show();

                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                final ToDoItem entity = addItemInTable(item);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!entity.isComplete()) {
                                            mAdapter1.add(entity);
                                        }
                                    }
                                });
                            } catch (final Exception e) {
                                createAndShowDialogFromTask(e, "Error");
                            }
                            return null;
                        }
                    };

                    runAsyncTask(task);

                    mTextNewToDo.setText("");
                    mTextNewToDo1.setText("");
                    mTextNewToDo2.setText("");
                    mTextNewToDo3.setText("");

                }

                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                    if(cc>=5)//cc>5
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ToDoActivity.this);
                        builder1.setMessage("A fatal earthquake has been detected here. Are you safe?");
                        builder1.setTitle("Are you SAFE?");
                        builder1 .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               // mAdapter1.checkBox1.setChecked(true);
                                //ToDoItemAdapter.getInstances().performClick();//mAdapter1.checkBox2.setChecked(true);
                               // ToDoItemAdapter.getInstances().checkBox2.performClick();//mAdapter1.checkBox3.setChecked(true);
                               // ToDoItemAdapter.getInstances().checkBox3.performClick();//mAdapter1.checkBox.setChecked(true);
                               // ToDoItemAdapter.getInstances().checkBox.performClick();
                                //mAdapter1.checkall(view,item);
                                //Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaa","bbbbbbbbbbbbbbbbbb");
                                Context mContext=(ToDoActivity)getInstance();
                                if (mContext instanceof ToDoActivity) {
                                    ToDoActivity activity = (ToDoActivity) mContext;
                                    activity.checkItem(item);
                                }
                            }
                        });
                        builder1.create().show();
                    }

                }



               // Log.d("vinay", "lati=" + lati + " " + "longi" + longi);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

      //   configure_button();


        ComponentName componentName=new ComponentName(this,MjobSchedular.class);

        JobInfo.Builder builder=new JobInfo.Builder(JOB_ID,componentName);

        builder.setPeriodic(3600*1000);
         builder.setPersisted(true);

        jobInfo=builder.build();

        jobScheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        Button btn1=(Button)findViewById(R.id.btn1);
        Button btn2=(Button)findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Toast.makeText(ToDoActivity.this,"scheduled",Toast.LENGTH_LONG).show();
                jobScheduler.schedule(jobInfo);


            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.cancel(JOB_ID);
                Toast.makeText(ToDoActivity.this,"cancelled",Toast.LENGTH_LONG).show();
            }
        });

       // configure_button();





        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        mProgressBar.setVisibility(ProgressBar.GONE);

        try {
            mClient = new MobileServiceClient(
                    "https://cell1.azurewebsites.net",
                    this).withFilter(new ProgressFilter());

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });

            mToDoTable = mClient.getTable(ToDoItem.class);

            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);
            initLocalStore().get();

            mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);
            mTextNewToDo1 = (EditText) findViewById(R.id.textNewToDo1);
            mTextNewToDo2 = (EditText) findViewById(R.id.textNewToDo2);
            mTextNewToDo3 = (EditText) findViewById(R.id.textNewToDo3);

            mAdapter1 = new ToDoItemAdapter(this, R.layout.row_list_to_do);
            ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
            listViewToDo.setAdapter(mAdapter1);

            refreshItemsFromTable();

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL.Maybe due to poor internet connection"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            refreshItemsFromTable();
        }

        return true;
    }

    public void checkItem(final ToDoItem item) {
        if (mClient == null) {
            return;
        }

        item.setComplete(true);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {


                try {
                    checkItemInTable(item);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (item.isComplete()) {
                                item.setComplete(true);
                            }
                        }
                    });

                   // createAndShowDialogFromTask(e, "Error");


                return null;
            }
        };

        runAsyncTask(task);

    }

    public void checkItemInTable(ToDoItem item) throws ExecutionException, InterruptedException {
        mToDoTable.update(item).get();
    }

    public void addItem(View view) {
        if (mClient == null) {
            return;
        }

        final ToDoItem item = new ToDoItem();

        item.setText(mTextNewToDo.getText().toString());
        item.setText(mTextNewToDo1.getText().toString());
        item.setText(mTextNewToDo2.getText().toString());
        item.setText(mTextNewToDo3.getText().toString());
        item.setComplete(false);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final ToDoItem entity = addItemInTable(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!entity.isComplete()){
                                mAdapter1.add(entity);
                            }
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);

        mTextNewToDo.setText("");
        mTextNewToDo1.setText("");
        mTextNewToDo2.setText("");
        mTextNewToDo3.setText("");
    }

    public ToDoItem addItemInTable(ToDoItem item) throws ExecutionException, InterruptedException {
        ToDoItem entity = mToDoTable.insert(item).get();
        return entity;
    }

    private void refreshItemsFromTable() {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<ToDoItem> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter1.clear();

                            for (ToDoItem item : results) {
                                mAdapter1.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    private List<ToDoItem> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mToDoTable.where().field("complete").
                eq(val(false)).execute().get();
    }
    /*private List<ToDoItem> refreshItemsFromMobileServiceTableSyncTable() throws ExecutionException, InterruptedException {
        //sync the data
        sync().get();
        Query query = QueryOperations.field("complete").
                eq(val(false));
        return mToDoTable.read(query).get();
    }*/

    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }
    /*
    private AsyncTask<Void, Void, Void> sync() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MobileServiceSyncContext syncContext = mClient.getSyncContext();
                    syncContext.push().get();
                    mToDoTable.pull(null).get();
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        return runAsyncTask(task);
    }
    */

    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    public static ToDoActivity getInstance(){
        return instance;
    }


    public void configure_button(){

      //     b.setOnClickListener(new View.OnClickListener() {
        //@Override
          //  public void onClick(View view) {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        locationManager.requestLocationUpdates("gps", 3600*1000, 0, listener);
         //}
        //});
    }






    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_earthquakes);
        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {

        locator();
    }







}