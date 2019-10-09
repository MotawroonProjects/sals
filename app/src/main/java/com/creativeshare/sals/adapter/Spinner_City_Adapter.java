package com.creativeshare.sals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.creativeshare.sals.R;
import com.creativeshare.sals.models.CityModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Spinner_City_Adapter extends BaseAdapter {
    private List<CityModel.postal_codes> cityModelList;
    private LayoutInflater inflater;
    private String current_language;

    public Spinner_City_Adapter(Context context, List<CityModel.postal_codes> cityModelList) {
        this.cityModelList = cityModelList;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @Override
    public int getCount() {
        return cityModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);
        }
        TextView tv_name = convertView.findViewById(R.id.tv_name);

        CityModel.postal_codes cityModel = cityModelList.get(position);
    tv_name.setText(cityModel.getCity());


        return convertView;
    }
}
