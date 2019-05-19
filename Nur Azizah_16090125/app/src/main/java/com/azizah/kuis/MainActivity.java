package com.azizah.kuis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azizah.kuis.api.RestApi;
import com.azizah.kuis.api.RetroServer;
import com.azizah.kuis.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText nama, kode_listrik, kategori;
    Button btnsave, btnTampildata, btnupdate,btndelete;
    ProgressBar pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = (ProgressBar) findViewById(R.id.pd);
        pd.setIndeterminate(true);
        pd.setVisibility(View.GONE);
        nama = (EditText) findViewById(R.id.edt_nama);
        kode_listrik = (EditText) findViewById(R.id.edt_kode_listrik);
        kategori = (EditText) findViewById(R.id.edt_kategori);

        btnTampildata = (Button) findViewById(R.id.btntampildata);
        btnupdate = (Button) findViewById(R.id.btnUpdate);
        btnsave = (Button) findViewById(R.id.btn_insertdata);
        btndelete = (Button) findViewById(R.id.btnhapus);

        //kondisi perubahan btn save > btn delete dan btn update
        Intent data = getIntent();
        final String iddata = data.getStringExtra("id");
        if (iddata != null) {
            btnsave.setVisibility(View.GONE);
            btnTampildata.setVisibility(View.GONE);
            btnupdate.setVisibility(View.VISIBLE);
            btndelete.setVisibility(View.VISIBLE);
            nama.setText(data.getStringExtra("nama"));
            kode_listrik.setText(data.getStringExtra("kode_listrik"));
            kategori.setText(data.getStringExtra("kategori"));
        }

        //btn update
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setVisibility(View.VISIBLE);
                RestApi api = RetroServer.getClient().create(RestApi.class);
                Call<ResponseModel> update = api.updateData(iddata, nama.getText().toString(), kode_listrik.getText().toString(), kategori.getText().toString());
                update.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Log.d("Retro", "Response");
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TampilData.class));
                        pd.setVisibility(View.GONE);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        pd.setVisibility(View.GONE);
                        Log.d("Retro", "OnFailure");
                    }
                });

            }
        });

        //btn delete
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setVisibility(View.VISIBLE);

                RestApi api = RetroServer.getClient().create(RestApi.class);
                Call<ResponseModel> del = api.deleteData(iddata);
                del.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        pd.setVisibility(View.GONE);
                        Log.d("Retro", "onResponse");
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        Intent gotampil = new Intent(MainActivity.this, TampilData.class);
                        startActivity(gotampil);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        pd.setVisibility(View.GONE);
                        Log.d("Retro", "onFailure");
                    }
                });
            }
        });
        //btn tampil data
        btnTampildata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TampilData.class));
            }
        });


        //button insert
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        String snama = nama.getText().toString();
        String skode_listrik = kode_listrik.getText().toString();
        String skategori = kategori.getText().toString();
        if (snama.isEmpty() ) {
            nama.setError("nama perlu di isi");
        }else if (skode_listrik.isEmpty()){
            kode_listrik.setError("kode listrk perlu di isi");}
            else if (skategori.isEmpty()){
            kategori.setError("kategori perlu di isi");
        } else {

            RestApi api = RetroServer.getClient().create(RestApi.class);
            Call<ResponseModel> sendbio = api.senddata(snama,skode_listrik,skategori);
            sendbio.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    /*                         pd.setVisibility(View.GONE); */
                    Log.d("RETRO", "response : " + response.body().toString());
                    String kode = response.body().getKode();

                if(kode.equals("1")) {
                    Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, TampilData.class));
                    nama.getText().clear();
                    kode_listrik.getText().clear();
                    kategori.getText().clear();

                }else                         {
                    Toast.makeText(MainActivity.this, "Data Error tidak berhasil disimpan", Toast.LENGTH_SHORT).show();
                }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    /*                         pd.setVisibility(View.GONE); */
                    Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                }
            });
        }}
        });

        }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("warnig");
        alert.setMessage("do you wan to exit");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
        }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
        }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        }
        }