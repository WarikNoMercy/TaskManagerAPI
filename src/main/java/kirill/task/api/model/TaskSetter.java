package kirill.task.api.model;


public class TaskSetter {

    private Long taskId;
    private String workerName;
    private String subject;

    public TaskSetter(){}

    public TaskSetter(Long taskId, String workerName, String subject){
        this.taskId = taskId;
        this.workerName = workerName;
        this.subject = subject;
    }

    public TaskSetter(Long taskId, String workerName){
        this.taskId = taskId;
        this.workerName = workerName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }
}
