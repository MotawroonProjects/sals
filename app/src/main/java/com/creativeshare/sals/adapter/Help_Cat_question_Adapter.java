package com.creativeshare.sals.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Help_Cat_question_Adapter extends RecyclerView.Adapter<Help_Cat_question_Adapter.Eyas_Holder> {
    List<Help_Cat_Model.Categories.Faqs> list;
    Context context;
    private String current_lang;
    private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Help_Cat_question_Adapter(List<Help_Cat_Model.Categories.Faqs> list, Context context) {
        this.list = list;
        this.context = context;
        homeActivity = (Home_Activity) context;
        Paper.init(homeActivity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.help_question_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        Help_Cat_Model.Categories.Faqs model = list.get(i);
      viewHolder.tv_question.setText(model.getQuestion());

        viewHolder.tv_ans.setText(model.getAnswer());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_question,tv_ans;
        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
tv_ans=itemView.findViewById(R.id.tv_ans);

        }


    }
}