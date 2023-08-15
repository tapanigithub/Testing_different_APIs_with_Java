import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AudioToText {
    /* get your api key from assemblyai.com */
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private String text;
    private String status;
    private String id;
    private double confidence;

    public static void convertAudioToText(String audioUrl) throws URISyntaxException, IOException, InterruptedException {

        AudioToText transcript = new AudioToText();
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                .header("Authorization", API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString("{\"audio_url\": " +'"' + audioUrl + '"' + "}" ))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        AudioToText responseBody = gson.fromJson(postResponse.body(), AudioToText.class); // transfer the json to a class
        String id = responseBody.getId(); /* we only want the id from here, because we need to do our get
                request with it */

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.assemblyai.com/v2/transcript/" + id)) // get request with the id
                .header("Authorization", API_KEY)
                .GET()
                .build();

        while (true) {
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            responseBody = gson.fromJson(getResponse.body(), AudioToText.class);
            if (responseBody.getStatus().equals("completed") || responseBody.getStatus().equals("error")) {
                break;
            }
            System.out.println("Processing..");
            Thread.sleep(1500); // make it look like it's doing something
        }
        if (responseBody.getStatus().equals("completed")) {
            System.out.println("Completed!\n" + "Confidence: " + responseBody.getConfidence());
            System.out.println("Text: " + responseBody.getText());
        }
        else System.out.println("There was an error :(");
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public double getConfidence() {
        return confidence;
    }
}
