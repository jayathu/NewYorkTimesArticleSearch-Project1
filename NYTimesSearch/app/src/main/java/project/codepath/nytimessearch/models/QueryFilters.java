package project.codepath.nytimessearch.models;

import org.parceler.Parcel;

/**
 * Created by jnagaraj on 2/14/16.
 */

@Parcel
public class QueryFilters {

    public static final String NEWEST = "newest";
    public static final String OLDEST = "oldest";

    public String beginDate;
    public String sortOrder;
    public String news_desk;

    public QueryFilters() {

    }
    public  QueryFilters(String beginDate, String sortOrder, String news_desk) {

        this.beginDate = beginDate;
        this.sortOrder = sortOrder;
        this.news_desk = news_desk;
    }




}
