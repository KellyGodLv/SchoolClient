package cn.kellygod.schoolclient.connection;

/**
 * @author 2016/11/27.
 */
public class UpdateAppBean {
    private String version="";
    private String message="";
    private String url="";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
