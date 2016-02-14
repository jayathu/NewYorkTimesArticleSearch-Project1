package project.codepath.nytimessearch.models;

/**
 * Created by jnagaraj on 2/11/16.
 */
public class Article {

    String webUrl;
    String headline;
    String snippet;

    public String getWebUrl() {
        return webUrl;
    }
    public String getHeadline() {return headline;}
    public String getSnippet() { return snippet; }

    public Article(String webUrl, String headline, String snippet) {

        this.webUrl = webUrl;
        this.headline = headline;
        this.snippet = snippet;
    }
}
