package incredible.kknunila.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import incredible.kknunila.R;
import incredible.kknunila.Server;
import incredible.kknunila.SessionManager;


public class InfoKelompok extends Fragment {

    TextView txtDesa, txtKec, txtKab;
    Button btnMap;

    SessionManager sesi;
    String npm;
    String latitude, longitude, nama_desa, nama_kec, nama_kab;

    String urlPenempatan = "http://103.31.251.234/aplikasi_kkn/info_kelompok.php?npm=";

    public InfoKelompok() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {
        View view = inflater.inflate(R.layout.fragment_info_kelompok, container, false);
        getActivity().setTitle("Info Penempatan");

        txtDesa = (TextView) view.findViewById(R.id.txtDesa);
        txtKab = (TextView) view.findViewById(R.id.txtKab);
        txtKec = (TextView) view.findViewById(R.id.txtKec);
        btnMap = (Button) view.findViewById(R.id.btnMap);

        sesi = new SessionManager(getActivity().getBaseContext());
        npm = sesi.getNPM();

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getBaseContext(), MapsActivity.class);
                startActivity(i);
            }
        });
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlPenempatan + npm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject nol = response.getJSONObject("0");

                    nama_desa = nol.getString(Server.KEY_DESA);
                    nama_kec = nol.getString(Server.KEY_KECAMATAN);
                    nama_kab = nol.getString(Server.KEY_KABUPATEN);
                    latitude = nol.getString("latitude");
                    longitude = nol.getString("longitude");

                    sesi.setLatitude(latitude);
                    sesi.setLongitude(longitude);
                    sesi.setDesa(nama_desa);

                    txtDesa.setText(nama_desa);
                    txtKec.setText(nama_kec);
                    txtKab.setText(nama_kab);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(getActivity().getBaseContext()).add(req);

        return view;
    }

}
