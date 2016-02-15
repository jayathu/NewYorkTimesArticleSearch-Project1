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

    @Bind(R.id.tvBeginDateVal)TextView tvBeginDateVal;

    @Bind(R.id.spinnerSortOrder) Spinner spinner;
    @Bind(R.id.cb_option_politics)CheckBox cb_option_politics;
    @Bind(R.id.cb_option_Sports)CheckBox cb_option_Sports;
    @Bind(R.id.cb_option_tech)CheckBox cb_option_tech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        filters = (QueryFilters) Parcels.unwrap(intent.getParcelableExtra("query_filters"));


        tvBeginDateVal.setText(filters.month + "/" + filters.day + "/" + filters.year);

        fillOutSpinner();

        setFilterSettings(filters);

        datePickerDialog = new DatePickerDialog(
                this, R.style.MyDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int _month = datePicker.getMonth()+1;
                int _day = datePicker.getDayOfMonth();
                int _year = datePicker.getYear();

                tvBeginDateVal.setText(_month + "/" + _day + "/" + _year);

                filters.setBeginDate(_month, _day, _year);


            }
        }, filters.year , filters.month+1, filters.day);
    }

    public void setFilterSettings(QueryFilters filters)
    {
        tvBeginDateVal.setText(filters.month + "/" + filters.day + "/" + filters.year);

        if(filters.oldest) {
            spinner.setSelection(1);
        }
        else {
            spinner.setSelection(0);
        }

        cb_option_tech.setChecked(filters.technology);
        cb_option_Sports.setChecked(filters.sports);
        cb_option_politics.setChecked(filters.politics);

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
            filters.setSortOrder(false);
        }
        else {
            filters.setSortOrder(true);
        }

        filters.setNews_desk(cb_option_politics.isChecked(),
                            cb_option_tech.isChecked(),
                            cb_option_Sports.isChecked()
                        );

        Intent intent = new Intent();
        intent.putExtra("query_filters", Parcels.wrap(filters));
        setResult(2, intent);
        finish();

    }

}
