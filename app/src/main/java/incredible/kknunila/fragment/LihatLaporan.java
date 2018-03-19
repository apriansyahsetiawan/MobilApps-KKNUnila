package incredible.kknunila.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import incredible.kknunila.ListLaporan;
import incredible.kknunila.ListLaporanAdapter;
import incredible.kknunila.R;
import incredible.kknunila.SessionManager;


public class LihatLaporan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<ListLaporan> listData = new ArrayList<>();
    ListLaporanAdapter laporanAdapter;
    SwipeRefreshLayout swipe;
    RecyclerView recyclerView;
    String urlList = "http://103.31.251.234/aplikasi_kkn/list_laporan.php?npm=";
    String id_laporan, foto, judul, waktu;
    FloatingActionButton fab;
    SessionManager sesi;
    String npm;

    public LihatLaporan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lihat_laporan, container, false);
        getActivity().setTitle("Laporan Kegiatan");

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerLaporan);
        laporanAdapter = new ListLaporanAdapter(listData);
        recyclerView.setAdapter(laporanAdapter);

        sesi = new SessionManager(getActivity().getBaseContext());
        npm = sesi.getNPM();

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.header, R.color.colorPrimaryDark);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                listData.clear();
                swipeLaporan();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getBaseContext(), buatLaporan.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        swipeLaporan();
        listData.clear();
    }

    private void swipeLaporan() {
        swipe.setRefreshing(true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlList + npm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("laporan");
                    listData.clear();
                    Log.e("ERROR JSON", response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        id_laporan = object.getString("id_laporan");
                        foto = object.getString( "foto_kegiatan");
                        judul = object.getString("nama_kegiatan");
                        waktu = object.getString("tanggal_kegiatan");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                        try {
                            Date format_waktu = dateFormat.parse(waktu);
                            waktu = format.format(format_waktu);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        ListLaporan dataList = new ListLaporan(id_laporan, foto, judul, waktu);
                        listData.add(dataList);
                    }
                    laporanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });

        Volley.newRequestQueue(getActivity().getBaseContext()).add(jsonObjectRequest);
    }

}