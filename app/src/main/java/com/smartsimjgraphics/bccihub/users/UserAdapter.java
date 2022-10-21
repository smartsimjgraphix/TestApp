package com.smartsimjgraphics.bccihub.users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.ActivityEditProfile;
import com.smartsimjgraphics.bccihub.ActivityProperties;
import com.smartsimjgraphics.bccihub.ActivityUserManage;
import com.smartsimjgraphics.bccihub.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    ArrayList<Users_Model> users;
    Context context;

    public UserAdapter() {
        users = new ArrayList<>();
    }

    public void setData(ArrayList<Users_Model> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View userView = layoutInflater.inflate(R.layout.layout_user_list_display, parent, false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users_Model users_model = users.get(position);

        holder.custID.setText(users_model.getCustID());
        holder.cust_type.setText(users_model.getCust_type());
        holder.cust_fname.setText(users_model.getCust_fname());
        holder.cust_lname.setText(users_model.getCust_lname());
        holder.cust_username.setText(users_model.getCust_username());
        holder.cust_password.setText(users_model.getCust_password());
        holder.cust_phone.setText(users_model.getCust_phone());
        holder.cust_address.setText(users_model.getCust_address());
        holder.cust_city.setText(users_model.getCust_city());
        holder.cust_occupation.setText(users_model.getCust_occupation());
        holder.cust_email.setText(users_model.getCust_email());
        holder.cust_fax.setText(users_model.getCust_fax());

        holder.layout_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("BCC I HUB - ALERT!")
                        .setMessage("Select an option fo user "+ users_model.getCust_username() +
                                " of Email "+ users_model.getCust_email())
                        .setIcon(R.mipmap.ic_launcher_bcc_round)
                        .setCancelable(true)
                        .setPositiveButton("Edit", (dialogInterface, i) -> {

                            Intent intent = new Intent(context, ActivityEditProfile.class);
                            intent.putExtra("custID", users_model.getCustID());
                            intent.putExtra("cust_type", users_model.getCust_type());
                            intent.putExtra("cust_fname", users_model.getCust_fname());
                            intent.putExtra("cust_lname", users_model.getCust_lname());
                            intent.putExtra("cust_username", users_model.getCust_username());
                            intent.putExtra("cust_password", users_model.getCust_password());
                            intent.putExtra("cust_phone", users_model.getCust_phone());
                            intent.putExtra("cust_address", users_model.getCust_address());
                            intent.putExtra("cust_city", users_model.getCust_city());
                            intent.putExtra("cust_occupation", users_model.getCust_occupation());
                            intent.putExtra("cust_email", users_model.getCust_email());
                            intent.putExtra("cust_fax", users_model.getCust_fax());
                            context.startActivity(intent);

                        })
                        .setNeutralButton("Manage", (dialog, which) -> {

                            Intent intent = new Intent(context, ActivityUserManage.class);
                            intent.putExtra("custID", users_model.getCustID());
                            intent.putExtra("cust_type", users_model.getCust_type());
                            intent.putExtra("cust_fname", users_model.getCust_fname());
                            intent.putExtra("cust_lname", users_model.getCust_lname());
                            intent.putExtra("cust_username", users_model.getCust_username());
                            intent.putExtra("cust_password", users_model.getCust_password());
                            intent.putExtra("cust_phone", users_model.getCust_phone());
                            intent.putExtra("cust_address", users_model.getCust_address());
                            intent.putExtra("cust_city", users_model.getCust_city());
                            intent.putExtra("cust_occupation", users_model.getCust_occupation());
                            intent.putExtra("cust_email", users_model.getCust_email());
                            intent.putExtra("cust_fax", users_model.getCust_fax());
                            context.startActivity(intent);

                        })
                        .setNegativeButton("View Properties", (dialog, which) -> {

                            // View properties associated to a user
                            Intent intent = new Intent(context, ActivityProperties.class);
                            intent.putExtra("custID", users_model.getCustID());
                            intent.putExtra("cust_type", users_model.getCust_type());
                            intent.putExtra("cust_fname", users_model.getCust_fname());
                            intent.putExtra("cust_lname", users_model.getCust_lname());
                            intent.putExtra("cust_username", users_model.getCust_username());
                            intent.putExtra("cust_password", users_model.getCust_password());
                            intent.putExtra("cust_phone", users_model.getCust_phone());
                            intent.putExtra("cust_address", users_model.getCust_address());
                            intent.putExtra("cust_city", users_model.getCust_city());
                            intent.putExtra("cust_occupation", users_model.getCust_occupation());
                            intent.putExtra("cust_email", users_model.getCust_email());
                            intent.putExtra("cust_fax", users_model.getCust_fax());
                            context.startActivity(intent);

                        });

                builder.create();
                builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView custID, cust_type, cust_fname, cust_lname, cust_username, cust_password,
                cust_phone, cust_address, cust_city, cust_occupation, cust_email, cust_fax;
        LinearLayoutCompat layout_users;

        public ViewHolder(@NonNull View itemView) {
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
}
