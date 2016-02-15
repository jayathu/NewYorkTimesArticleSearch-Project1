package project.codepath.nytimessearch.models;

import org.parceler.Parcel;

/**
 * Created by jnagaraj on 2/14/16.
 */

@Parcel
public class QueryFilters {

    public static final String NEWEST = "newest";
    public static final String OLDEST = "oldest";

    public String beginDateString;
    public String sortOrder;
    public String news_desk;
    public int month;
    public int day;
    public int year;

    public QueryFilters() {

    }
    public  QueryFilters(int month, int day, int year, String sortOrder, String news_desk) {

        this.month = month;
        this.year = year;
        this.day = day;

        this.beginDateString = String.valueOf(year);

        if(month < 10) {
            this.beginDateString += "0" + month;
        }
        else {
            this.beginDateString += String.valueOf(month);
        }
        if( day < 10) {
            this.beginDateString += "0" + day;
        }
        else {
            this.beginDateString += String.valueOf(day);
        }

        this.sortOrder = sortOrder;
        this.news_desk = news_desk;
    }


}
