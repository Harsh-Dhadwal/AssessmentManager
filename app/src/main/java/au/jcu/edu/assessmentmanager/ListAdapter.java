package au.jcu.edu.assessmentmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<AssessmentTask> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView view;

        ViewHolder(CardView view) {
            super(view);
            this.view = view;
        }
    }

    ListAdapter(DatabaseHelper myDB , MainActivity activity) {
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AssessmentTask item = mList.get(position);

        CardView card = holder.view;

        TextView cardName = card.findViewById(R.id.card_name);
        TextView cardSubject = card.findViewById(R.id.card_subject);
        TextView cardDate = card.findViewById(R.id.card_date);
        TextView cardTime = card.findViewById(R.id.card_time);
        CheckBox cardCheckbox = card.findViewById(R.id.card_checkbox);

        cardName.setText(item.getAssessmentName());
        cardSubject.setText(item.getSubjectCode());
        cardDate.setText(item.getDueDate());
        cardTime.setText(item.getDueTime());

        cardCheckbox.setChecked(toBoolean(item.getStatus()));
        cardCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                myDB.updateStatus(item.getId() , 1);
                myDB.deleteTask(item.getId());
            }else
                myDB.updateStatus(item.getId() , 0);
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<AssessmentTask> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        AssessmentTask item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

//    public void editItem(int position){
//        AssessmentTask item = mList.get(position);
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("id" , item.getId());
//        bundle.putString("assessmentName" , item.getAssessmentName());
//        bundle.putString("dueDate" , item.getDueDate());
//        bundle.putString("dueTime" , item.getDueTime());
//
//        AddNewAssessment task = new AddNewAssessment();
//        task.setArguments(bundle);
//        task.show(activity.getSupportFragmentManager() , task.getTag());
//
//
//    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}