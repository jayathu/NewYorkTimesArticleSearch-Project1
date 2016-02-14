package project.codepath.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import project.codepath.nytimessearch.R;

public class Settings extends AppCompatActivity {

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fillOutSpinner();
        datePickerDialog = new DatePickerDialog(
                this, R.style.MyDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                TextView textView = (TextView)findViewById(R.id.tvBeginDateVal);
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();

                textView.setText(month + "/" + day + "/" + year);

            }
        }, 2016 , 1, 0);
    }

    public void onBeginDate(View view) {
        Toast.makeText(this, "Set Date", Toast.LENGTH_SHORT).show();
        datePickerDialog.show();
    }

    private void fillOutSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSortOrder);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}
