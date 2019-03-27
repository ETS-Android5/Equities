package equities.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Activity_Web_View extends AppCompatActivity {

  private String webviewurl;
  private WebView wv_url;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);

    //here we will get the url passed by previous activity
    webviewurl=getIntent().getStringExtra("url");

    wv_url=findViewById(R.id.wv_url);

    wv_url.setWebViewClient(new WebViewClient());
    wv_url.getSettings().setJavaScriptEnabled(true);
      wv_url.getSettings().setDomStorageEnabled(true);

      wv_url.loadUrl(webviewurl);

  }
}