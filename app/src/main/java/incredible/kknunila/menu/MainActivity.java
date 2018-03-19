package incredible.kknunila.menu;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import incredible.kknunila.R;
import incredible.kknunila.SessionManager;
import incredible.kknunila.fragment.MenuMahasiswa;

public class MainActivity extends AppCompatActivity {

    Button btnRegist, btnLogin, btnPetunjuk, btnAbout;
    //    TextView jadwalKKN;
    Button info;
    SessionManager sesi;

    String urlJadwal = "http://103.31.251.234/aplikasi_kkn/jadwal.php";
    String tgl_buka, tgl_buka2, tgl_skrng, tgl_tutup, tgl_tutup2, periode, tahun;

    long tglBuka, tglTutup, tglSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sesi = new SessionManager(this);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnPetunjuk = (Button) findViewById(R.id.btnPetunjuk);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        info = (Button) findViewById(R.id.infoKKN);

        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        tgl_skrng = sdf1.format(cl.getTime());
        try {
            Date sekarang = sdf1.parse(tgl_skrng);
            tglSkrng = sekarang.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJadwal, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jadwal = response.getJSONObject("jadwal");
                    periode = jadwal.getString("periode");
                    tahun = jadwal.getString("tahun");
                    tgl_buka = jadwal.getString("tgl_buka");
                    tgl_tutup = jadwal.getString("tgl_tutup");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                    try {
                        Date buka = dateFormat.parse(tgl_buka);
                        tgl_buka = format.format(buka);
                        tglBuka = buka.getTime();

                        Date tutup = dateFormat.parse(tgl_tutup);
                        tgl_tutup = format.format(tutup);
                        tglTutup = tutup.getTime();



                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);


        if (sesi.isLogin()) {
            Intent intent = new Intent(this, MenuMahasiswa.class);
            finish();
            startActivity(intent);
        }

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tglSkrng >= tglBuka && tglSkrng<= tglTutup) {

                    Intent reg = new Intent(MainActivity.this, Regist.class);
                    startActivity(reg);

                } else {
                    if (tglSkrng < tglBuka) {
//                        AlertDialog blmbuka = new AlertDialog.Builder(MainActivity.this).create();
//                        blmbuka.setTitle("Pendaftaran belum dibuka");
//                        blmbuka.setMessage("Maaf pendaftaran belum dibuka saat ini");
//                        blmbuka.setIcon(R.drawable.info_green);

                        Toast.makeText(MainActivity.this, "Maaf Pendaftaran Belum Dibuka", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Maaf Pendaftaran Telah Ditutup", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);

            }
        });

        btnPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent petunjuk = new Intent(MainActivity.this, Petunjuk.class);
                startActivity(petunjuk);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(MainActivity.this, About.class);
                startActivity(about);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                displayPopUp(v);
            }
        });

    }

    private void displayPopUp(View anchorView) {
        PopupWindow popupInfo = new PopupWindow(MainActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popup, null);
        popupInfo.setContentView(layout);

        TextView jadwaldaftarKKN = (TextView) layout.findViewById(R.id.jadwalPendaftaran);
        TextView jadwalMulaiKKN = (TextView) layout.findViewById(R.id.jadwalKKN);
        TextView webkkn = (TextView) layout.findViewById(R.id.link_kkn);

        jadwaldaftarKKN.setText("Jadwal Pendaftaran KKN Periode " + periode + " Tahun " + tahun +
                " akan dibuka pada tanggal " + tgl_buka + " sampai dengan " + tgl_tutup);

        jadwalMulaiKKN.setText("Kegiatan KKN dimulai pada tanggal 22 Januari 2018 sampai 03 Maret 2018");

        webkkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_link = new Intent();
                open_link.setAction(Intent.ACTION_VIEW);
                open_link.addCategory(Intent.CATEGORY_BROWSABLE);
                open_link.setData(Uri.parse("http://kkn.unila.ac.id"));
                startActivity(open_link);
            }
        });
        popupInfo.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupInfo.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        popupInfo.setOutsideTouchable(true);
        popupInfo.setFocusable(true);

        popupInfo.setBackgroundDrawable(new BitmapDrawable());
        popupInfo.showAsDropDown(anchorView, -300, -500);
    }
}
