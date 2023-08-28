/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import fungsi.HUtil;

/**
 *
 * @author hp2k
 */
public class RacikanDetail {

    public Obat obat;
    public double p1, p2, kandungan;

    public RacikanDetail(Obat obat) {
        this.obat = obat;
        p1 = 1;
        p2 = 1;
        kandungan = this.obat.kapasitas;
    }

    public void setP1(double newP1) {
        if (newP1 == 0) {
            this.p1 = 1;
            return;
        }
        this.p1 = newP1;
        updateKandungan();
    }

    public void setP2(double newP2) {
        if (newP2 == 0) {
            this.p2 = 1;
            return;
        }
        this.p2 = newP2;
        updateKandungan();
    }

    private void updateKandungan() {
        this.kandungan = this.p1 / this.p2 * this.obat.kapasitas;
    }

    public void setKandungan(double newKandungan) {
        double fpb = newKandungan > this.obat.kapasitas ? HUtil.fpb(newKandungan, this.obat.kapasitas) : HUtil.fpb(this.obat.kapasitas, newKandungan);

        this.kandungan = newKandungan;

        this.p1 = newKandungan / fpb;
        this.p2 = this.obat.kapasitas / fpb;
    }
}
