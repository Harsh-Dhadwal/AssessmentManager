package au.jcu.edu.assessmentmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private List<AssessmentTask> assessmentList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        db = new DatabaseHelper(MainActivity.this);

        assessmentList = new ArrayList<>();
        adapter = new ListAdapter(db, MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        assessmentList = db.getAllTasks();


//        add test assessment
        if (assessmentList.isEmpty()){
            AssessmentTask t1 = new AssessmentTask();
            t1.newAssessmentTask("CP1001", "Prac 1", "31/05/2022", "23:59");
            db.insertAssessment(t1);

            AssessmentTask t2 = new AssessmentTask();
            t2.newAssessmentTask("CP1005", "Prac 3", "7/06/2022", "23:59");
            db.insertAssessment(t2);
        }

        Collections.reverse(assessmentList);
        adapter.setTasks(assessmentList);
    }

    protected void onDestroy(){
        super.onDestroy();
        db.clearAll();

    }

}