package com.application.charles.rssfeed.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.charles.rssfeed.R;
import com.application.charles.rssfeed.models.Entry;
import com.application.charles.rssfeed.views.AudioPlayAcitivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.RssFeedAdapterViewHolder> {

    private List<Entry> mEntries;
    private Context mContext;

    public RssFeedAdapter(List<Entry> entries, Context context) {
        mEntries = entries;
        mContext = context;
    }

    @Override
    public RssFeedAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_list_item_layout, parent, false);
        return new RssFeedAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RssFeedAdapterViewHolder holder, int position) {
        final Entry entry = mEntries.get(position);
        SpannableString albumTitle = new SpannableString("Album: ");
        albumTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, albumTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString songTitle = new SpannableString("Title: ");
        songTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, songTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString artistTitle = new SpannableString("Artist: ");
        artistTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, artistTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString priceTitle = new SpannableString("Price: ");
        priceTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, priceTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(albumTitle).append(entry.getmTitle()).append("\n");
        sb.append(songTitle).append(entry.getmName()).append("\n");
        sb.append(artistTitle).append(entry.getmArtist()).append("\n");
        sb.append(priceTitle).append(entry.getmPrice());
        holder.mTitle.setText(sb);
        Picasso.with(mContext).load(Uri.parse(entry.getmImage())).into(holder.mImage);
        if (entry.isClicked()) {
            holder.mTitle.setMaxLines(Integer.MAX_VALUE);
            holder.mTryItButton.setVisibility(View.VISIBLE);
        } else {
            holder.mTitle.setMaxLines(3);
            holder.mTryItButton.setVisibility(View.GONE);
        }
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    holder.mTitle.setMaxLines(Integer.MAX_VALUE);
                    holder.mTryItButton.setVisibility(View.VISIBLE);
                } else {
                    holder.mTitle.setMaxLines(3);
                    holder.mTryItButton.setVisibility(View.GONE);
                }
                isClicked = !isClicked;
                entry.setClicked(isClicked);
            }
        });
        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mContext, Uri.parse(entry.getmLink()));
            }
        });
        holder.mTryItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AudioPlayAcitivity.class);
                intent.putExtra("url", entry.getmPreviewLink());
                intent.putExtra("title", entry.getmTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public static class RssFeedAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mTitle;
        private TextView mTryItButton;

        private RssFeedAdapterViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.rss_list_item_image);
            mTitle = (TextView) itemView.findViewById(R.id.rss_list_item_title);
            mTryItButton = (TextView) itemView.findViewById(R.id.rss_list_item_try_button);
        }
    }
}
