package cn.kellygod.schoolclient.education.utils;

/**
 * @author kellygod on 2016/11/11.
 */
public class EducationCode {

    public static final String STEUDENT_MAIN                =   "xs_main.aspx";
    //学生考试查询网址
    public static final String STUDENT_EXSM_ROOM_QUERY      =   "xskscx.aspx";
    //学生课表查询网址
    public static final String STUDENT_TABLE_TIME_QUERY     =   "tjkbcx.aspx";
    //学生成绩查询网址
    public static final String STUDENT_SCORE_QUERY          =   "xscj_gc.aspx";
    //专业推荐课查询
    public static final String TIMETABLE_QUERY              =   "gnmkdm=N121601";
    //个人课表（不推荐）
    public static final String TIMETABLE_QUERY_PERSON       =   "gnmkdm=N121603";
    //考场查询代码
    public static final String EXAMINATION_QUERY            =   "gnmkdm=N121604";
    //考试成绩查询 成绩查询需要post数据提交__VIEWSTATE 可用jsoup提取 及按钮名称
    public static final String SCORE_QUERY                  =   "gnmkdm=N121605";

    /**
     *  查询成绩Post数据时需要提交
     * */
    //在校成绩查询
    public static final String RECORD_SCORE_QUERY_BUTTON    =   "在校成绩查询";
    //查询已修课程最高成绩
    public static final String MAX_SCORE_QUERY_BUTTON       =   "查询已修课程最高成绩";

}
