package incredible.kknunila.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class InfoDPL extends Fragment {

    TextView txtNamaDpl, txtNipDpl, txtJurusanDpl, txtFakultasDpl, txtHpDpl;
    TextView txtNamaKDpl, txtNipKDpl, txtJurusanKDpl, txtFakultasKDpl, txtHpKDpl;
    TextView txtNamaDpl2, txtNipDpl2, txtJurusanDpl2, txtFakultasDpl2, txtHpDpl2;
    SessionManager sesi;
    String npm;
    String urlDPL = "http://103.31.251.234/aplikasi_kkn/info_kelompok.php?npm=";
    String urlKDPL = "http://103.31.251.234/aplikasi_kkn/info_dpl.php?npm=";


    public InfoDPL() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {
        View view = inflater.inflate(R.layout.fragment_info_dpl, container, false);
        getActivity().setTitle("Info DPL");

        txtNamaDpl = (TextView) view.findViewById(R.id.txtNamaDPL1);
        txtNipDpl = (TextView) view.findViewById(R.id.txtNipDPL1);
        txtJurusanDpl = (TextView) view.findViewById(R.id.txtJurusanDPL1);
        txtFakultasDpl = (TextView) view.findViewById(R.id.txtFakultasDPL1);
        txtHpDpl = (TextView) view.findViewById(R.id.txtTlpDPL1);

        txtNamaDpl2 = (TextView) view.findViewById(R.id.txtNamaDPL2);
        txtNipDpl2 = (TextView) view.findViewById(R.id.txtNipDPL2);
        txtJurusanDpl2 = (TextView) view.findViewById(R.id.txtJurusanDPL2);
        txtFakultasDpl2 = (TextView) view.findViewById(R.id.txtFakultasDPL2);
        txtHpDpl2 = (TextView) view.findViewById(R.id.txtTlpDPL2);

        txtNamaKDpl = (TextView) view.findViewById(R.id.txtNamaKDPL1);
        txtNipKDpl = (TextView) view.findViewById(R.id.txtNipKDPL1);
        txtJurusanKDpl = (TextView) view.findViewById(R.id.txtJurusanKDPL1);
        txtFakultasKDpl = (TextView) view.findViewById(R.id.txtFakultasKDPL1);
        txtHpKDpl = (TextView) view.findViewById(R.id.txtTlpKDPL1);

        sesi = new SessionManager(getActivity().getBaseContext());
        npm = sesi.getNPM();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlDPL + npm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject nol = response.getJSONObject("0");
                    String nama_dpl = nol.getString("nama_dpl");
                    String nip_dpl = nol.getString("nip");
                    String jurusan_dpl = nol.getString("nama_jurusan");
                    String fakultas_dpl = nol.getString("nama_fakultas");
                    String hp_dpl = nol.getString("hp");

                    JSONObject satu = response.getJSONObject("1");
                    String nama_dpl2 = satu.getString("nama_dpl");
                    String nip_dpl2 = satu.getString("nip");
                    String jurusan_dpl2 = satu.getString("nama_jurusan");
                    String fakultas_dpl2 = satu.getString("nama_fakultas");
                    String hp_dpl2 = satu.getString("hp");

                    txtNamaDpl.setText(nama_dpl);
                    txtNipDpl.setText(nip_dpl);
                    txtJurusanDpl.setText(jurusan_dpl);
                    txtFakultasDpl.setText(fakultas_dpl);
                    txtHpDpl.setText(hp_dpl);

                    txtNamaDpl2.setText(nama_dpl2);
                    txtNipDpl2.setText(nip_dpl2);
                    txtJurusanDpl2.setText(jurusan_dpl2);
                    txtFakultasDpl2.setText(fakultas_dpl2);
                    txtHpDpl2.setText(hp_dpl2);

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

        JsonObjectRequest reqKdpl = new JsonObjectRequest(Request.Method.GET, urlKDPL + npm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject nol = response.getJSONObject("0");
                    String nama_kdpl = nol.getString(Server.KEY_NAMAKDPL);
                    String nip_kdpl = nol.getString(Server.KEY_NIPKDPL);
                    String jurusan_kdpl = nol.getString(Server.KEY_JURUSAN);
                    String fakultas_kdpl = nol.getString(Server.KEY_FAKULTAS);
                    String hp_kdpl = nol.getString(Server.KEY_HPKDPL);

                    txtNamaKDpl.setText(nama_kdpl);
                    txtNipKDpl.setText(nip_kdpl);
                    txtJurusanKDpl.setText(jurusan_kdpl);
                    txtFakultasKDpl.setText(fakultas_kdpl);
                    txtHpKDpl.setText(hp_kdpl);
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

        Volley.newRequestQueue(getActivity().getBaseContext()).add(reqKdpl);

        return view;
    }
}
