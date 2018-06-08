package airhawk.com.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;

import java.util.logging.Logger;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by Julian Dinkins on 5/10/2018.
 */

public class Test_Method {

    public static void main(String[] args) {

// GENERAL EXCHANGE MARKET SUMMARY DJ/NYSE/NASDAQ/CRYPTO
        ProcessXml(GoogleRSFeed());
    }
    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st =null;
            String sd =null;
            String sp =null;
            String sl =null;
            String urls =null;
            ArrayList<News_FeedItem> feedItems=new ArrayList<>();
            org.w3c.dom.Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                News_FeedItem it = new News_FeedItem();
                Node curentchild = items.item(i);
                if (curentchild.getNodeName().equalsIgnoreCase("item")) {
                    NodeList itemchilds = curentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node curent = itemchilds.item(j);
                        if (curent.getNodeName().equalsIgnoreCase("title")) {
                            st = Html.fromHtml(curent.getTextContent()).toString();
                            it.setTitle(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("description")) {
                            sd = Html.fromHtml(curent.getTextContent()).toString();
                            String d = curent.getTextContent().toString();
                            List<String> extractedUrls = extractUrls(d);
                            System.out.println(extractedUrls);
                            urls =extractedUrls.get(0);
                            System.out.println("THIS IS WHERE IMAGE IS "+d);
                            sd = sd.replaceAll("@20", " ");
                            it.setDescription(sd);
                            it.setThumbnailUrl(urls);
                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = Html.fromHtml(curent.getTextContent()).toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = Html.fromHtml(curent.getTextContent()).toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                        }
                        // else if (curent.getNodeName().equalsIgnoreCase("img src")) {
                        //si = Html.fromHtml(curent.getTextContent()).toString();
                        //si = si.replaceAll("@20", " ");
                        //it.setThumbnailUrl(si);
                        // }
                    }
                    Log.d("itemTitle", st);
                    Log.d("itemDescription", sd);
                    Log.d("itemPubDate", sp);
                    Log.d("itemLink", sl);
                    Log.d("itemURL", urls);
                    feedItems.add(it);
                    Log.d("itemTitle", it.getTitle());
                    Log.d("itemDescription", it.getDescription());
                    Log.d("itemPubDate", it.getPubDate());
                    Log.d("itemLink", it.getLink());
                    //if (it.getThumbnailUrl()!=null){Log.d("itemURL", it.getThumbnailUrl());}else{
                    //    System.out.println("No Image");
                    // }

                }
            }
        }
    }

    public static List<String> extractUrls(String text)
    {// Credit to BullyWiiPlaza from stackoverflow.com
        List<String> containedUrls = new ArrayList<String>();
        String imgRegex = "<[iI][mM][gG][^>]+[sS][rR][cC]\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern pattern = Pattern.compile(imgRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            String address = "https://news.google.com/news/rss/search/section/q/Stocks%20Crypto?ned=us&gl=US&hl=en";



            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    }

