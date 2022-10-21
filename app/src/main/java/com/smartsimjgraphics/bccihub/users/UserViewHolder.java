package com.smartsimjgraphics.bccihub.users;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView custID, cust_type, cust_fname, cust_lname, cust_username, cust_password,
            cust_phone, cust_address, cust_city, cust_occupation, cust_email, cust_fax;
    LinearLayoutCompat layout_users;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        custID = itemView.findViewById(R.id.id_tv_users_list_id);
        cust_type = itemView.findViewById(R.id.id_tv_users_list_type);
        cust_fname = itemView.findViewById(R.id.id_tv_users_list_fname);
        cust_lname = itemView.findViewById(R.id.id_tv_users_list_lname);
        cust_username = itemView.findViewById(R.id.id_tv_users_list_username);
        cust_password = itemView.findViewById(R.id.id_tv_users_list_password);
        cust_phone = itemView.findViewById(R.id.id_tv_users_list_phone);
        cust_address = itemView.findViewById(R.id.id_tv_users_list_address);
        cust_city = itemView.findViewById(R.id.id_tv_users_list_city);
        cust_occupation = itemView.findViewById(R.id.id_tv_users_list_occupation);
        cust_email = itemView.findViewById(R.id.id_tv_users_list_email);
        cust_fax = itemView.findViewById(R.id.id_tv_users_list_fax);
        layout_users = itemView.findViewById(R.id.il_layout_users);

    }
}