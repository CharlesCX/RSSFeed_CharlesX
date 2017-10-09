package com.application.charles.rssfeed.presenters;

import android.app.Activity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.charles.rssfeed.models.Entry;
import com.application.charles.rssfeed.models.Feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class RssFeedPresenter {

    public static final String REQUEST_URL = "https://itunes.apple.com/us/rss/topsongs/limit=10/xml";

    private Activity mMainActivity;
    private onRssFeedLoadListener mLoadListener;
    private RequestQueue mRequestQueue;
    private XmlPullParserFactory mFactory;
    private XmlPullParser mXmlPullParser;

    public RssFeedPresenter(Activity mainActivity, onRssFeedLoadListener loadListener) {
        mMainActivity = mainActivity;
        mLoadListener = loadListener;
        mRequestQueue = Volley.newRequestQueue(mainActivity);
        try {
            mFactory = XmlPullParserFactory.newInstance();
            mXmlPullParser = mFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void getTopSongs() {
        StringRequest request = new StringRequest(
                GET,
                REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response", response);
                        mLoadListener.onLoadSuccess(parseXMLResponse(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", error.getMessage());
                        mLoadListener.onLoadError(error.getMessage());
                    }
                });
        mRequestQueue.add(request);
    }

    private Feed parseXMLResponse(String response) {
        Feed feed = new Feed();
        try {
            mXmlPullParser.setInput(new StringReader(response));
            int event = mXmlPullParser.getEventType();
            List<Entry> entries = new ArrayList<>();
            Entry entry = null;
            String value = "";
            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = mXmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        switch (tagName) {
                            case "link":
                                if ("audio/x-m4a".equals(mXmlPullParser.getAttributeValue(null, "type"))) {
                                    entry.setmPreviewLink(mXmlPullParser.getAttributeValue(null, "href"));
                                }
                                break;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        value = mXmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (mXmlPullParser.getDepth() == 2) {
                            switch (tagName) {
                                case "title":
                                    feed.setmTitle(value);
                                    break;
                                case "updated":
                                    feed.setmUpdated(value);
                                    break;
                                case "icon":
                                    feed.setmIcon(value);
                                case "entry":
                                    if (entry != null) {
                                        entries.add(entry);
                                    }
                                    entry = new Entry();
                                    break;
                            }
                        } else if (mXmlPullParser.getDepth() > 2) {
                            switch (tagName) {
                                case "id":
                                    entry.setmId(value);
                                    entry.setmLink(value);
                                    break;
                                case "title":
                                    entry.setmTitle(value);
                                    break;
                                case "im:name":
                                    entry.setmName(value);
                                    break;
                                case "im:artist":
                                    entry.setmArtist(value);
                                    break;
                                case "im:price":
                                    entry.setmPrice(value);
                                    break;
                                case "rights":
                                    entry.setmRight(value);
                                    break;
                                case "content":
                                    entry.setmContent(value);
                                    break;
                                case "im:image":
                                    entry.setmImage(value);
                                    break;
                            }
                        }
                        break;
                }
                event = mXmlPullParser.next();
            }
            feed.setmEntries(entries);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;
    }

    public interface onRssFeedLoadListener {
        void onLoadSuccess(Feed feed);
        void onLoadError(String error);
    }
}
