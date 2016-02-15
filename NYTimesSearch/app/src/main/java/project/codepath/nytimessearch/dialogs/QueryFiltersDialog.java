package project.codepath.nytimessearch.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.codepath.nytimessearch.R;

/**
 * Created by jnagaraj on 2/14/16.
 */
public class QueryFiltersDialog extends DialogFragment {

    private String beginDate;
    private String sortOder;
    private String news_desk;

    public QueryFiltersDialog() {

    }

    public  static  QueryFiltersDialog newInstance(String beginDate, String sortOder, String news_desk) {

        QueryFiltersDialog dialog = new QueryFiltersDialog();

        Bundle args = new Bundle();

        args.putString("begin_date", beginDate);
        args.putString("sort_order", sortOder);
        args.putString("news_dek", news_desk);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
