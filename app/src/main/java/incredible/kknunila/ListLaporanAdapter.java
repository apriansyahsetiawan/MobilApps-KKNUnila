package incredible.kknunila;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import incredible.kknunila.menu.Laporan;


/**
 * Created by user on 11/23/2017.
 */

public class ListLaporanAdapter extends RecyclerView.Adapter<LaporanHolder> {

    public ArrayList<ListLaporan> dataList;
//    private static String urlMove = null;
//    private Context context;

    public ListLaporanAdapter(ArrayList<ListLaporan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public LaporanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_laporan, parent, false);
        return new LaporanHolder(card, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final LaporanHolder holder, final int position) {
        final ListLaporan data = dataList.get(position);
        holder.updateUI(data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Laporan.class);
                intent.putExtra("id_laporan", holder.id_laporan);
                v.getContext().startActivity(intent);
//                Toast.makeText(v.getContext(), "id laporan = " + holder.id_laporan, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
