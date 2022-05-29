package au.jcu.edu.assessmentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewAssessmentInput extends AppCompatActivity {

    //widgets
    private EditText taskInput, subjectInput, dateInput, timeInput;

    private DatabaseHelper myDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment_input);

        taskInput = findViewById(R.id.taskInput);
        subjectInput = findViewById(R.id.subjectInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        Button saveButton = findViewById(R.id.saveBtn);

        myDb = new DatabaseHelper(getApplicationContext());

        saveButton.setOnClickListener(v -> {
            String task = taskInput.getText().toString();
            String subject = subjectInput.getText().toString();
            String date = dateInput.getText().toString();
            String time = timeInput.getText().toString();

            if (task.isEmpty() || subject.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill in all fields!!!!", Toast.LENGTH_SHORT).show();
            } else {
                AssessmentTask assessmentTask = new AssessmentTask();
                assessmentTask.newAssessmentTask(subject, task, date, time);
                myDb.insertAssessment(assessmentTask);
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}