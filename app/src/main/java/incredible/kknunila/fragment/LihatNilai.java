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
import incredible.kknunila.SessionManager;

public class LihatNilai extends Fragment {

    String urlNilai = "http://103.31.251.234/aplikasi_kkn/nilai.php?npm=";
    String nilai_dpl, nilai_kdpl, persentase_dpl, persentase_kdpl;
    String npm;

    TextView nilai, nilaidpl, nilaikdpl, persentasedpl, persentasekdpl, total;
    SessionManager sesi;

    double nilaiDpl;
    double nilaiKdpl;
    double persendpl;
    double persenkdpl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lihat_nilai, container, false);
        getActivity().setTitle("Lihat Nilai");

        nilai = (TextView) view.findViewById(R.id.nilai);
        nilaidpl = (TextView) view.findViewById(R.id.nilaidpl);
        nilaikdpl = (TextView) view.findViewById(R.id.nilaikdpl);
        persentasedpl = (TextView) view.findViewById(R.id.persendpl);
        persentasekdpl = (TextView) view.findViewById(R.id.persenkdpl);
        total = (TextView) view.findViewById(R.id.total);

        sesi = new SessionManager(getActivity().getBaseContext());
        npm = sesi.getNPM();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlNilai + npm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject n = response.getJSONObject("0");
                    nilai_dpl = n.getString("nilai");
                    nilai_kdpl = n.getString("nilai_kdpl");
                    persentase_dpl = n.getString("persentase_dpl");
                    persentase_kdpl = n.getString("persentase_kdpl");

                    nilaidpl.setText(nilai_dpl);
                    nilaikdpl.setText(nilai_kdpl);
                    persentasedpl.setText(persentase_dpl);
                    persentasekdpl.setText(persentase_kdpl);

                    nilaiDpl = Double.parseDouble(nilai_dpl);
                    nilaiKdpl = Double.parseDouble(nilai_kdpl);
                    persendpl = Double.parseDouble(persentase_dpl);
                    persenkdpl = Double.parseDouble(persentase_kdpl);

                    double dpl = (persendpl / 100) * nilaiDpl;
                    double kdpl = (persenkdpl / 100) * nilaiKdpl;
                    double nilai_total = dpl + kdpl;

                    total.setText(String.valueOf(nilai_total));

                    if (nilai_total > 80) {
                        nilai.setText("A");
                    } else if (nilai_total > 76 && nilai_total < 80) {
                        nilai.setText("B+");
                    } else if (nilai_total > 71 && nilai_total < 76) {
                        nilai.setText("B");
                    } else if (nilai_total > 66 && nilai_total < 70) {
                        nilai.setText("C+");
                    } else if (nilai_total > 61 && nilai_total < 66) {
                        nilai.setText("C");
                    } else if (nilai_total > 56 && nilai_total < 61) {
                        nilai.setText("D");
                    } else if (nilai_total <= 56) {
                        nilai.setText("E");
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

        Volley.newRequestQueue(getActivity().getBaseContext()).add(jsonObjectRequest);

        return view;
    }

}
