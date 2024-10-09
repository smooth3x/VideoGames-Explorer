package com.example.videogames_explorer.services;

import android.os.StrictMode;

import com.example.videogames_explorer.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class gamesServiceAPI {

    public static ArrayList<Game> getHotGames() {
        ArrayList<Game> arr = new ArrayList<>();

        String sURL = "https://www.giantbomb.com/api/games/?api_key=2b5704ca211042d7e36887cc9ca19bd263caeb05&format=json&filter=original_release_date:2009-01-01+00%3A00%3A00|2024-10-09+00%3A00%3A00&sort=original_release_date:desc&limit=100&field_list=name,deck,description,original_release_date,platforms,image";

        URL url = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL(sURL);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        arr = getGameList(url);
        return arr;
    }

    public static ArrayList<Game> searchGames(String game) {
        ArrayList<Game> arr = new ArrayList<>();

        String sURL = "https://www.giantbomb.com/api/search?api_key=2b5704ca211042d7e36887cc9ca19bd263caeb05&format=json&query='" + game + "'&resources=game&sort=original_release_date:desc&limit=50&field_list=name,deck,description,original_release_date,platforms,image";

        URL url = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL(sURL);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        arr = getGameList(url);
        return arr;
    }

    public static ArrayList<Game> getGameList(URL url) {

        ArrayList<Game> arr = new ArrayList<>();

        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObject = root.getAsJsonObject();
            JsonArray rootArray = rootObject.getAsJsonArray("results");

            for (JsonElement je : rootArray) {
                JsonObject obj = je.getAsJsonObject();

                String name = obj.has("name") && !obj.get("name").isJsonNull() ? obj.get("name").getAsString() : "";
                String deck = obj.has("deck") && !obj.get("deck").isJsonNull() ? obj.get("deck").getAsString() : "";
                String originalReleaseDate = obj.has("original_release_date") && !obj.get("original_release_date").isJsonNull() ? obj.get("original_release_date").getAsString() : "";

                List<Game.Platform> platforms = new ArrayList<>();
                if (obj.has("platforms") && !obj.get("platforms").isJsonNull()) {
                    JsonArray platformsArray = obj.getAsJsonArray("platforms");
                    for (JsonElement platformElement : platformsArray) {
                        JsonObject platformObj = platformElement.getAsJsonObject();
                        String platformName = platformObj.has("name") && !platformObj.get("name").isJsonNull() ? platformObj.get("name").getAsString() : "";
                        platforms.add(new Game.Platform(platformName));
                    }
                }

                Game.Image image = null;
                if (obj.has("image") && !obj.get("image").isJsonNull()) {
                    JsonObject imageObj = obj.getAsJsonObject("image");
                    String imageUrl = imageObj.has("original_url") && !imageObj.get("original_url").isJsonNull() ? imageObj.get("original_url").getAsString() : "";
                    image = new Game.Image(imageUrl);
                }

                Game game = new Game(name, deck, originalReleaseDate, platforms, image);
                arr.add(game);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static String getGameTrailerUrl(String gameName) {
        String apiKey = "AIzaSyDBV3zYzwRIpfwlx5Dot_LBXorbOK6OWHA";  // Replace with your API key
        String searchUrl = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet"
                + "&q=" + gameName + " official trailer"
                + "&type=video"
                + "&key=" + apiKey
                + "&maxResults=1";

        try {
            URL url = new URL(searchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray items = jsonResponse.getJSONArray("items");

            if (items.length() > 0) {
                JSONObject firstItem = items.getJSONObject(0);
                JSONObject idObject = firstItem.getJSONObject("id");
                String videoId = idObject.getString("videoId");
                return "https://www.youtube.com/watch?v=" + videoId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // In case of failure, return null or handle appropriately
    }
}