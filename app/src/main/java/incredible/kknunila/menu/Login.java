package incredible.kknunila.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import incredible.kknunila.R;
import incredible.kknunila.Server;
import incredible.kknunila.SessionManager;
import incredible.kknunila.fragment.MenuMahasiswa;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    EditText txtUsername, txtPassword;
    String npm;
    String token;
    private SessionManager sesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnMasuk);
        txtUsername = (EditText) findViewById(R.id.username);
        txtPassword = (EditText) findViewById(R.id.password);
        btnLogin.setOnClickListener(this);

        sesi = new SessionManager(this);
    }

    private void login() {
        npm = txtUsername.getText().toString().trim();
        token = txtPassword.getText().toString().trim();
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Sedang Login", "Mohon Tunggu...", false, false);
        StringRequest sr = new StringRequest(Request.Method.POST, Server.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        try {
                            JSONObject json = new JSONObject(response);
                            String sukses = json.getString("success");
                            String pesan = json.getString("message");
                            if (sukses.equals("true")) {
//                                finish();
                                openProfile();
                                sesi.cerateLoginSession("1");
                                sesi.setNPM(npm);
                                tampil(pesan);
                            } else {
                                tampil(pesan);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Server.KEY_NPM, npm);
                params.put(Server.KEY_TOKEN, token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }

    private void tampil(String pesan) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();

    }

    private void openProfile() {
        Intent i = new Intent(this, MenuMahasiswa.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finishAffinity();
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
//        finish();
        login();
    }
}
