import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.bkendall.bk_weather.FetchWeather;
import com.bkendall.bk_weather.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetWeatherFragment extends Fragment {
    private String latitude;
    private String longitude;
    private String secret_api_key;

    private Handler handler;

    public GetWeatherFragment(double lat, double lon, String api_key){
        latitude = String.valueOf(lat);
        longitude = String.valueOf(lon);
        secret_api_key = api_key;
        handler = new Handler();
    }

    private void fetchWeather(){
        new Thread(){
            public void run(){
                try {
                    final JSONObject json = FetchWeather.getForecast("0", "0", getString(R.string.api_key));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
