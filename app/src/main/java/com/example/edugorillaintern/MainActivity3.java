package com.example.edugorillaintern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edugorillaintern.data.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity3 extends AppCompatActivity  {
    private Button button ;
    private LineChart lineChart;
    private BarData barData;
    private BarDataSet barDataSet;
   private ArrayList barEntries;
   private ProgressBar progressBar;
    private ArrayList<String > costAl;
  private BarChart barChart ;
   private ArrayList<Integer> dateAl;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
 // lineChart = findViewById(R.id.activity3_lineChart);
        //  connection progress bar
        progressBar= findViewById(R.id.MainActivity3_progressbar);
// button
        button= findViewById(R.id.MainActivity3_next);

//  button for openning the new actiity

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,MainActivity4.class);
                startActivity(intent);

            }
        });


        //  lineChart.setOnChartGestureListener(MainActivity3.this);
        // lineChart.setOnChartValueSelectedListener(MainActivity3.this);

//    lineChart.setDragEnabled(true);
//    lineChart.setScaleXEnabled(false);

        //=========================================================



        barChart = findViewById(R.id.activity3_BarChart);

       // getEntries();
     //   printGraph();


    }


    private void processJsonData(String data)  {

       // creating the array list of date and cost



        if (data != null){

            dateAl = new ArrayList<>();
            costAl= new ArrayList<>();

            try{
             // adding the json data to the json array for furher processs

                JSONArray jsonArray  = new JSONArray(data);
                for (int i = 0 ; i<jsonArray.length(); i++){

                    JSONObject object =  jsonArray.getJSONObject(i);

                   /// seprating the date data and cost data and store it into the String
                    String date = object.getString( "date_created");
                    String cost = object.getString( "amount");

                //    Log.i(""+i, "processJsonData:  ============== "+date+"    ========================   "+cost );


                  // seprating the date into year month and day
                    String[] ymd = date.split("-");

                   //  int year = Integer.parseInt(ymd[0]);
                    int month = Integer.parseInt(ymd[1]);
                    int day = Integer.parseInt(ymd[2]);

                 // seprating the july data from the json data
                  if (month == 07){
                      dateAl.add(day);
                      costAl.add(cost);
                   //   Log.i("", "processJsonData: "+date+"-----=========="+cost);

                  }

                }
                printGraph();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "can't fetch data ", Toast.LENGTH_SHORT).show();
        }
    }






    private void getEntries(){
      barEntries = new ArrayList();
//        barEntries.add(new BarEntry(1f,2));
//        barEntries.add(new BarEntry(2f,6));
//        barEntries.add(new BarEntry(3f,3));
//        barEntries.add(new BarEntry(1f,5));
//        barEntries.add(new BarEntry(4f,6));

        for (int  i = 0 ;i  <80 ;i++){
         barEntries.add(new BarEntry(dateAl.get(i),Float.parseFloat(costAl.get(i))));
       }
    }



    private void printGraph() {
        getEntries();
        progressBar.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
         barChart.setVisibility(View.VISIBLE);
//        // creating the arrylist of enty for y values
//        ArrayList<Entry> yValues = new ArrayList<>();
//        // adding the cost and date values into yValues
//
//        for (int  i = 0 ;i  < dateAl.size();i++){
//            yValues.add(new Entry(dateAl.get(i),Float.parseFloat(costAl.get(i))));
//        }
//
//      // adding the yValues into LineDateSet
//        LineDataSet lineDataSet = new LineDataSet(yValues,"Cost");
//        lineDataSet.setFillAlpha(110);
//        lineDataSet.setColor(Color.BLACK);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSet);
//        LineData lineData = new LineData(dataSets);
//        lineChart.setData(lineData);

        ///========================================================
        barDataSet = new BarDataSet(barEntries,"data set");
        barData = new BarData(barDataSet);
         barChart.setData(barData);
         barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);





    }





    @Override
    protected void onStart() {
        super.onStart();




        // creating the client object
        OkHttpClient client = new OkHttpClient();

        // accessing the json data
        Request request = new Request.Builder()
                .url("https://www.dropbox.com/s/n4i57r22rdx89cw/list2.json?dl=1")
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
                    MainActivity3.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           //  Log.i("MainActivity2", "run: "+myResponse);

                            // sending the json data to processJsonData method
                           processJsonData(myResponse);

                        }
                    });
                }
            }
        });
    }
}