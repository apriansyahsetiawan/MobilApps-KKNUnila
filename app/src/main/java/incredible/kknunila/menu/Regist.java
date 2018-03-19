package incredible.kknunila.menu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import incredible.kknunila.R;
import incredible.kknunila.Server;

public class Regist extends AppCompatActivity {

    Spinner spinFak, spinJur, ukuran, spinhub;
    Button btnPilih, btnDaftar;
    EditText input_npm;
    EditText input_nama;
    EditText input_email;
    EditText input_sks;
    EditText input_ipk;
    EditText input_tmpt_lahir;
    ImageView input_tgl_lahir;
    TextView tgl_lahir;
    EditText input_alamat;
    EditText input_hobi;
    EditText input_kk;
    EditText input_telpon;
    EditText inputNamaAyah, inputPkAyah, inputNamaIbu, inputPkIbu, alamatOrtu, namaHb, alamatHb, telpHb;
    ImageView imgFoto;
    RadioGroup rgGender;
    Bitmap bitmap, decoded;

    private ArrayList<String> listFakultas;
    private ArrayList<String> listJurusan;
    private JSONArray result;
    private JSONArray result2;

    public String jurusanUrl;
    String[] size = {"S", "M", "L", "XL", "XXL", "XXXL"};

    String[] hub = {"AYAH", "IBU", "KAKAK", "PAMAN", "BIBI", "LAINNYA"};
    String setIdJurusan;
    String tgllahir;
    String npm;
    private static final String TAG = Regist.class.getSimpleName();

    public static final String urlFakultas = "http://103.31.251.234/aplikasi_kkn/fakultas2.php";
    public static final String urlJurusan = "http://103.31.251.234/aplikasi_kkn/jurusan.php?id_fakultas=";
    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat dateFormatter;
    private static final String TAG_SUCCESS = "success";
    int success;

