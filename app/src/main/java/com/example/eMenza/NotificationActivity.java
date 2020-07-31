package com.example.eMenza;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eMenza.classes.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout.LayoutParams show = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams hide = new LinearLayout.LayoutParams(0, 0);

    private TextView textParagraph1;
    private TextView textParagraph2;
    private TextView textParagraph3;
    private TextView textParagraph4;
    private TextView textParagraph5;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    private Document doc;

    private String[] postsString = new String[]{"post-3631", "post-126", "post-3626", "post-3023", "post-3272"};

    private ArrayList<Post> postList = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ProgressBar progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        new AsyncCaller().execute();
    }

    private void setListeners() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    private void initData() {

        // Get components
        btn1 = (Button) findViewById(R.id.buttonTitle1);
        btn2 = (Button) findViewById(R.id.buttonTitle2);
        btn3 = (Button) findViewById(R.id.buttonTitle3);
        btn4 = (Button) findViewById(R.id.buttonTitle4);
        btn5 = (Button) findViewById(R.id.buttonTitle5);

        textParagraph1 = (TextView) findViewById(R.id.textParagraph1);
        textParagraph2 = (TextView) findViewById(R.id.textParagraph2);
        textParagraph3 = (TextView) findViewById(R.id.textParagraph3);
        textParagraph4 = (TextView) findViewById(R.id.textParagraph4);
        textParagraph5 = (TextView) findViewById(R.id.textParagraph5);

        // Put data into components
        btn1.setText(postList.get(0).getNotification().text());
        btn2.setText(postList.get(1).getNotification().text());
        btn3.setText(postList.get(2).getNotification().text());
        btn4.setText(postList.get(3).getNotification().text());
        btn5.setText(postList.get(4).getNotification().text());

        textParagraph1.setText(postList.get(0).getParagraph().text());
        textParagraph2.setText(postList.get(1).getParagraph().text());
        textParagraph3.setText(postList.get(2).getParagraph().text());
        textParagraph4.setText(postList.get(3).getParagraph().text());
        textParagraph5.setText(postList.get(4).getParagraph().text());
        hideParagraphs();

    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(5000);
        v.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        hideParagraphs();
        switch(i) {
            case R.id.buttonTitle1: textParagraph1.setVisibility(View.VISIBLE); break;
            case R.id.buttonTitle2: textParagraph2.setVisibility(View.VISIBLE); break;
            case R.id.buttonTitle3: textParagraph3.setVisibility(View.VISIBLE); break;
            case R.id.buttonTitle4: textParagraph4.setVisibility(View.VISIBLE); break;
            case R.id.buttonTitle5: textParagraph5.setVisibility(View.VISIBLE); break;
            default: break;
        }

    }

    private void hideParagraphs() {
        // Hide their text
        textParagraph1.setVisibility(View.INVISIBLE);
        textParagraph2.setVisibility(View.INVISIBLE);
        textParagraph3.setVisibility(View.INVISIBLE);
        textParagraph4.setVisibility(View.INVISIBLE);
        textParagraph5.setVisibility(View.INVISIBLE);
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {

            try {
                String url="http://www.scns.rs/";//your website url
                doc = Jsoup.connect(url).get();

                for(int i = 0; i < postsString.length; i++) {
                    Element postId = doc.getElementById(postsString[i]);
                    Element notification = postId.select("a").first();
                    Element title = postId.select("p").first();
                    Element paragraph = postId.select("p").last();
                    Post post = new Post(postId, notification, title, paragraph);
                    postList.add(post);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // First I must insert data into postList, then I can access it
            initData();
            setListeners();
            ProgressBar progressBar = findViewById(R.id.progressBar4);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

}


