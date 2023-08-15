import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {
    // get your api key from api-ninjas.com
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private int temp;
    private int feels_like;
    private int humidity;
    private String error;

    public static void getWeather(String city) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.api-ninjas.com/v1/weather?city=" + city))
                .header("X-Api-Key", API_KEY)
                .GET().build();

        // httpclient sends our get() request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // we need the help of gson to transfer the json into our weather class
        Gson gson = new Gson();
        Weather responseBody = gson.fromJson(getResponse.body(), Weather.class);
        if (responseBody.getError() == null) {
            System.out.println("Weather for " + city + ":");
            System.out.println("Temperature: " + responseBody.getTemp());
            System.out.println("Feels like: " + responseBody.getFeelsLike());
            System.out.println("Humidity: " + responseBody.getHumidity());
        }
        else System.out.println("Couldn't find weather for your input.");

    }

    public int getTemp() {
        return temp;
    }

    public int getFeelsLike() {
        return feels_like;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getError() {
        return error;
    }
}


