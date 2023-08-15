import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Currency {
    // get your api key from api-ninjas.com
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private double new_amount;
    private String new_currency;
    private String old_currency;
    private double old_amount;

    public static void getCurrencyRate(String currencyFrom, String currencyTo, double amount) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.api-ninjas.com/v1/convertcurrency?have=" + currencyFrom +
                        "&want=" + currencyTo + "&amount=" + amount))
                .header("X-Api-Key", API_KEY)
                .GET().build();

        // httpclient sends our get() request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // we need the help of gson to transfer the json in to our currency class
        Gson gson = new Gson();
        Currency responseBody = gson.fromJson(getResponse.body(), Currency.class);
        System.out.println(responseBody.getOld_amount() + responseBody.getOld_currency() +
                " = " + responseBody.getNew_amount() + responseBody.getNew_currency());

    }

    /*
    We only need getters here to help us get the correct fields from the http response json
     */
    public double getNew_amount() {
        return new_amount;
    }

    public double getOld_amount() {
        return old_amount;
    }

    public String getNew_currency() {
        return new_currency;
    }

    public String getOld_currency() {
        return old_currency;
    }
}
