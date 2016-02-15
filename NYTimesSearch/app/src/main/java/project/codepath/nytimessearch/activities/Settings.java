package project.codepath.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import project.codepath.nytimessearch.R;
import project.codepath.nytimessearch.models.QueryFilters;

public class Settings extends AppCompatActivity {

    private QueryFilters filters;

    DatePickerDialog datePickerDialog;

    @Bind(R.id.tvDebug) TextView tvDebug;
    @Bind(R.id.tvBeginDate) TextView tvBeginDate;

    @Bind(R.id.tvBeginDateVal)TextView tvBeginDateVal;

    @Bind(R.id.spinnerSortOrder) Spinner spinner;
    @Bind(R.id.cb_option_politics)CheckBox cb_option_politics;
    @Bind(R.id.cb_option_Sports)CheckBox cb_option_Sports;
    @Bind(R.id.cb_option_tech)CheckBox cb_option_tech;



    private int month;
    private int day;
    private int year;
    private String sortOrder;
    private String news_desk_values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        filters = (QueryFilters) Parcels.unwrap(intent.getParcelableExtra("query_filters"));

        month = filters.month;
        day = filters.day;
        year = filters.year;
        sortOrder = filters.sortOrder;
        tvBeginDateVal.setText(filters.month + "/" + filters.day + "/" + filters.year);


        fillOutSpinner();

        datePickerDialog = new DatePickerDialog(
                this, R.style.MyDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int _month = datePicker.getMonth()+1;
                int _day = datePicker.getDayOfMonth();
                int _year = datePicker.getYear();


                month = _month;
                day = _day;
                year = _year;

                tvBeginDateVal.setText(month + "/" + day + "/" + year);


            }
        }, filters.year , filters.month+1, filters.day);
    }

    public void onBeginDate(View view) {
        datePickerDialog.show();
    }

    private void fillOutSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void onSaveFilters(View view) {

        if(spinner.getSelectedItem().toString().equalsIgnoreCase("newest")) {
            sortOrder = QueryFilters.NEWEST;
        }
        else {
            sortOrder = QueryFilters.OLDEST;
        }


        news_desk_values = "";

        if(cb_option_politics.isChecked()){
            news_desk_values += "\"Politics\"";
        }
        if(cb_option_Sports.isChecked()) {
            news_desk_values += " \"Sports\"";
        }
        if(cb_option_tech.isChecked()){
            news_desk_values += " \"Technology\"";
        }



        //filters = new QueryFilters(month, day, year, sortOrder, news_desk_params);
        filters = new QueryFilters(month, day, year, sortOrder, news_desk_values);

        /*String news_desk_params = "";
        if(!news_desk_values.equals(""))
        {
            news_desk_params = "&news_desk:" + news_desk_values;
        }
        String sort_order_params = "&sort:"+ filters.sortOrder;
        String date_params = "&begin_date:"+ filters.beginDateString;*/


        //tvDebug.setText(date_params +  news_desk_params + sort_order_params);

        Intent intent = new Intent();
        intent.putExtra("query_filters", Parcels.wrap(filters));
        setResult(2, intent);
        finish();

    }

}
