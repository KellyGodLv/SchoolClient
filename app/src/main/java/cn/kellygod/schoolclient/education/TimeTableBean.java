package cn.kellygod.schoolclient.education;

/**
 * @author kellygod
 */
public class TimeTableBean {
    public int getId() {
        return id;
    }
    TimeTableBean(int id,String timeTable){
        this.id=id;
        this.timeTable=timeTable;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    private int id=0;
    private String timeTable="";
}
