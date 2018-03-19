package incredible.kknunila;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 10/31/2017.
 */

public class ListLaporan {
    private String id_laporan, foto_kegiatan, nama_kegiatan, waktu_kegiatan, pj_kegiatan, lokasi_kegiatan, sasaran_kegiatan, deskripsi_kegiatan;

    public String getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(String id_laporan) {
        this.id_laporan = id_laporan;
    }

    public String getFoto_kegiatan() {
        return foto_kegiatan;
    }

    public void setFoto_kegiatan(String foto_kegiatan) {
        this.foto_kegiatan = foto_kegiatan;
    }

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public void setNama_kegiatan(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public String getWaktu_kegiatan() {
        return waktu_kegiatan;
    }

    public void setWaktu_kegiatan(String waktu_kegiatan) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date format_waktu = dateFormat.parse(waktu_kegiatan);
            waktu_kegiatan = format.format(format_waktu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.waktu_kegiatan = waktu_kegiatan;
    }

    public String getPj_kegiatan() {
        return pj_kegiatan;
    }

    public void setPj_kegiatan(String pj_kegiatan) {
        this.pj_kegiatan = pj_kegiatan;
    }

    public String getLokasi_kegiatan() {
        return lokasi_kegiatan;
    }

    public void setLokasi_kegiatan(String lokasi_kegiatan) {
        this.lokasi_kegiatan = lokasi_kegiatan;
    }

    public String getSasaran_kegiatan() {
        return sasaran_kegiatan;
    }

    public void setSasaran_kegiatan(String sasaran_kegiatan) {
        this.sasaran_kegiatan = sasaran_kegiatan;
    }

    public String getDeskripsi_kegiatan() {
        return deskripsi_kegiatan;
    }

    public void setDeskripsi_kegiatan(String deskripsi_kegiatan) {
        this.deskripsi_kegiatan = deskripsi_kegiatan;
    }

    public ListLaporan(String id_laporan, String foto_kegiatan, String nama_kegiatan, String waktu_kegiatan) {
//        , String pj_kegiatan, String lokasi_kegiatan, String sasaran_kegiatan, String deskripsi_kegiatan

        String imgPath = "http://103.31.251.234/aplikasi_kkn/uploads/";
        this.id_laporan = id_laporan;
        this.foto_kegiatan = imgPath + foto_kegiatan;
        this.nama_kegiatan = nama_kegiatan;
        this.waktu_kegiatan = waktu_kegiatan;
//        this.pj_kegiatan = pj_kegiatan;
//        this.lokasi_kegiatan = lokasi_kegiatan;
//        this.sasaran_kegiatan = sasaran_kegiatan;
//        this.deskripsi_kegiatan = deskripsi_kegiatan;
    }

}
