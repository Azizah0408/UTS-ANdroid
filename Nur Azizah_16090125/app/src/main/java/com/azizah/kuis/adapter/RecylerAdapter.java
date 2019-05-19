package com.azizah.kuis.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azizah.kuis.MainActivity;
import com.azizah.kuis.R;
import com.azizah.kuis.model.DataModel;

import java.util.List;
public class RecylerAdapter  extends RecyclerView.Adapter<RecylerAdapter.MyHolder> {
    List<DataModel> mList ;
    Context ctx;

    public RecylerAdapter(Context ctx, List<DataModel> mList) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @Override
    public RecylerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist, parent, false);
        MyHolder holder = new MyHolder(layout);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecylerAdapter.MyHolder holder, final int position) {
        holder.nama.setText(mList.get(position).getNama());
        holder.kode_listrik.setText(mList.get(position).getKode_Listrik());
        holder.kategori.setText(mList.get(position).getKategori());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(ctx,MainActivity.class);
                try {
                    goInput.putExtra("id", mList.get(position).getId());
                    goInput.putExtra("nama", mList.get(position).getNama());
                    goInput.putExtra("kode_listrik", mList.get(position).getKode_Listrik());
                    goInput.putExtra("kategori", mList.get(position).getKategori());

                    ctx.startActivity(goInput);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ctx, "Error data " +e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nama, kategori, kode_listrik;
        DataModel dataModel;
        public MyHolder(View v){
            super(v);

        nama  = (TextView) v.findViewById(R.id.tvNama);
        kode_listrik = (TextView) v.findViewById(R.id.tvKode_listrik);
        kategori = (TextView) v.findViewById(R.id.tvKategori);


    }

    } }

