package project.codepath.nytimessearch.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import project.codepath.nytimessearch.R;
import project.codepath.nytimessearch.models.Article;

/**
 * Created by jnagaraj on 2/11/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article>{

   // @Bind(R.id.ivImage)ImageView ivImage;
   // @Bind(R.id.tvTitle)TextView tvTitle;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = this.getItem(position);
       // ButterKnife.bind(convertView);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);


        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ivImage.setImageResource(0);

        tvTitle.setText(article.getHeadline());

        String thumbnail = article.getThumbNail();
        if(!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(ivImage);
        }

        return convertView;

    }
}
