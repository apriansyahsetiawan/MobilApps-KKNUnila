package incredible.kknunila.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import incredible.kknunila.R;

public class Laporan extends AppCompatActivity {

    String urlLaporan = "http://103.31.251.234/aplikasi_kkn/detail_laporan.php?id_laporan=";
    ImageView ImgLaporan, ImgLaporan2, ImgLaporan3;
    TextView judul, waktu, pj, lokasi, sasaran, deskripsi;
    String Judul, Waktu, Pj, Lokasi, Sasaran, Deskripsi, Foto, Foto2, Foto3;
    private Context context;
    String imgPath = "http://103.31.251.234/aplikasi_kkn/uploads/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        ImgLaporan = (ImageView) findViewById(R.id.imglaporanKegiatan);
        ImgLaporan2 = (ImageView) findViewById(R.id.imglaporanKegiatan2);
        ImgLaporan3 = (ImageView) findViewById(R.id.imglaporanKegiatan3);
        judul = (TextView) findViewById(R.id.laporan_kegiatan);
        waktu = (TextView) findViewById(R.id.waktu_kegiatan);
        pj = (TextView) findViewById(R.id.penanggung_jawab);
        lokasi = (TextView) findViewById(R.id.lokasi_kegiatan);
        sasaran = (TextView) findViewById(R.id.sasaran_kegiatan);
        deskripsi = (TextView) findViewById(R.id.deskripsi_kegiatan);

        Intent intent = getIntent();
        String id_laporan = intent.getStringExtra("id_laporan");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlLaporan + id_laporan, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Log.d("test", response.toString());
                    JSONObject laporan = response.getJSONObject("laporan");

                    Judul = laporan.getString("nama_kegiatan");
                    Waktu = laporan.getString("tanggal_kegiatan");
                    Pj = laporan.getString("penanggung_jawab");
                    Lokasi = laporan.getString("lokasi_kegiatan");
                    Sasaran = laporan.getString("sasaran_kegiatan");
                    Deskripsi = laporan.getString("deskripsi_kegiatan");
                    Foto = laporan.getString("foto_kegiatan");
                    Foto2 = laporan.getString("foto_kegiatan2");
                    Foto3 = laporan.getString("foto_kegiatan3");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                    try {
                        Date format_waktu = dateFormat.parse(Waktu);
                        Waktu = format.format(format_waktu);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    judul.setText(Judul);
                    waktu.setText(Waktu);
                    pj.setText(Pj);
                    lokasi.setText(Lokasi);
                    sasaran.setText(Sasaran);
                    deskripsi.setText(Deskripsi);

                    Picasso.with(context).load(imgPath + Foto).into(ImgLaporan);
                    Picasso.with(context).load(imgPath + Foto2).into(ImgLaporan2);
                    Picasso.with(context).load(imgPath + Foto3).into(ImgLaporan3);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }
}
