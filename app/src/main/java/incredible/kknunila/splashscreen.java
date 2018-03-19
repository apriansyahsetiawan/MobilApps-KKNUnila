package incredible.kknunila;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import incredible.kknunila.menu.MainActivity;

/**
 * Created by user on 11/22/2017.
 */

public class splashscreen extends AppCompatActivity {
    TextView kknUnila;
    ImageView unila, load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        unila = (ImageView) findViewById(R.id.unila);
        kknUnila = (TextView) findViewById(R.id.kknUnila);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
