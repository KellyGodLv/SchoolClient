package cn.kellygod.schoolclient.education.utils;

/**
 * @author kellygod on 2016/11/14.
 */
public class ExaminationRoomBean {
    private String courseCode="";
    private String courseName="";
    private String userName="";
    private String examTime="";
    private String examAddr="";
    private String examType="";
    private String seatNum="";

    public ExaminationRoomBean(String courseCode, String courseName, String userName, String examTime, String examAddr, String examType, String seatNum, String campusAddr) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.userName = userName;
        this.examTime = examTime;
        this.examAddr = examAddr;
        this.examType = examType;
        this.seatNum = seatNum;
        this.campusAddr = campusAddr;
    }
    public ExaminationRoomBean(){}
    public String getCampusAddr() {
        return campusAddr;
    }

    public void setCampusAddr(String campusAddr) {
        this.campusAddr = campusAddr;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamAddr() {
        return examAddr;
    }

    public void setExamAddr(String examAddr) {
        this.examAddr = examAddr;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    private String campusAddr="";

}
