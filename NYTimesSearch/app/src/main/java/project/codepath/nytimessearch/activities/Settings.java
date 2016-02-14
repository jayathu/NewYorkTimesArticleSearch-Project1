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

    @Bind(R.id.tvBeginDate) TextView tvBeginDate;

    @Bind(R.id.spinnerSortOrder) Spinner spinner;
    @Bind(R.id.cb_option_politics)CheckBox cb_option_politics;
    @Bind(R.id.cb_option_Sports)CheckBox cb_option_Sports;
    @Bind(R.id.cb_option_tech)CheckBox cb_option_tech;

    private String beginDate;
    private String sortOrder;
    private String news_desk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        filters = (QueryFilters) Parcels.unwrap(intent.getParcelableExtra("query_filters"));

        fillOutSpinner();
        datePickerDialog = new DatePickerDialog(
                this, R.style.MyDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();

                tvBeginDate.setText(month + "/" + day + "/" + year);

                beginDate = year + "" + month + "" + day;

            }
        }, 2016 , 1, 0);
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

        if(spinner.getSelectedItem().toString() == "Newest") {
            sortOrder = QueryFilters.NEWEST;
        }
        else {
            sortOrder = QueryFilters.OLDEST;
        }


        if(cb_option_politics.isChecked()){
            news_desk = "\"Politics\"";
        }
        if(cb_option_Sports.isChecked()) {
            news_desk += " \"Sports\"";
        }
        if(cb_option_tech.isChecked()){
            news_desk += " \"Technology\"";
        }

        filters = new QueryFilters(beginDate, sortOrder, news_desk);

        Intent intent = new Intent();
        intent.putExtra("query_filters", Parcels.wrap(filters));
        setResult(2, intent);
        finish();

    }

}
