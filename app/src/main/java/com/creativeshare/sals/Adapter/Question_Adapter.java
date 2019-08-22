package com.creativeshare.sals.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.Questions_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Question_Adapter extends RecyclerView.Adapter<Question_Adapter.Eyas_Holder> {
    List<Questions_Model.Faqs> list;
    Context context;
    private String current_lang;
    private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Question_Adapter(List<Questions_Model.Faqs> list, Context context) {
        this.list = list;
        this.context = context;
        homeActivity = (Home_Activity) context;
        Paper.init(homeActivity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        String content = "";
        Questions_Model.Faqs model = list.get(i);
      viewHolder.tv_Question.setText(model.getQuestion());
viewHolder.tv_Question.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        homeActivity.DisplayFragmentQuestion(list.get(viewHolder.getLayoutPosition()));
    }
});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_Question;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_Question = itemView.findViewById(R.id.tv_question);


        }


    }
}