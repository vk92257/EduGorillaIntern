package com.example.edugorillaintern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edugorillaintern.data.MyAdapter;
import com.example.edugorillaintern.receiver.NetworkStateChangeReciever;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="MainActivity4" ;
   private ArrayList<String> titleAl,contentAl;
    private Button prev,next;
    private WebView webView;
    private TextView title;
    private int position = 0;
    private ProgressBar progressBar;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // adding  the xml button , webview and textView whit code
         prev = findViewById(R.id.MainActivity4_leftButton);
         next = findViewById(R.id.MainActivity4_RightButton);
         webView = findViewById(R.id.MainActivity4_webview);
         title = findViewById(R.id.MainActivity4_title);
         prev.setOnClickListener(this);
         next.setOnClickListener(this);
        progressBar= findViewById(R.id.MainActivity4_progresBar);
         // adding brodcast reciever for internt connection notification

         NetworkStateChangeReciever networkStateChangeReciever = new NetworkStateChangeReciever();
         IntentFilter intentFilter = new IntentFilter();
         intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
         intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(networkStateChangeReciever,intentFilter);

     }


     private void showingData(int position){
        progressBar.setVisibility(View.INVISIBLE);
         title.setText(titleAl.get(position));
         final String mimeType = "text/html";
         final String encoding = "UTF-8";
         webView.loadDataWithBaseURL("", contentAl.get(position), mimeType, encoding, "");

     }

    private void processJsonData(String data)  {
        if (data != null){
            titleAl = new ArrayList<>();
            contentAl = new ArrayList<>();
            try{
              JSONArray jsonArray = new JSONArray(data);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject object =  jsonArray.getJSONObject(i);
                    String titel = object.getString( "title");
                    String content = object.getString( "content");
                   // Log.i(TAG, "processJsonData: "+"  =====  titile --- "+titel+"=========content"+content);


                    titleAl.add(titel);
                    contentAl.add(content);

                }
                showingData(position);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, " internet is not working can't fetch data ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.MainActivity4_leftButton:

                // if the user press the prev button
                if (position>=1){

                    position--;
                showingData(position);
                }else
                    Toast.makeText(this, "Reached the Minimum limit", Toast.LENGTH_SHORT).show();
                break;

            case R.id.MainActivity4_RightButton:

                // if user press the next button
                if (position<=2){
                    position++;
                showingData(position);
                }
                else
                    Toast.makeText(this, "Reached the Max limit", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // creating the client object
        OkHttpClient client = new OkHttpClient();

        // accessing the json data
        Request request = new Request.Builder()
                .url("https://www.dropbox.com/s/ep7v5yex3fjs3s1/webview.json?dl=1")
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
                    MainActivity4.this.runOnUiThread(new Runnable() {
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