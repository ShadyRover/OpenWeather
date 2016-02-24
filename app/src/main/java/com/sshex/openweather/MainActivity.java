package com.sshex.openweather;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.sshex.openweather.models.WeatherModel;
import com.sshex.openweather.network.ApiInterface;
import com.sshex.openweather.storage.DataStorage;
import com.sshex.openweather.storage.OpenDatabaseHelper;
import com.sshex.openweather.utils.HelpUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private RestAdapter restAdapter;
    private ApiInterface api;
    private TextView main_temp,max_temp,min_temp,weather_name,air_pres,water,wind,tv_city;
    private ImageView img_weather,settingsBtn;
    private LinearLayout max_min_layer;
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider,city;
    Dao<WeatherModel.Main, Long> mainWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.API_URL)).build();
         api = restAdapter.create(ApiInterface.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        try {
            Location location = locationManager.getLastKnownLocation(provider);


            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {
                Toast.makeText(MainActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Init views
        main_temp = (TextView)findViewById(R.id.main_temp);
        max_temp =(TextView)findViewById(R.id.tv_max_temp);
        min_temp = (TextView)findViewById(R.id.tv_min_temp);
        weather_name = (TextView)findViewById(R.id.tv_weather);
        air_pres = (TextView)findViewById(R.id.tv_apres);
        water = (TextView)findViewById(R.id.water_textView);
        wind = (TextView)findViewById(R.id.tv_wind);
        img_weather = (ImageView)findViewById(R.id.img_weather);
        max_min_layer = (LinearLayout)findViewById(R.id.max_min_layer);
        tv_city = (TextView)findViewById(R.id.tv_city);
        settingsBtn = (ImageView)findViewById(R.id.settings_btn);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.settings);
                Button save_btn =(Button)dialog.findViewById(R.id.save_button);
                final EditText city = (EditText)dialog.findViewById(R.id.city_et);
                city.setText(DataStorage.getCity(getApplicationContext()));
                final Switch switch_units = (Switch)dialog.findViewById(R.id.switch_units);
                if(!DataStorage.getUnits(getApplicationContext()).equals("metric"))switch_units.setChecked(false);
                save_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataStorage.saveCity(city.getText().toString(),getApplicationContext());
                        String units="imperial";
                        if(switch_units.isChecked())units="metric";
                        DataStorage.saveUnits(units,getApplicationContext());
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getWeather(DataStorage.getCity(getApplicationContext()));
                    }
                });

            }
        });

        OpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                OpenDatabaseHelper.class);

        try {
            mainWeather = todoOpenDatabaseHelper.getMainWeather();
            Log.d("SQL","OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        city = DataStorage.getCity(getApplicationContext());
        getWeather(city);

    }

    private void getCurrentCity(double lat, double lon){
        Geocoder geoCoder = new Geocoder(this, Locale.ENGLISH);
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lon, 1);
            DataStorage.saveCity(address.get(1).getLocality(),getApplicationContext());

        } catch (IOException e) {}
        catch (NullPointerException e) {}
    }

    private void getWeather(final String city){
        api.getWeather(city, getString(R.string.API_KEY),DataStorage.getUnits(getApplicationContext()), new Callback<WeatherModel>() {
            @Override
            public void success(WeatherModel weatherModel, Response response) {
                if(weatherModel.getCod()!=200) return;

                main_temp.setText(weatherModel.getMain().getTemp().toString());
                weather_name.setText(weatherModel.getWeather().get(0).getMain());
                air_pres.setText(weatherModel.getMain().getPressure().toString());
               // water.setText(weatherModel.getMain().getHumidity());
                tv_city.setText(city);
                wind.setText(weatherModel.getWind().getSpeed().toString());

                //Write to DB
                try {
                    mainWeather.create(new WeatherModel.Main(weatherModel.getMain().getTemp(),weatherModel.getMain().getPressure(),weatherModel.getMain().getHumidity()));
                    List<WeatherModel.Main> list = mainWeather.queryForAll();
                    Log.d("ORM","Create");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(weatherModel.getMain().getTempMax()!=null && weatherModel.getMain().getTempMin()!=null)
                    {
                        max_min_layer.setVisibility(View.VISIBLE);
                        max_temp.setText(weatherModel.getMain().getTempMax().toString());
                        min_temp.setText(weatherModel.getMain().getTempMin().toString());
                    }
                else max_min_layer.setVisibility(View.GONE);
                switch (HelpUtils.getWeatherIcon(weatherModel.getWeather().get(0).getIcon())){
                    case "01":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.weathersunny));
                        break;
                    case "02":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.partlycloudy));
                        break;
                    case "03":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                        break;
                    case "04":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                        break;
                    case "09":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.pouring));
                        break;
                    case "10":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                        break;
                    case "11":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.lightning));
                        break;
                    case "13":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.snowy));
                        break;
                    case "50":
                        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Server is down now...Sorry!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onLocationChanged(Location location) {
        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.ENGLISH);
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.
            getCurrentCity(lat,lng);
        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
