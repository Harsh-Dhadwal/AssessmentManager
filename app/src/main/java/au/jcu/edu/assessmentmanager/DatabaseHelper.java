package au.jcu.edu.assessmentmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String DB_NAME = "ASSESSMENT_DATABASE";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ASSESSMENT_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "SUBJECT_CODE";
    private static final String COL_3 = "ASSESSMENT_NAME";
    private static final String COL_4 = "DUE_DATE";
    private static final String COL_5 = "DUE_TIME";
    private static final String COL_6 = "STATUS";

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "SUBJECT_CODE TEXT , " +
                "ASSESSMENT_NAME TEXT , " +
                "DUE_DATE TEXT , " +
                "DUE_TIME , " +
                "STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    Clear database in during testing
    public void clearAll(){
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    add assessment object to database
    public void insertAssessment(AssessmentTask assessment) {
        db = this.getWritableDatabase();

        ContentValues assessmentValues = new ContentValues();

        assessmentValues.put(COL_2, assessment.getSubjectCode());
        assessmentValues.put(COL_3, assessment.getAssessmentName());
        assessmentValues.put(COL_4, assessment.getDueDate());
        assessmentValues.put(COL_5, assessment.getDueTime());
        assessmentValues.put(COL_6, 0);

        db.insert(TABLE_NAME, null, assessmentValues);
    }

    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_6 , status);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

//    delete object from database
    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

//    retrieve data from database
    @SuppressLint("Range")
    public List<AssessmentTask> getAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<AssessmentTask> assessmentList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        AssessmentTask task = new AssessmentTask();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setSubjectCode(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setAssessmentName(cursor.getString(cursor.getColumnIndex(COL_3)));
                        task.setDueDate(cursor.getString(cursor.getColumnIndex(COL_4)));
                        task.setDueTime(cursor.getString(cursor.getColumnIndex(COL_5)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        assessmentList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return assessmentList;
    }
}
