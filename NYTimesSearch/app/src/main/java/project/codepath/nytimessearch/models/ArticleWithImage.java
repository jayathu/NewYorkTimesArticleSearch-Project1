package project.codepath.nytimessearch.models;

/**
 * Created by jnagaraj on 2/14/16.
 */
public class ArticleWithImage extends Article {

    String thumbNail;

    public String getThumbNail() { return thumbNail; }

    public  ArticleWithImage(String webUrl, String headline, String snippet, String thumbNail) {
        super(webUrl, headline, snippet);
        this.thumbNail = thumbNail;
    }
}
