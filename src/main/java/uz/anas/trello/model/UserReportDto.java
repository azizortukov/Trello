package uz.anas.trello.model;

public interface UserReportDto {

    String getFullName();
    Long getOnTimeTasks();
    Long getLateTasks();
    Long getFinishedTasks();

}
