package project.codepath.nytimessearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by jnagaraj on 2/14/16.
 */
public class ArticleFactory {

    public static Article MakeArticle(JSONObject jsonObject) {
        Article article;
        try {

            String webUrl = jsonObject.getString("web_url");
            String headline = jsonObject.getJSONObject("headline").getString("main");
            String snippet = jsonObject.getString("snippet");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                String thumbnail = "http://nytimes.com/" + multimediaJson.getString("url");
                article = new ArticleWithImage(webUrl, headline, snippet, thumbnail);
            }else {
                article = new Article(webUrl, headline, snippet);
            }

            return article;

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();

        for(int x = 0; x < array.length(); x++) {
            try {
                results.add(MakeArticle(array.getJSONObject(x)));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
