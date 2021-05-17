package org.meicode.weatherrapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class NewCityActivity extends AppCompatActivity {
    EditText cityName;
    Button button;
    TextView tvResult;
    LinearLayout layout;
    DecimalFormat df = new DecimalFormat("#.##");
    private final String URL1 = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final String URL2 = "&units=metric&APPID=b6bef0411eff3e08c48e10f6606ce109";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcity);

        cityName = findViewById(R.id.searchCity);
        button = findViewById(R.id.btn);
        tvResult = findViewById(R.id.tvResult);
    }

    public void getWeatherDetails(View view){
        String tempUrl = "";
        String city = cityName.getText().toString().trim();
        if (city.equals("")){
            tvResult.setText("Please Enter City");
         }else {
            String URL3 = URL1 + city + URL2;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //  Log.d("response", response);//
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        int humidity = jsonObjectMain.getInt("humidity");
                        double temp = jsonObjectMain.getDouble("temp");
                        tvResult.setTextSize(22);
                        output += "Current Weather of " + city + "\n " + "\n Temp: " + df.format(temp) + "\n Description: " + description + "\n Humidity: " + humidity;
                        tvResult.setText(output);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

}
