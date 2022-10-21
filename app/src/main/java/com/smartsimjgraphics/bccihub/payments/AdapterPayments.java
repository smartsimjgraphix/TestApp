package com.smartsimjgraphics.bccihub.payments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

import java.util.ArrayList;

public class AdapterPayments extends RecyclerView.Adapter<PaymentsViewHolder>{

    ArrayList<Payments_Model> payments;
    Context context;

    public AdapterPayments() {
        payments = new ArrayList<>();
    }

    public void setData(ArrayList<Payments_Model> payments){
        this.payments = payments;
    }

    @NonNull
    @Override
    public PaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View paymentsView = layoutInflater.inflate(R.layout.layout_payments_list_display, parent,false);
        return new PaymentsViewHolder(paymentsView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsViewHolder holder, int position) {
        Payments_Model payments_model = payments.get(position);

        holder.payID.setText(String.valueOf(payments_model.getPayID()));
        holder.pay_source.setText(payments_model.getPay_source());
        holder.pay_date_time.setText(payments_model.getPay_date_time());
        holder.cust_username.setText(payments_model.getCust_username());
        holder.cust_phone.setText(payments_model.getCust_phone());
        holder.cust_email.setText(payments_model.getCust_email());

        holder.layout_payments.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Alliance Media")
                    .setMessage("Selected is " + payments_model.getPay_source()
                            + " at "+ payments_model.getPay_date_time() + " of Customer Number "
                            + payments_model.getCust_phone() + " Email: " +payments_model.getCust_email())

                    .setIcon(R.drawable.web_hi_res_512)
                    .setCancelable(true)
                    .setPositiveButton("Accept & Authourise", (dialogInterface, i) -> {

                        Toast.makeText(context, "Status updated", Toast.LENGTH_SHORT).show();

                        /*Intent intent = new Intent(holder.itemView.getContext(), ActivitySingleAdvertView.class);
                        intent.putExtra("key_intent_advertId", advertID);
                        holder.itemView.getContext().startActivity(intent);*/

                    });

            builder.create();
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView payID, pay_source, pay_date_time, cust_username, cust_phone, cust_email;

        public ImageView imgPaymentPreview;
        public LinearLayout layout_payments;

        public ViewHolder(@NonNull View itemView) {
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

}
