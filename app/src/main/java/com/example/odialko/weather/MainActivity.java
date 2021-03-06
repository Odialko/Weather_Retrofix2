package com.example.odialko.weather;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.odialko.weather.data.model.Query;
import com.example.odialko.weather.data.model.Weather;
import com.example.odialko.weather.data.remote.WeatherAPI;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRefresh;
    TextView tvTemp, tvLastUpdate, tvCity, tvCondit;
    ImageView img;
    WebView web;
//    Query query = response.body().getQuery();
    String myUrl = "http://l.yimg.com/a/i/us/we/52/30.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRefresh = (Button)findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        tvTemp = (TextView)findViewById(R.id.tvTemp);
        tvLastUpdate = (TextView)findViewById(R.id.tvLastUpdate);
        tvCity = (TextView)findViewById(R.id.tvCity);
        tvCondit = (TextView)findViewById(R.id.tvCondit);
//        img = (ImageView)findViewById(R.id.img);
        web = (WebView) findViewById(R.id.web);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRefresh:
                onClick_button_refresh();
                break;
        }
    }

    public void onClick_button_refresh() {

        WeatherAPI.Factory.getIstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Query query = response.body().getQuery();
                tvTemp.setText(query.getResults().getChannel().getItem().getCondition().getTemp() + "°C");
                tvCity.setText(query.getResults().getChannel().getLocation().getCity());
                tvLastUpdate.setText(query.getResults().getChannel().getLastBuildDate());
                tvCondit.setText(query.getResults().getChannel().getItem().getCondition().getText());
//                img.setImageURI(Uri.parse("http://l.yimg.com/a/i/us/we/52/32.gif"));
//                img.setImageURI(Uri.parse(query.getResults().getChannel().getImage().getUrl()));

//                img.setImageDrawable(Drawable.createFromPath(query.getResults().getChannel().getImage().getUrl()));
//                Picasso.with(getApplicationContext()).load("");
                web.loadUrl("http://l.yimg.com/a/i/us/we/52/" + query.getResults().getChannel().getItem().getCondition().getCode() + ".gif");


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Злетіло нахуй, тисни ще", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClick_button_refresh();
    }
}
