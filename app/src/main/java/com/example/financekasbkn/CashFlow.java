package com.example.financekasbkn;

public class CashFlow {

    private int idCashFlow;
    private int nominal;
    private String keterangan;
    private String tanggal;
    private String bulan;
    private String tahun;
    private String tipe;

    public CashFlow() {
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public int getIdCashFlow() {
        return idCashFlow;
    }

    public void setIdCashFlow(int idCashFlow) {
        this.idCashFlow = idCashFlow;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
