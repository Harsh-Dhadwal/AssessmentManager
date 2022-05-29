package au.jcu.edu.assessmentmanager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<AssessmentTask> assessmentList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton addBtn = findViewById(R.id.addBtn);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        db = new DatabaseHelper(MainActivity.this);

        assessmentList = new ArrayList<>();
        adapter = new ListAdapter(db);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        assessmentList = db.getAllTasks();


//  //      add test assessment
//        if (assessmentList.isEmpty()){
//            AssessmentTask t1 = new AssessmentTask();
//            t1.newAssessmentTask("CP1001", "Prac 1", "31/05/2022", "23:59");
//            db.insertAssessment(t1);
//
//            AssessmentTask t2 = new AssessmentTask();
//            t2.newAssessmentTask("CP1005", "Prac 3", "7/06/2022", "23:59");
//            db.insertAssessment(t2);
//        }

        Collections.reverse(assessmentList);
        adapter.setTasks(assessmentList);

//        add new item
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewAssessmentInput.class);
            launchForm.launch(intent);
        }
        );

//        delete item on swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    protected void onDestroy(){
        super.onDestroy();
//        db.clearAll();

    }

    ActivityResultLauncher<Intent> launchForm = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> refreshList()
            );

    @SuppressLint("NotifyDataSetChanged")
    private void refreshList() {
        assessmentList = db.getAllTasks();
        Collections.reverse(assessmentList);
        adapter.setTasks(assessmentList);
        adapter.notifyDataSetChanged();
    }

}