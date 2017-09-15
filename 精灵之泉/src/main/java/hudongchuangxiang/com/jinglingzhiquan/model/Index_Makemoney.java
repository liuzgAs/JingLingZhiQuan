package hudongchuangxiang.com.jinglingzhiquan.model;

/**
 * Created by zhangjiebo on 2017/9/15 0015.
 */
public class Index_Makemoney {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private String url;
    private int status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
