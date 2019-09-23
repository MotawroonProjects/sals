package com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.adapter.Pieces_Code_Adapter;
import com.creativeshare.sals.models.Shipment_Response_Model;
import com.creativeshare.sals.models.Move_Data_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Policy extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
    private TextView tv_namefrom, tv_nameto, tv_phonefrom, tv_phoneto, tv_addressfrom, tv_address_to;
    private ImageView im_awbbarcode, im_origindestnbarcode, im_clientbarcode, im_dhlroutingbarcode;
    private Preferences preferences;
    private UserModel userModel;
    private RecyclerView rece_piece_barcode;
    private List<Shipment_Response_Model.Pieces.Piece> pieceList;
    private Pieces_Code_Adapter pieces_code_adapter;
    private String sharePath="no";
private Button bt_share;
    public static Fragment_Policy newInstance() {
        return new Fragment_Policy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);
        initView(view);
        updatedata();
        return view;
    }

    private void updatedata() {
        if(userModel!=null) {
            tv_namefrom.setText(userModel.getUser().getFirst_name() + " " + userModel.getUser().getLast_name());
            tv_phonefrom.setText(userModel.getUser().getMobile_number());
            tv_addressfrom.setText(Move_Data_Model.getAddreessf());
            tv_nameto.setText(Move_Data_Model.getName());
            tv_phoneto.setText(Move_Data_Model.getPhone());
            tv_address_to.setText(Move_Data_Model.getAdddresst());

            Shipment_Response_Model shipment_response_model = Move_Data_Model.getShipment_response_model();

            if(shipment_response_model.getBarcodes().getAWBBarCode()!=null) {

                byte[] awbdecodedString = Base64.decode(shipment_response_model.getBarcodes().getAWBBarCode(), Base64.DEFAULT);
                Bitmap awbdecodedByte = BitmapFactory.decodeByteArray(awbdecodedString, 0, awbdecodedString.length);
                im_awbbarcode.setImageBitmap(awbdecodedByte);
            }
        if(shipment_response_model.getBarcodes().getClientIDBarCode()!=null){
        byte[] clientdecodedString = Base64.decode(shipment_response_model.getBarcodes().getClientIDBarCode(), Base64.DEFAULT);
        Bitmap clientdecodedByte = BitmapFactory.decodeByteArray(clientdecodedString, 0, clientdecodedString.length);
        im_clientbarcode.setImageBitmap(clientdecodedByte);}
        if(shipment_response_model.getBarcodes().getOriginDestnBarcode()!=null){
        byte[] origindecodedString = Base64.decode(shipment_response_model.getBarcodes().getOriginDestnBarcode(), Base64.DEFAULT);
        Bitmap origindecodedByte = BitmapFactory.decodeByteArray(origindecodedString, 0, origindecodedString.length);
        im_origindestnbarcode.setImageBitmap(origindecodedByte);}
if(shipment_response_model.getBarcodes().getDHLRoutingBarCode()!=null){
        byte[] dhldecodedString = Base64.decode(shipment_response_model.getBarcodes().getDHLRoutingBarCode(), Base64.DEFAULT);
        Bitmap dhldecodedByte = BitmapFactory.decodeByteArray(dhldecodedString, 0, dhldecodedString.length);
        im_dhlroutingbarcode.setImageBitmap(dhldecodedByte);}
        if (shipment_response_model.getPieces() != null && shipment_response_model.getPieces().getPiece() != null) {
            pieceList.clear();
            pieceList.addAll(shipment_response_model.getPieces().getPiece());
            pieces_code_adapter.notifyDataSetChanged();
        }}
    }

    private void initView(View view) {
        pieceList = new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);

        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        tv_namefrom = view.findViewById(R.id.tv_name_from);
        tv_nameto = view.findViewById(R.id.tv_name_to);
        tv_phonefrom = view.findViewById(R.id.tv_phone_from);
        tv_phoneto = view.findViewById(R.id.tv_phone_to);
        tv_addressfrom = view.findViewById(R.id.tv_addressfrom);
        tv_address_to = view.findViewById(R.id.tv_addressto);
        back_arrow = view.findViewById(R.id.arrow);
        im_awbbarcode = view.findViewById(R.id.im_awbbarcode);
        im_clientbarcode = view.findViewById(R.id.im_clientbarcode);
        im_origindestnbarcode = view.findViewById(R.id.im_origindestnbarcode);
        im_dhlroutingbarcode = view.findViewById(R.id.im_dhlroutingbarcode);
        rece_piece_barcode = view.findViewById(R.id.rec_barcode);
        bt_share=view.findViewById(R.id.bt_share);
        pieces_code_adapter = new Pieces_Code_Adapter(pieceList, activity);
        rece_piece_barcode.setDrawingCacheEnabled(true);
        rece_piece_barcode.setItemViewCacheSize(25);
        rece_piece_barcode.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rece_piece_barcode.setLayoutManager(new GridLayoutManager(activity, 1));
        rece_piece_barcode.setAdapter(pieces_code_adapter);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
takeScreenshot();
share(sharePath);
            }
        });

    }
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

            // create bitmap screen capture
         NestedScrollView v1 = activity.getWindow().getDecorView().findViewById(R.id.scrollView);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = getBitmapFromView(v1, v1.getChildAt(0).getHeight(), v1.getChildAt(0).getWidth());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePath = imageFile.getPath();

         //   Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            sharePath = filePath;

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void share(String sharePath){

        Log.d("ffff",sharePath);
        File file = new File(sharePath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent .setType("image/*");
        intent .putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent );

    }
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
