package incredible.kknunila.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import incredible.kknunila.R;


public class Home extends Fragment {

    public Home() {
    }

    View home;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {
        home = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Menu Mahasiswa");
        return home;
    }

}
