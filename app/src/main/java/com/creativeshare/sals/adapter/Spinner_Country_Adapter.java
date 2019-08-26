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
import com.creativeshare.sals.models.Country_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Spinner_Country_Adapter extends BaseAdapter {
    private List<Country_Model.Countries> countriesList;
    private LayoutInflater inflater;
    private String current_language;

    public Spinner_Country_Adapter(Context context, List<Country_Model.Countries> countriesList) {
        this.countriesList = countriesList;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @Override
    public int getCount() {
        return countriesList.size();
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

        Country_Model.Countries country_model = countriesList.get(position);
if(current_language.equals("ar")){
    tv_name.setText(country_model.getAr_name());

}
else {
    tv_name.setText(country_model.getEn_name());
}
        return convertView;
    }
}
