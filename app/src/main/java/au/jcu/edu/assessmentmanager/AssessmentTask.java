package au.jcu.edu.assessmentmanager;

public class AssessmentTask {

    private int id, status;
    private String subjectCode, assessmentName, dueDate, dueTime;

    public void newAssessmentTask(String subjectCode, String assessmentName, String dueDate, String dueTime){
        this.id = 0;
        this.subjectCode = subjectCode;
        this.assessmentName = assessmentName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.status = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
