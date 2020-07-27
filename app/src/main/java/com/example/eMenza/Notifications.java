package com.example.eMenza;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    LinearLayout.LayoutParams show = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams hide = new LinearLayout.LayoutParams(0, 0);


    private String[] postsString = new String[]{"post-3539", "post-3534", "post-3529", "post-3504", "post-3493"};

    private ArrayList<Post> postList = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getData();

    }

    private void showParagraph() {

        for(int i = 0; i < postList.size(); i++) {

        }
        // visible
        // text
        // animacija

        // za scrollview za koji je button vezan
    }

    private void setListeners() {
        Button btn1 = (Button) findViewById(R.id.button2);
        Button btn2 = (Button) findViewById(R.id.button4);
        Button btn3 = (Button) findViewById(R.id.button5);
        Button btn4 = (Button) findViewById(R.id.button6);
        Button btn5 = (Button) findViewById(R.id.button7);

        TextView textView1 = (TextView) findViewById(R.id.textView20);
        TextView textView2 = (TextView) findViewById(R.id.textView21);
        TextView textView3 = (TextView) findViewById(R.id.textView22);
        TextView textView4 = (TextView) findViewById(R.id.textView23);
        TextView textView5 = (TextView) findViewById(R.id.textView24);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="http://www.scns.rs/";//your website url
                    Document doc = Jsoup.connect(url).get();

                    TextView textView1 = (TextView) findViewById(R.id.textView20);
                    TextView textView2 = (TextView) findViewById(R.id.textView21);
                    TextView textView3 = (TextView) findViewById(R.id.textView22);
                    TextView textView4 = (TextView) findViewById(R.id.textView23);
                    TextView textView5 = (TextView) findViewById(R.id.textView24);

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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            /*for(int i = 0; i < postList.size(); i++) {
                                Button button = new Button(getApplicationContext());
                                final ScrollView scrollView = new ScrollView(getApplicationContext());
                                final TextView textView = new TextView(getApplicationContext());

                                button.setId(i);
                                button.setText(postList.get(i).getNotification().text());



                                textView.setText(postList.get(i).getParagraph().text());

                                scrollView.setId(i);
                                scrollView.addView(textView);

                                LinearLayout layout = findViewById(R.id.linearLayout);


                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        scrollView.setLayoutParams(show);

                                    }
                                });

                                layout.addView(button, show);
                                layout.addView(scrollView, hide);
                            }*/
                    }
                });
            }
        }).start();
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


}
