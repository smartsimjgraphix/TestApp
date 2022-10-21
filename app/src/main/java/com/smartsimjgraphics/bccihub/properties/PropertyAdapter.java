package com.smartsimjgraphics.bccihub.properties;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.ActivityCustomerPay;
import com.smartsimjgraphics.bccihub.ActivitySendMail;
import com.smartsimjgraphics.bccihub.ActivityViewProperty;
import com.smartsimjgraphics.bccihub.R;

import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

    ArrayList<Property_Model> property;
    Context context;

    public PropertyAdapter() {
        property = new ArrayList<Property_Model>();
    }

    public void setData(ArrayList<Property_Model> property) {
        this.property = property;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View gadgetView = layoutInflater.inflate(R.layout.layout_property_list_display, parent, false);
        return new PropertyViewHolder(gadgetView);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property_Model property_model = property.get(position);

        SharedPreferences pref = this.context.getSharedPreferences("runtimeDetails", MODE_PRIVATE);
        String retrieved_comp_id = pref.getString("company_id", "");
        String retrieved_comp_name = pref.getString("company_name", "");

        holder.propID.setText(String.valueOf(property_model.getPropID()));
        holder.propTitle.setText(property_model.getPropTitle());
        holder.propDistrict.setText(property_model.getPropDistrict());
        holder.propLocation.setText(property_model.getPropLocation());
        holder.propSize.setText(property_model.getPropSize());
        holder.propNumOfBedRooms.setText(property_model.getPropNumOfBedRooms());
        holder.propYear.setText(property_model.getPropYear());
        holder.propPrice.setText(property_model.getPropPrice());
        holder.propType.setText(property_model.getPropType());
        holder.propStatus.setText(property_model.getPropStatus());
        holder.propPayStatus.setText(property_model.getPropPayStatus());
        holder.propPayMonth.setText(property_model.getPropPayMonth());
        holder.propDescription.setText(property_model.getPropDescription());
        holder.cust_email.setText(property_model.getCust_email());
        holder.cust_phone.setText(property_model.getCust_phone());

        String str_prop_type = property_model.getPropType();

        if(str_prop_type.equalsIgnoreCase("Commercial")){
            //commercial
            holder.layout_properties.setBackgroundColor(Color.GREEN);
        }else if(str_prop_type.equalsIgnoreCase("Residential")){
            holder.layout_properties.setBackgroundColor(Color.RED);

        }

        holder.layout_properties.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("BCC I HUB - Alert!")
                    .setMessage("Select an option for Property: " + property_model.getPropTitle()
                            + "of type " + property_model.getPropType())
                    .setIcon(R.mipmap.ic_launcher_bcc_round)
                    .setCancelable(true)
                    .setPositiveButton("Details", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(holder.itemView.getContext(), ActivityViewProperty.class);

                            intent.putExtra("key_propID", property_model.getPropID());
                            intent.putExtra("key_propTitle", property_model.getPropTitle());
                            intent.putExtra("key_propDistrict", property_model.getPropDistrict());
                            intent.putExtra("key_propLocation", property_model.getPropLocation());
                            intent.putExtra("key_propSize", property_model.getPropSize());
                            intent.putExtra("key_propNumOfBedRooms", property_model.getPropNumOfBedRooms());
                            intent.putExtra("key_propYear", property_model.getPropYear());
                            intent.putExtra("key_propPrice", property_model.getPropPrice());
                            intent.putExtra("key_propType", property_model.getPropType());
                            intent.putExtra("key_propStatus", property_model.getPropStatus());
                            intent.putExtra("key_propPayStatus", property_model.getPropPayStatus());
                            intent.putExtra("key_propPayMonth", property_model.getPropPayMonth());
                            intent.putExtra("key_propDescription", property_model.getPropDescription());
                            intent.putExtra("key_cust_email", property_model.getCust_email());
                            intent.putExtra("key_cust_phone", property_model.getCust_phone());

                            holder.itemView.getContext().startActivity(intent);

                        }
                    })
                    .setNegativeButton("Pay", (dialog, which) -> {

                        Intent intent = new Intent(holder.itemView.getContext(), ActivityCustomerPay.class);
                        holder.itemView.getContext().startActivity(intent);

                    })
                    .setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(holder.itemView.getContext(), ActivitySendMail.class);
                            holder.itemView.getContext().startActivity(intent);

                        }
                    });

            builder.create();
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return property.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView propID, propTitle, propDistrict, propLocation, propSize,
                propNumOfBedRooms, propYear, propPrice, propType, propStatus,
                propPayStatus, propPayMonth, propDescription, cust_email, cust_phone;
        ImageView property_img;
        ConstraintLayout layout_properties;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            propID = itemView.findViewById(R.id.id_tv_list_prop_id);
            propTitle = itemView.findViewById(R.id.id_tv_list_prop_title);
            propDistrict = itemView.findViewById(R.id.id_tv_list_prop_district);
            propLocation = itemView.findViewById(R.id.id_tv_list_prop_location);
            propSize = itemView.findViewById(R.id.id_tv_list_prop_size);
            propNumOfBedRooms = itemView.findViewById(R.id.id_tv_list_prop_no_of_rooms);
            propYear = itemView.findViewById(R.id.id_tv_list_prop_year);
            propPrice = itemView.findViewById(R.id.id_tv_list_prop_price);
            propType = itemView.findViewById(R.id.id_tv_list_prop_type);
            propStatus = itemView.findViewById(R.id.id_tv_list_prop_status);
            propPayStatus = itemView.findViewById(R.id.id_tv_list_prop_pay_status);
            propPayMonth = itemView.findViewById(R.id.id_tv_list_prop_pay_month);
            propDescription = itemView.findViewById(R.id.id_tv_list_prop_desc);
            cust_email = itemView.findViewById(R.id.id_tv_list_prop_cust_email);
            cust_phone = itemView.findViewById(R.id.id_tv_list_prop_cust_phone);
            property_img = itemView.findViewById(R.id.id_tv_list_prop_img_preview);
            layout_properties = itemView.findViewById(R.id.constraint_layout_properties);

        }
    }

}