    int bitmap_size = 60;
    int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        input_npm = (EditText) findViewById(R.id.inputNPM);
        input_nama = (EditText) findViewById(R.id.inputNama);
        input_email = (EditText) findViewById(R.id.inputEmail);
        input_sks = (EditText) findViewById(R.id.inputTotalSKS);
        input_ipk = (EditText) findViewById(R.id.inputIPK);
        input_tmpt_lahir = (EditText) findViewById(R.id.tempatlahir);
        input_tgl_lahir = (ImageView) findViewById(R.id.btntgllahir);
        tgl_lahir = (TextView) findViewById(R.id.tgllahir);
        input_alamat = (EditText) findViewById(R.id.inputAlamat);
        input_hobi = (EditText) findViewById(R.id.inputHobi);
        input_kk = (EditText) findViewById(R.id.inputKeahlian);
        input_telpon = (EditText) findViewById(R.id.inputTelp);
        inputNamaAyah = (EditText) findViewById(R.id.inputNamaAyah);
        inputPkAyah = (EditText) findViewById(R.id.inputPekerjaanAyah);
        inputNamaIbu = (EditText) findViewById(R.id.inputNamaIbu);
        inputPkIbu = (EditText) findViewById(R.id.inputPekerjaanIbu);
        alamatOrtu = (EditText) findViewById(R.id.inputAlamatOrtu);
        namaHb = (EditText) findViewById(R.id.inputNamaWali);
        alamatHb = (EditText) findViewById(R.id.inputAlamatWali);
        telpHb = (EditText) findViewById(R.id.inputTlpWali);
        spinhub = (Spinner) findViewById(R.id.spinHub);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hub);
        spinhub.setAdapter(adapter);
        ukuran = (Spinner) findViewById(R.id.pilihUkuran);
        ArrayAdapter<String> adt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, size);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ukuran.setAdapter(adt);


        imgFoto = (ImageView) findViewById(R.id.imgFoto);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        input_tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        listFakultas = new ArrayList<String>();
        spinFak = (Spinner) findViewById(R.id.pilihFakultas);
        spinFak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jurusanUrl = urlJurusan + getIdFakultas(position);

                listJurusan.clear();
                getDataJurusan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getDataFakultas();

        listJurusan = new ArrayList<String>();
        spinJur = (Spinner) findViewById(R.id.pilihJurusan);
        spinJur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setIdJurusan = getIdJurusan(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnPilih = (Button) findViewById(R.id.btnFoto);
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input_npm.length() == 0) {
                    input_npm.setError("NPM tidak boleh kosong");
                } else if (input_npm.length() < 10) {
                    input_npm.setError("NPM salah, silahkan cek kembali");
                } else if (input_npm.length() > 10) {
                    input_npm.setError("NPM salah, silahkan cek kembali");
                }

                npm = input_npm.getText().toString();

                if (input_nama.length() == 0) {
                    input_nama.setError("Nama tidak boleh kosong");
                }
                if (input_email.length() == 0) {
                    input_email.setError("Email tidak boleh kosong");
                }
                if (input_telpon.length() == 0) {
                    input_telpon.setError("No Telpon tidak boleh kosong");
                }
                if (namaHb.length() == 0) {
                    namaHb.setError("Nama tidak boleh kosong");
                }
                if (telpHb.length() == 0) {
                    telpHb.setError("No Telpon tidak boleh kosong");
                }

                String sortnpm = npm.substring(4,6);
                if (sortnpm.equals("08") || sortnpm.equals("07") || sortnpm.equals("06") || sortnpm.equals("05") || sortnpm.equals("04") || sortnpm.equals("03") || sortnpm.equals("02") || sortnpm.equals("01")) {
                    Log.d(TAG, "sort npm : " + sortnpm);
                    uploadData();

                } else {
//                    Toast.makeText(Regist.this, "Tidak ada NPM dengan kode fakultas " + sortnpm + ", silahkan cek kembali!", Toast.LENGTH_SHORT).show();
                    input_npm.setError("NPM salah, cek kembali");
                }
            }
        });

    }

    private void showDateDialog() {
        Calendar kalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {
                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, month, dayOfMonth);
                newDate.set(dayOfMonth, month, year);

                tgl_lahir.setText(dateFormatter.format(newDate.getTime()));
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void uploadData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please Wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DAFTAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCESS);

                    AlertDialog dialogSimpan = new AlertDialog.Builder(Regist.this).create();
                    dialogSimpan.setTitle("Pendaftaran Berhasil");
                    dialogSimpan.setMessage("Anda akan mendapatkan pesan konfirmasi pada email anda!");
                    dialogSimpan.setIcon(R.drawable.ic_check_circle_white_24dp);
                    dialogSimpan.setButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Regist.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });

                    dialogSimpan.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Regist.this, "Pendaftaran Tidak Berhasil", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tgllahir = tgl_lahir.getText().toString();
                try {
                    Date format_waktu = dateFormat.parse(tgllahir);
                    tgllahir = format.format(format_waktu);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Map<String, String> params = new HashMap<String, String>();
                int selectedGender = rgGender.getCheckedRadioButtonId();
                String gender = ((RadioButton) findViewById(selectedGender)).getText().toString().trim();
                params.put("npm", npm);
                params.put("nama_mhs", input_nama.getText().toString());
                params.put("email", input_email.getText().toString());
                params.put("jenis_kelamin", gender);
                params.put("id_jurusan", setIdJurusan.toString());
                params.put("total_sks", input_sks.getText().toString());
                params.put("ipk", input_ipk.getText().toString());
                params.put("tempat_lahir", input_tmpt_lahir.getText().toString());
                params.put("tgl_lahir", tgllahir);
                params.put("alamat", input_alamat.getText().toString());
                params.put("hobi", input_hobi.getText().toString());
                params.put("kk", input_kk.getText().toString());
                params.put("telpon", input_telpon.getText().toString());
                params.put("foto", getStringImage(decoded));
                params.put("nama_ayah", inputNamaAyah.getText().toString());
                params.put("id_pk_ayah", inputPkAyah.getText().toString());
                params.put("nama_ibu", inputNamaIbu.getText().toString());
                params.put("id_pk_ibu", inputPkIbu.getText().toString());
                params.put("alamat_ortu", alamatOrtu.getText().toString());
                params.put("nama_hb", namaHb.getText().toString());
                params.put("alamat_hb", alamatHb.getText().toString());
                params.put("telp_hb", telpHb.getText().toString());
                params.put("hubung_hb", spinhub.getSelectedItem().toString());
                params.put("ukuran_kaos", ukuran.getSelectedItem().toString());

                Log.e(TAG, "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Regist.this);
        requestQueue.add(stringRequest);

    }

    // dibawah ini untuk mengambil foto dari galery
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imgFoto.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void getDataFakultas() {
        StringRequest stringRequest = new StringRequest(urlFakultas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TEST - ", response.toString());
                JSONObject j = null;
                try {
                    j = new JSONObject(response);

                    result = j.getJSONArray("fakultas");

                    getFakultas(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(stringRequest);
    }

    private void getFakultas(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);

                listFakultas.add(json.getString("nama_fakultas"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adpFak = new ArrayAdapter<String>(Regist.this, android.R.layout.simple_spinner_dropdown_item, listFakultas);
        spinFak.setAdapter(adpFak);

        adpFak.notifyDataSetChanged();
    }

    public String getIdFakultas(int position) {
        String idFakultas = "";
        try {
            JSONObject json = result.getJSONObject(position);

            idFakultas = json.getString("id_fakultas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idFakultas;
    }

    private void getDataJurusan() {
        StringRequest sr = new StringRequest(jurusanUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    result2 = object.getJSONArray("jurusan");

                    getJurusan(result2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rQ = Volley.newRequestQueue(this);
        rQ.add(sr);
    }

    private void getJurusan(JSONArray object) {
        for (int i = 0; i < object.length(); i++) {
            try {
                JSONObject Json = object.getJSONObject(i);

                listJurusan.add(Json.getString("nama_jurusan"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adpJur = new ArrayAdapter<String>(Regist.this, android.R.layout.simple_spinner_dropdown_item, listJurusan);
        spinJur.setAdapter(adpJur);

        adpJur.notifyDataSetChanged();
    }

    private String getIdJurusan(int position) {
        String idJurusan = "";
        try {
            JSONObject Json = result2.getJSONObject(position);

            idJurusan = Json.getString("id_jurusan");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idJurusan;
    }

}

