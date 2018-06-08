package airhawk.com.myapplication;

import com.jjoe64.graphview.series.DataPoint;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Test_Nasdaq_Graph_Points {
    public static void main(String[] args) {

        Document nas_data = null;
        Document sp_data = null;
        Document dow_data = null;
        List<String> name = new ArrayList<>();
        List<String> number = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://charting.nasdaq.com/ext/charts.dll?2-1-14-0-0-512-03NA000000GSAT-&SF:1|5-BG=FFFFFF-BT=0-HT=395--XTBL-").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = doc.getElementsByClass("DrillDownData");
        ArrayList<String> elements=new ArrayList<String>();
        for (Element el : divs) {
            Elements tds = el.select("td");
            String result = tds.get(0).text();
            elements.add(result);
        }
//System.out.println(elements);
        for(int j=elements.size() - 1; j >= 0; j--){
            if (j % 2 == 0) { // Even
                number.add(elements.get(j));
            } else { // Odd
                name.add(elements.get(j));
            }
        }
        //System.out.println("NAME "+name+" "+"NUMBER "+number);
        for (int counter = 0; counter < number.size(); counter++) {
            System.out.println("VOLUME "+name.get(counter)+" NUMBER "+number.get(counter));
        }
        List<String> numbers = number;
        Collections.reverse(numbers);
        List<String> _7Days = numbers.subList(0, 7);
        List<String> _30days = numbers.subList(0, 30);
        List<String> _90days = numbers.subList(0, 90);
        List<String> _180days = numbers.subList(0, 180);

        System.out.println("7 DAY "+_7Days);
        System.out.println("30 DAYS "+_30days);
        System.out.println("90 DAYS "+_90days);
        System.out.println("180 DAYS"+_180days);

        DataPoint[] dataPoints = new DataPoint[number.size()]; // declare an array of DataPoint objects with the same size as your list
        DataPoint[] _7dataPoints = new DataPoint[_7Days.size()];
        //for (int counter = 0; counter < number.size(); counter++) {
            // add new DataPoint object to the array for each of your list entries
        //    dataPoints[counter] = new DataPoint(counter, Double.parseDouble(number.get(counter))); // not sure but I think the second argument should be of type double
        //    System.out.println("ALL "+dataPoints[counter]);
        //    _7dataPoints[counter] = new DataPoint(counter, Double.parseDouble(_7Days.get(counter)));
        //    System.out.println("7 DAYS "+_7dataPoints[counter]);
        //}
    }








}









