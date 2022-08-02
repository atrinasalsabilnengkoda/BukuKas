package com.example.financekasbkn;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CashFlowViewHolder> {

    private List<CashFlow> cashFlowList;
    String nominal = "";

    public DetailAdapter(List<CashFlow> cashFlowList) {
        this.cashFlowList = cashFlowList;
    }

    @NonNull
    @Override
    public DetailAdapter.CashFlowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detail_item, parent, false);

        return new DetailAdapter.CashFlowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.CashFlowViewHolder holder, int position) {
        holder.keteranganTv.setText(cashFlowList.get(position).getKeterangan());
        holder.tanggalTv.setText(cashFlowList.get(position).getTanggal()+"/"+ cashFlowList.get(position).getBulan()+"/"+ cashFlowList.get(position).getTahun());

        if(cashFlowList.get(position).getTipe().contains("pemasukan")){
            nominal = "[+] Rp. "+ cashFlowList.get(position).getNominal();
            holder.detailCashFlowIcon.setImageResource(R.drawable.pemasukan_arrow);
        }else if(cashFlowList.get(position).getTipe().contains("pengeluaran")) {
            nominal = "[-] Rp. "+ cashFlowList.get(position).getNominal();
            holder.detailCashFlowIcon.setImageResource(R.drawable.pengeluaran_arrow);
        }
        holder.nominalTv.setText(nominal);

    }

    @Override
    public int getItemCount() {
        Log.d(DetailCashFlow.class.getSimpleName(), String.valueOf(cashFlowList.size()));
        return cashFlowList.size();
    }

    public class CashFlowViewHolder extends RecyclerView.ViewHolder {

        public TextView nominalTv;
        public TextView keteranganTv;
        public TextView tanggalTv;
        public ImageView detailCashFlowIcon;


        public CashFlowViewHolder(@NonNull View itemView) {
            super(itemView);

            nominalTv = itemView.findViewById(R.id.textNominalDetails);
            keteranganTv = itemView.findViewById(R.id.textDescription);
            tanggalTv = itemView.findViewById(R.id.textDate);
            detailCashFlowIcon = itemView.findViewById(R.id.arrowLogo);
        }
    }
}

