package project.codepath.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import project.codepath.nytimessearch.R;
import project.codepath.nytimessearch.models.Article;
import project.codepath.nytimessearch.models.ArticleWithImage;

/**
 * Created by jnagaraj on 2/13/16.
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<Article> mArticles;
    private final int WITH_THUMBNAIL = 0, WITHOUT_THUMBNAIL = 1;

    public ArticleRecyclerAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case WITH_THUMBNAIL:
                View v1 = inflater.inflate(R.layout.item_article_result, parent, false);
                viewHolder = new HolderForArticlesWithThumbnails(v1);
                break;
            case WITHOUT_THUMBNAIL:
                View v2 = inflater.inflate(R.layout.item_article_no_thumbnail, parent, false);
                viewHolder = new HolderForArticlesWithoutThumbnails(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new HolderForArticlesWithoutThumbnails(v);
                break;
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case WITH_THUMBNAIL:
                HolderForArticlesWithThumbnails viewHolder1 = (HolderForArticlesWithThumbnails) viewHolder;
                configureViewHolderWithThumbnails(viewHolder1, position, context);
                break;
            case WITHOUT_THUMBNAIL:
                HolderForArticlesWithoutThumbnails viewHolder2 = (HolderForArticlesWithoutThumbnails) viewHolder;
                configureViewHolderWithoutThumbnails(viewHolder2, position);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mArticles.get(position) instanceof ArticleWithImage) {
            return WITH_THUMBNAIL;
        } else if (mArticles.get(position) instanceof Article) {
            return WITHOUT_THUMBNAIL;
        }
        return -1;
    }

    public static class HolderForArticlesWithThumbnails extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvSnippet;
        public ImageView ivThumbnail;

        public HolderForArticlesWithThumbnails(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivImage);


        }
    }

    public static class HolderForArticlesWithoutThumbnails extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvSnippet;

        public HolderForArticlesWithoutThumbnails(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleNoThumbnail);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippetNoThumbnail);


        }
    }

    private void configureViewHolderWithThumbnails(HolderForArticlesWithThumbnails vh1, int position, Context context) {

        ArticleWithImage article = (ArticleWithImage)mArticles.get(position);
        vh1.tvTitle.setText(article.getHeadline());
        vh1.tvSnippet.setText(article.getSnippet());
        String thumbnailUrl = article.getThumbNail();
        Glide.with(context).load(thumbnailUrl).into(vh1.ivThumbnail);
    }

    private void configureViewHolderWithoutThumbnails(HolderForArticlesWithoutThumbnails vh2, int position) {

        Article article = mArticles.get(position);
        vh2.tvTitle.setText(article.getHeadline());
        vh2.tvSnippet.setText(article.getSnippet());

    }
}
