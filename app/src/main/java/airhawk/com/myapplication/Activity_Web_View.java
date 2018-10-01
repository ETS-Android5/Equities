package airhawk.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Activity_Web_View extends AppCompatActivity {

  private String webviewurl;
  private WebView wv_url;
  private InterstitialAd mInterstitialAd;
  public void onBackPressed() {
    // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
    MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

    if (mInterstitialAd.isLoaded()) {
      mInterstitialAd.show();
    }
    mInterstitialAd.setAdListener(new AdListener() {
      @Override
      public void onAdClosed() {
        finish();
      }
    });
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    mInterstitialAd = new InterstitialAd(this);
    mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
    mInterstitialAd.loadAd(new AdRequest.Builder().build());

    //here we will get the url passed by previous activity
    webviewurl=getIntent().getStringExtra("url");

    wv_url=findViewById(R.id.wv_url);

    wv_url.setWebViewClient(new WebViewClient());
    wv_url.getSettings().setJavaScriptEnabled(true);
      wv_url.getSettings().setDomStorageEnabled(true);

      wv_url.loadUrl(webviewurl);

  }
}