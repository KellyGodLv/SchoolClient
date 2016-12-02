package cn.kellygod.schoolclient.education.utils;

/**
 * @author kellygod on 2016/11/10.
 */
public class StudentInfo {
    // 使用饿汉模式创建此单例   多线程安全
    private static StudentInfo studentInfo=new StudentInfo();
    private String studentName="";
    private String studentCode="";
    private boolean isLogin=false;

    public boolean isLogin() {
        return isLogin;
    }
    public void setLogin(boolean login) {
        isLogin = login;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }


    private StudentInfo(){ }

    public static StudentInfo getInstance(){
        return studentInfo;
    }
}
