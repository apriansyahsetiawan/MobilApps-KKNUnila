package incredible.kknunila.fragment;

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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import incredible.kknunila.AppController;
import incredible.kknunila.R;
import incredible.kknunila.SessionManager;

public class buatLaporan extends AppCompatActivity {

    private static final String TAG = AppController.class.getSimpleName();

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String url_laporan = "http://103.31.251.234/aplikasi_kkn/kirim_laporan.php";
    EditText nama_kegiatan, nama_pj, lokasi_kegiatan, sasaran_kegiatan, deskripsi_kegiatan;
    ImageView date_pick, foto_kegiatan, foto_kegiatan2, foto_kegiatan3;
//    Button pilih;
    FloatingActionButton kirim;
    TextView tgl_dipilih;
    Bitmap bitmap1, decode1;
    Bitmap bitmap2, decode2;
    Bitmap bitmap3, decode3;
    int success;
    int bitmap_size = 60;
    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    private static final int SELECT_FILE3 = 3;
    private static final String TAG_SUCCESS = "success";
    String tgl;
    SessionManager sesi;
    String npm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_laporan);

        nama_kegiatan = (EditText) findViewById(R.id.nama_kegiatan);
        nama_pj = (EditText) findViewById(R.id.namaPJ);
        lokasi_kegiatan = (EditText) findViewById(R.id.lokasiKegiatan);
        sasaran_kegiatan = (EditText) findViewById(R.id.sasaranKegiatan);
        deskripsi_kegiatan = (EditText) findViewById(R.id.desc_kegiatan);
        kirim = (FloatingActionButton) findViewById(R.id.kirim_laporan);
//        pilih = (Button) findViewById(R.id.pilihFoto);
        foto_kegiatan = (ImageView) findViewById(R.id.foto_kegiatan);
        foto_kegiatan2 = (ImageView) findViewById(R.id.foto_kegiatan2);
        foto_kegiatan3 = (ImageView) findViewById(R.id.foto_kegiatan3);
        date_pick = (ImageView) findViewById(R.id.btnDate);
        tgl_dipilih = (TextView) findViewById(R.id.tgl_dipilih);
        tgl_dipilih.setInputType(InputType.TYPE_NULL);
        tgl_dipilih.requestFocus();

        sesi = new SessionManager(getApplicationContext());
        npm = sesi.getNPM();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        foto_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(SELECT_FILE1);
            }
        });

        foto_kegiatan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(SELECT_FILE2);
            }
        });

        foto_kegiatan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(SELECT_FILE3);
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim_laporan();
            }
        });

    }

    private void showDateDialog() {
        Calendar kalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(dayOfMonth, month, year);
//                newDate.set(year, month, dayOfMonth);

                tgl_dipilih.setText(dateFormatter.format(newDate.getTime()));
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void kirim_laporan() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_laporan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TEST KIRIM ", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCESS);

                    AlertDialog dialogSimpan = new AlertDialog.Builder(buatLaporan.this).create();
                    dialogSimpan.setTitle("Simpan Laporan");
                    dialogSimpan.setMessage("Laporan Berhasil Disimpan!");
                    dialogSimpan.setIcon(R.drawable.ic_check_circle_white_24dp);
                    dialogSimpan.setButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(buatLaporan.this, MenuMahasiswa.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    loading.dismiss();
                    dialogSimpan.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e(TAG, "Error = " + error.getMessage());
                Toast.makeText(buatLaporan.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String foto_kegiatan = getStringImage(decode1);
                String foto_kegiatan2 = getStringImage(decode2);
                String foto_kegiatan3 = getStringImage(decode3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tgl = tgl_dipilih.getText().toString();
                try {
                    Date format_waktu = dateFormat.parse(tgl);
                    tgl = format.format(format_waktu);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("npm", npm.toString());
                params.put("nama_kegiatan", nama_kegiatan.getText().toString());
                params.put("tanggal_kegiatan", tgl);
                params.put("penanggung_jawab", nama_pj.getText().toString());
                params.put("lokasi_kegiatan", lokasi_kegiatan.getText().toString());
                params.put("sasaran_kegiatan", sasaran_kegiatan.getText().toString());
                params.put("deskripsi_kegiatan", deskripsi_kegiatan.getText().toString());
                params.put("foto_kegiatan", foto_kegiatan);
                params.put("foto_kegiatan2", foto_kegiatan2);
                params.put("foto_kegiatan3", foto_kegiatan3);

                return params;
            }
        };

        AppController.getPermission().addToRequestQueue(stringRequest, "success");

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser(int req_code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), req_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == SELECT_FILE1) {
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    setToImageView(getResizedBitmap(bitmap1, 512));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == SELECT_FILE2) {
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    setToImageView2(getResizedBitmap(bitmap2, 512));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == SELECT_FILE3) {
                try {
                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    setToImageView3(getResizedBitmap(bitmap3, 512));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decode1 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        foto_kegiatan.setImageBitmap(decode1);
    }

    private void setToImageView2(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decode2 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        foto_kegiatan2.setImageBitmap(decode2);
    }

    private void setToImageView3(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decode3 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        foto_kegiatan3.setImageBitmap(decode3);
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


}
