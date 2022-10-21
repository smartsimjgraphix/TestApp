package com.smartsimjgraphics.bccihub.properties;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

public class PropertyViewHolder extends RecyclerView.ViewHolder {

    TextView propID, propTitle, propDistrict, propLocation, propSize,
            propNumOfBedRooms, propYear, propPrice, propType, propStatus,
            propPayStatus, propPayMonth, propDescription, cust_email, cust_phone;
    ImageView property_img;
    LinearLayout layout_properties;

    public PropertyViewHolder(@NonNull View itemView) {
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
