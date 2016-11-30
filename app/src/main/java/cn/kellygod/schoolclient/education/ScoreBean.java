package cn.kellygod.schoolclient.education;

/**
 * @author kellygod 2016/11/25.
 */
public class ScoreBean {
    //http://202.101.111.205:8082/sms/opac/search/showSearch.action?xc=6
    //http://202.101.111.205:8082/search?kw=单片机&xc=3
    //http://202.101.111.205:8082/sms/opac/user/lendStatus.action?sn=A03B7B762168599F6F892953A0D90DC85B7CF7CFA7690515F6543F159E4E49FA6088CD7581B863E5A8195A3F5D83DFBCCFCEA5A20262ADCDA35C25A6718D85AFC2A3C787A06291A9&xc=6
    //http://202.101.111.205:8082/sms/opac/user/lendStatus.action?xc=3&sn=A03B7B762168599F6F892953A0D90DC85B7CF7CFA7690515F6543F159E4E49FA6088CD7581B863E5A8195A3F5D83DFBC5A1BF5F6ACE379A2EBE0522404097C8AC2A3C787A06291A9
    //schoolid=631&
    // backurl=%2Fuser%2Fuc%2FshowUserCenter.jspx&
    // userType=0&username=201311503140&
    // password=888
//    <td>学年</td><td>学期</td><td>课程代码</td><td>课程名称</td><td>课程性质</td><td>课程归属</td>
//    <td>学分</td><td>绩点</td><td>成绩</td><td>辅修标记</td><td>补考成绩</td><td>重修成绩</td>
//    <td>学院名称</td><td>备注</td><td>重修标记</td><td>课程英文名称</td>

    private String xn ="";
    private String xq ="";
    private String kcdm="";
    private String kcmc="";
    private String kcxz="";
    private String kcgs="";
    private String xf="";
    private String jd="";
    private String cj="";
    private String fxbj="";
    private String bkcj="";
    private String cxcj="";
    private String xymc="";
    private String bz="";
    private String cxbj="";
    private String kcywmc="";

    public ScoreBean(){}
    public ScoreBean(String xn,
                     String xq,
                     String kcdm,
                     String kcmc,
                     String kcxz,
                     String kcgs,
                     String xf,
                     String jd,
                     String cj,
                     String fxbj,
                     String bkcj,
                     String cxcj,
                     String xymc,
                     String bz,
                     String cxbj,
                     String kcywmc) {
        this.xn = xn;
        this.xq = xq;
        this.kcdm = kcdm;
        this.kcmc = kcmc;
        this.kcxz = kcxz;
        this.kcgs = kcgs;
        this.xf = xf;
        this.jd = jd;
        this.cj = cj;
        this.fxbj = fxbj;
        this.bkcj = bkcj;
        this.cxcj = cxcj;
        this.xymc = xymc;
        this.bz = bz;
        this.cxbj = cxbj;
        this.kcywmc = kcywmc;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getKcdm() {
        return kcdm;
    }

    public void setKcdm(String kcdm) {
        this.kcdm = kcdm;
    }

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getKcxz() {
        return kcxz;
    }

    public void setKcxz(String kcxz) {
        this.kcxz = kcxz;
    }

    public String getKcgs() {
        return kcgs;
    }

    public void setKcgs(String kcgs) {
        this.kcgs = kcgs;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj;
    }

    public String getFxbj() {
        return fxbj;
    }

    public void setFxbj(String fxbj) {
        this.fxbj = fxbj;
    }

    public String getBkcj() {
        return bkcj;
    }

    public void setBkcj(String bkcj) {
        this.bkcj = bkcj;
    }

    public String getCxcj() {
        return cxcj;
    }

    public void setCxcj(String cxcj) {
        this.cxcj = cxcj;
    }

    public String getXymc() {
        return xymc;
    }

    public void setXymc(String xymc) {
        this.xymc = xymc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCxbj() {
        return cxbj;
    }

    public void setCxbj(String cxbj) {
        this.cxbj = cxbj;
    }

    public String getKcywmc() {
        return kcywmc;
    }

    public void setKcywmc(String kcywmc) {
        this.kcywmc = kcywmc;
    }
}
