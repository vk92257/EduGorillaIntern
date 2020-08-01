package com.example.edugorillaintern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edugorillaintern.data.MyAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
      public  List<String> userName,emails;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;
        private RecyclerView.LayoutManager layoutManager;
   private  MyAdapter myAdapter;
    @Override
 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

            progressBar = findViewById(R.id.MainActivity2_progressbar);

        recyclerView = findViewById(R.id.MainActivity2_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        userName = new ArrayList<>();
        emails = new ArrayList<>();






    }

        private void processJsonData(String data)  {
            if (data != null){
               try{
               // using this code we are seprating the required information according to our need
                JSONObject jsonObject  = new JSONObject(data);
                   JSONArray jsonArray = jsonObject.getJSONArray("data");
                   for (int i = 0;i<10;i++){
                       JSONObject object =  jsonArray.getJSONObject(i);
                       String name = object.getString( "name");
                       String email = object.getString( "email");
                    //   Log.i(""+i, "processJsonData:  ============== "+name+"    ========================   "+email );
                            userName.add(name);
                            emails.add(email);
                    }

                   progressBar.setVisibility(View.INVISIBLE);
                   myAdapter = new MyAdapter(this,userName,emails);
                   recyclerView.setAdapter(myAdapter);
                   myAdapter.notifyDataSetChanged();

                   }
               catch (Exception e){
                   e.printStackTrace();
               }
            }
            else{
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "can't fetch data ", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    protected void onStart() {
        super.onStart();

        // creating the client object
        OkHttpClient client = new OkHttpClient();

        // accessing the json data
        Request request = new Request.Builder()
                .url("https://www.dropbox.com/s/1hh8vh7whv6cjme/list1.json?dl=1")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // if response is failed than it will print the error
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                if (response.isSuccessful()){


                    //  storing the json data in myResponse variable
                    final String myResponse = response.body().string();

                    // going to the main thread to make changes into the ui
                    MainActivity2.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Log.i("MainActivity2", "run: "+myResponse);

                            // sending the json data to processJsonData method
                            processJsonData(myResponse);

                        }
                    });
                }
            }
        });
    }
}