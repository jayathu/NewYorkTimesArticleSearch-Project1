package project.codepath.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.codepath.nytimessearch.R;
import project.codepath.nytimessearch.models.Article;

/**
 * Created by jnagaraj on 2/13/16.
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {

    private List<Article> mArticles;
    Context context;

    public ArticleRecyclerAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public ArticleRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.tvTitle;
        textView.setText(article.getHeadline());

        ImageView thumbnail = viewHolder.ivThumbnail;

        String thumbnailUrl = article.getThumbNail();
        if(!TextUtils.isEmpty(thumbnailUrl)) {
            Picasso.with(context).load(thumbnailUrl).into(thumbnail);
        }

        //Picasso.with(context).load(Uri.parse(article.getWebUrl())).into(thumbnail);

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView ivThumbnail;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivImage);


        }
    }
}
