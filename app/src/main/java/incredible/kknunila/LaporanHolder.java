package incredible.kknunila;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12/2/2017.
 */

public class LaporanHolder extends RecyclerView.ViewHolder {
    ImageView imgLaporan, deleteLaporan;
    TextView judul, waktu;
    private Context context;
    String id_laporan;
    String url_delete= "http://103.31.251.234/aplikasi_kkn/delete_laporan.php?id_laporan=";
    int success;

    public LaporanHolder(View itemView, final Context context) {
        super(itemView);
        this.context = context;

        imgLaporan = (ImageView) itemView.findViewById(R.id.imgLaporan);
        judul = (TextView) itemView.findViewById(R.id.judulKegiatan);
        waktu = (TextView) itemView.findViewById(R.id.waktu);
        deleteLaporan = (ImageView) itemView.findViewById(R.id.delete_laporan);

        deleteLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogHapus = new AlertDialog.Builder(context);
                dialogHapus.setTitle("Hapus Laporan");
                dialogHapus.setMessage("Anda yakin akan menghapus laporan?");
                dialogHapus.setIcon(R.drawable.ic_delete_black_24dp);
                dialogHapus.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Berhasil Dihapus", Toast.LENGTH_LONG).show();
                    }
                });
                dialogHapus.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogHapus.show();
                delete_laporan();
            }
        });

    }

    private void delete_laporan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete + id_laporan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt("Success");
//                    laporanAdapter.dataList.remove(id_laporan);
                    if (success == 1){
//                        laporanAdapter.notifyDataSetChanged();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("ERROR", error.getMessage().toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_laporan", id_laporan);

                return params;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);

    }

    public void updateUI(ListLaporan data) {
//        this.position
        id_laporan = data.getId_laporan();
        Picasso.with(context).load(data.getFoto_kegiatan()).into(imgLaporan);
//        imgLaporan.setImageResource(data.getFoto_kegiatan());
        judul.setText(data.getNama_kegiatan());
        waktu.setText(data.getWaktu_kegiatan());
    }
}
