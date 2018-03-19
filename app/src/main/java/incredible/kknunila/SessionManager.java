package incredible.kknunila;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by imastudio on 2/9/16.
 */
public class SessionManager {
    private static final String KEY_TOKEN = "tokenLogin";
    private static final String KEY_LOGIN = "isLogin";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE =0;    Context c;

    //0 agar cuma bsa dibaca hp itu sendiri
    String PREF_NAME="MahasiswaPref";

    //konstruktor
    public SessionManager(Context c){
        this.c = c;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //membuat session login
    public void cerateLoginSession(String token){
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
        //commit digunakan untuk menyimpan perubahan
    }
    //mendapatkan token
    public String getToken(){
        return pref.getString(KEY_TOKEN, "");
    }
    //cek login
    public boolean isLogin(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
    //logout user
    public void logout(){
        editor.clear();
        editor.commit();
    }

    public void setDesa(String nama_desa){
        editor.putString("nama_desa", nama_desa);
        editor.commit();
    }
    public String getDesa(){
        return pref.getString("nama_desa", "");
    }

    public void setNPM(String npm){
        editor.putString("npm", npm);
        editor.commit();
    }
    public String getNPM(){
        return pref.getString("npm", "");
    }

    public void setLatitude(String latitude){
        editor.putString("latitude", latitude);
        editor.commit();
    }
    public String getLatitude(){
        return pref.getString("latitude", "");
    }

    public void setLongitude(String longitude){
        editor.putString("longitude", longitude);
        editor.commit();
    }
    public String getLongitude(){
        return pref.getString("longitude", "");
    }

    public void setId_laporan(String id_laporan) {
        editor.putString("id_laporan", id_laporan);
        editor.commit();
    }
    public String getId_laporan() {
        return pref.getString("id_laporan", "");
    }

}
