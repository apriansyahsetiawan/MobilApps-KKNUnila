package incredible.kknunila.menu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import incredible.kknunila.AdapterPetunjuk;
import incredible.kknunila.R;
import me.relex.circleindicator.CircleIndicator;

public class Petunjuk extends AppCompatActivity {

    private static ViewPager viewPager;
    private static int currentPage = 0;
    private static final Integer[] flipper={R.drawable.navigation_drawer, R.drawable.lokasi, R.drawable.laporan, R.drawable.kirimlaporan, R.drawable.kirimlaporan2, R.drawable.detaillaporan};
    private ArrayList<Integer> petunjukArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petunjuk);
        init();
    }

    private void init() {
        for (int i = 0; i<flipper.length; i++)
            petunjukArray.add(flipper[i]);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AdapterPetunjuk(Petunjuk.this, petunjukArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == flipper.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 2500, 2500);
    }
}
