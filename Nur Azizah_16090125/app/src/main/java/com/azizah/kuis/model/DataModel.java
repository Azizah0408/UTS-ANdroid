package com.azizah.kuis.model;

import com.google.gson.annotations.SerializedName;

    @SuppressWarnings("unused")
    public class   DataModel {

        @SerializedName("Kategori")
        private String mKategori;
        @SerializedName("id")
        private String mId;
        @SerializedName("nama")
        private String mNama;
        @SerializedName("kode_listrik")
        private String mKode_listrik;

        public String getKategori() {
            return mKategori;     }


        public void setKategori(String kategori) { mKategori = kategori;}

        public String getId() {return mId;}

        public void setId(String id) {mId = id;}

        public String getNama() {return mNama;}

        public void setNama(String nama) { mNama = nama;}

        public String getKode_Listrik() {return mKode_listrik;}

        public void setKode_listrik(String kode_listrik) { mKode_listrik = kode_listrik;}

    }

