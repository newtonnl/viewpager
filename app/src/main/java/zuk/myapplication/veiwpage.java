package zuk.myapplication;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class veiwpage extends AppCompatActivity {
    final String Tag="viewpager";
    private ViewPager mPager;
//    private List<View> listViews;
    final ArrayList<View> list = new ArrayList<View>();
    private ImageView cursor;
    private TextView t1, t2, t3;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    private Context context = null;
    private LocalActivityManager manager = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setFlags(flag,flag);
    //    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        context = getApplicationContext();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



        InitViewPager();

        InitTextView();
        InitImageView();



    }

    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);

        Log.d(Tag,"inittextView");
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "veiwpage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://zuk.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "veiwpage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://zuk.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
 //       listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();

        Intent intentCQAtest = new Intent(context,zuk.myapplication.test_main.class);

        list.add(getView("R.layout.lay1",intentCQAtest));
        list.add(mInflater.inflate(R.layout.lay2,null));
        list.add(mInflater.inflate(R.layout.lay3,null));
        Log.d(Tag,"InitViewPager");

        mPager.setAdapter(new MyPagerAdapter(list));
        mPager.setCurrentItem(0);
 //       mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    private View getView(String lay1, Intent intentCQAtest) {
        return manager.startActivity(lay1,intentCQAtest).getDecorView();
    }


    public class MyPagerAdapter extends PagerAdapter{
        private List<View> mListViews;

        public MyPagerAdapter(List<View> ListViews) {
            this.mListViews = ListViews;
        }
        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2){
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int  getCount(){
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup arg0,int arg1){
            ((ViewPager) arg0).addView(mListViews.get(arg1),0);
            return mListViews.get(arg1);
        }

    };

    private void InitImageView(){
        cursor = (ImageView) findViewById(R.id.imageView);
        bmpW = BitmapFactory.decodeResource(getResources(),R.drawable.a).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW/3 - bmpW)/2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);

    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int one = offset*2+bmpW;
        int two = one * 2;
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0){
                case 0:
                    if (currIndex == 1){
                        animation = new TranslateAnimation(one, 0 , 0, 0);
                    }else if(currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0){
                        animation = new TranslateAnimation(offset,one,0,0);
                    }else if (currIndex == 2){
                        animation = new TranslateAnimation(two,one,0,0);
                    }
                    break;
                case 2:
                    if (currIndex == 0){
                        animation = new TranslateAnimation(offset,two,0, 0);
                    }else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor.startAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


}
