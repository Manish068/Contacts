package com.example.contacts.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.Models.MyContacts;
import com.example.contacts.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    Context context;
    ArrayList<MyContacts> myContactsArrayList;

    public MyAdapter(Context context, ArrayList<MyContacts> myContactsArrayList) {
        this.context = context;
        this.myContactsArrayList = myContactsArrayList;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        MyContacts myContacts = myContactsArrayList.get(position);
        holder.tvName.setText(myContacts.getName());
        holder.tvNumber.setText(myContacts.getNumber());

    }

    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        AppCompatImageButton callButton, MessageButton;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtname);
            tvNumber = (TextView) itemView.findViewById(R.id.txtNumber);
            callButton = itemView.findViewById(R.id.ibcall);
            MessageButton = itemView.findViewById(R.id.ibMessage);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + myContactsArrayList.get(getAdapterPosition()).getNumber()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }
            });
            MessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mintent=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+myContactsArrayList.get(getAdapterPosition()).getNumber()));
                    context.startActivity(mintent);
                }
            });
        }
    }
}
