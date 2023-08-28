/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author hp2k
 */
public class Obat {

    public String kd_barang, nm_barang, nm_jenis, kd_satuan, letak_barang, nm_industri;
    public boolean is_utama;
    public double hrg_karyawan, hrg_beli, hrg_ralan, hrg_kelas1, hrg_kelas2, hrg_kelas3, hrg_vip, hrg_vvip, hrg_beliluar;
    public int stok,kapasitas;

    public Obat(String kd_barang, String nm_barang, String nm_jenis, String kd_satun, String letak_barang, boolean is_utama, String nm_industri, double harga_beli, double hrg_karyawan, double hrg_ralan, double hrg_kelas1, double hrg_kelas2, double harg_kelas3, double harga_vip, double harga_vvip, double hrg_beliluar, int stok, int kapasitas) {
        this.kd_barang = kd_barang;
        this.nm_barang = nm_barang;
        this.nm_jenis = nm_jenis;
        this.kd_satuan = kd_satun;
        this.letak_barang = letak_barang;
        this.is_utama = is_utama;
        this.nm_industri = nm_industri;
        this.hrg_beli = harga_beli;
        this.hrg_karyawan = hrg_karyawan;
        this.hrg_ralan = hrg_ralan;
        this.hrg_kelas1 = hrg_kelas1;
        this.hrg_kelas2 = hrg_kelas2;
        this.hrg_kelas3 = harg_kelas3;
        this.hrg_vip = harga_vip;
        this.hrg_vvip = harga_vvip;
        this.hrg_beliluar = hrg_beliluar;
        this.stok = stok;
        this.kapasitas = kapasitas;
    }

    public static Obat fromResultSet(ResultSet rs) throws SQLException {
        return new Obat(
                rs.getString("kd_barang"),
                rs.getString("nm_barang"),
                rs.getString("nm_jenis"),
                rs.getString("kd_satuan"),
                rs.getString("letak_barang"),
                rs.getBoolean("is_utama"),
                rs.getString("nm_industri"),
                rs.getDouble("hrg_beli"),
                rs.getDouble("hrg_karyawan"),
                rs.getDouble("hrg_ralan"),
                rs.getDouble("hrg_kelas1"),
                rs.getDouble("hrg_kelas2"),
                rs.getDouble("hrg_kelas3"),
                rs.getDouble("hrg_vip"),
                rs.getDouble("hrg_vvip"),
                rs.getDouble("hrg_beliluar"),
                rs.getInt("stok"),
                rs.getInt("kapasitas")
        );
    }

    public double getHargaJual(String kelas) {
        switch (kelas) {
            case "Karyawan":
                return this.hrg_ralan;
            case "Rawat Jalan":
                return this.hrg_ralan;
            case "Beli Luar":
                return this.hrg_beliluar;
            case "Kelas 1":
                return this.hrg_kelas1;
            case "Kelas 2":
                return this.hrg_kelas2;
            case "Kelas 3":
                return this.hrg_kelas3;
            case "VIP":
                return this.hrg_vip;
            case "VVIP":
                return this.hrg_vvip;

        }
        return 0.0;
    }

    public double getHargaJual(double kenaikan) {
        return this.hrg_beli * (1 + kenaikan);
    }

    public static ArrayList<Obat> arrayFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Obat> result = new ArrayList();

        while (rs.next()) {
            result.add(Obat.fromResultSet(rs));
        }
        return result;
    }

}
