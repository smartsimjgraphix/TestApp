package com.smartsimjgraphics.bccihub.payments;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

public class PaymentsViewHolder extends RecyclerView.ViewHolder{

    public TextView payID, pay_source, pay_date_time, cust_username, cust_phone, cust_email;

    public ImageView imgPaymentPreview;
    public LinearLayout layout_payments;


    public PaymentsViewHolder(@NonNull View itemView) {
        super(itemView);

        layout_payments = itemView.findViewById(R.id.id_layout_payment_thumbnail);
        imgPaymentPreview = itemView.findViewById(R.id.id_image_thumb_payment_preview);
        payID = itemView.findViewById(R.id.id_tv_payment_id);
        pay_source = itemView.findViewById(R.id.id_tv_pay_source);
        pay_date_time = itemView.findViewById(R.id.id_tv_pay_date_time);
        cust_username = itemView.findViewById(R.id.id_tv_payment_cust_username);
        cust_phone = itemView.findViewById(R.id.id_tv_payment_cust_phone);
        cust_email = itemView.findViewById(R.id.id_tv_payment_cust_email);

    }

}
