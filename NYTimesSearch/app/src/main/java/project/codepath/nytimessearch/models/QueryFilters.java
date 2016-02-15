package project.codepath.nytimessearch.models;

import org.parceler.Parcel;

/**
 * Created by jnagaraj on 2/14/16.
 */

@Parcel
public class QueryFilters {

    public boolean oldest;
    public boolean politics;
    public boolean technology;
    public boolean sports;

    public int month;
    public int day;
    public int year;

    public QueryFilters() {

    }

    public void setBeginDate(int month, int day, int year) {

        this.month = month;
        this.year = year;
        this.day = day;
    }

    public void setSortOrder(boolean oldest) {
        this.oldest = oldest;
    }

    public void setNews_desk(boolean politics, boolean technology, boolean sports) {
        this.politics = politics;
        this.technology = technology;
        this.sports = sports;
    }

    public String getNews_desk() {
        if(!politics && !technology && !sports) {
            return "";
        }

        String news_desk_values = "(";

        if(politics){
            news_desk_values += "\"Politics\"";
        }
        if(sports) {
            news_desk_values += "\"Sports\"";
        }
        if(technology){
            news_desk_values += "\"Technology\"";
        }

        news_desk_values += ")";

        return news_desk_values;

    }

    public String getBeginDateString() {

        String beginDateString = String.valueOf(year);

        if(month < 10) {
            beginDateString += "0" + month;
        }
        else {
            beginDateString += String.valueOf(month);
        }
        if( day < 10) {
            beginDateString += "0" + day;
        }
        else {
            beginDateString += String.valueOf(day);
        }

        return beginDateString;
    }

    public String getSortOrder(){
        if(oldest){
            return "oldest";
        }
        else{
            return "newest";
        }
    }



}
