package com.example.tektraining21.demorest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    public static final String PHOTO_BASE_URL = "http://services.hanselandpetal.com/photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        output = (TextView) findViewById(R.id.textView);
//        output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<MyTask>();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_do_task) {
            if (isOnline()){
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");

            }
            else Toast.makeText(this, "No network", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
    }

//    private void updateDisplay(String s) {
//
//        output.append(s+'\n');
//
//    }

    private void updateDisplay(List<Flower> flowerList) {

//        if (flowerList!=null) for (Flower flower : flowerList) {
//
//            output.append(flower.getName() + '\n');
//
//        }

        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.list_item_layout, flowerList);
        setListAdapter(adapter);




    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return  true;
        }
        else return false;

    }

    private class MyTask extends AsyncTask<String,String, List<Flower>> {

        @Override
        protected void onPreExecute() {


            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected List<Flower> doInBackground(String... strings) {


            String content = null;
            try {
                content = HttpManager.getData(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Flower> flowerList = JSONParser.parseFeed(content);

//            for (Flower flower: flowerList) {
//                String imageURL = PHOTO_BASE_URL + flower.getPhoto();
//                InputStream stream = null;
//                try {
//                    stream = (InputStream) new URL(imageURL).getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
//                    flower.setBitmap(bitmap);
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }



            return flowerList;

        }

        @Override
        protected void onPostExecute(List<Flower> flowerList) {

            updateDisplay(flowerList);
            tasks.remove(this);
            if (tasks.size() == 0){
                pb.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }
    }
}
