package com.creativeshare.sals.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Other_Services_Model;
import com.creativeshare.sals.models.Shipment_Response_Model;

import java.util.List;

public class Pieces_Code_Adapter extends RecyclerView.Adapter<Pieces_Code_Adapter.Eyas_Holder> {
    List<Shipment_Response_Model.Pieces.Piece> list;
    Context context;
   // private String current_lang;
   // private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Pieces_Code_Adapter(List<Shipment_Response_Model.Pieces.Piece> list, Context context) {
        this.list = list;
        this.context = context;
      //  homeActivity = (Home_Activity) context;
        //Paper.init(homeActivity);
        //current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.piece_bar_code_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        Shipment_Response_Model.Pieces.Piece model = list.get(i);
        byte[] piecedecodedString = Base64.decode(model.getLicensePlateBarCode(), Base64.DEFAULT);
        Bitmap piecedecodedByte = BitmapFactory.decodeByteArray(piecedecodedString, 0, piecedecodedString.length);
        viewHolder.im_bar_code.setImageBitmap(piecedecodedByte);
viewHolder.tv_content.setText("Piece"+(i+1));



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
    private ImageView im_bar_code;
private TextView tv_content;
        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            im_bar_code = itemView.findViewById(R.id.im_barcode);
tv_content=itemView.findViewById(R.id.tv_content);

        }


    }
}