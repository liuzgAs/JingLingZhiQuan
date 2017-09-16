package hudongchuangxiang.com.jinglingzhiquan.model;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class OrderReceiptbefore {
    /**
     * realStatus : 0
     * realTips : 您暂未实名认证不能进行收款
     * alipayStatus : 0
     * alipayTips : 您暂未进件不能使用支付宝收款
     * wechatStatus : 0
     * wechatTips : 您暂未进件不能使用微信收款
     * status : 1
     * info : 返回成功！
     */

    private int realStatus;
    private String realTips;
    private int alipayStatus;
    private String alipayTips;
    private int wechatStatus;
    private String wechatTips;
    private int status;
    private String info;

    public int getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(int realStatus) {
        this.realStatus = realStatus;
    }

    public String getRealTips() {
        return realTips;
    }

    public void setRealTips(String realTips) {
        this.realTips = realTips;
    }

    public int getAlipayStatus() {
        return alipayStatus;
    }

    public void setAlipayStatus(int alipayStatus) {
        this.alipayStatus = alipayStatus;
    }

    public String getAlipayTips() {
        return alipayTips;
    }

    public void setAlipayTips(String alipayTips) {
        this.alipayTips = alipayTips;
    }

    public int getWechatStatus() {
        return wechatStatus;
    }

    public void setWechatStatus(int wechatStatus) {
        this.wechatStatus = wechatStatus;
    }

    public String getWechatTips() {
        return wechatTips;
    }

    public void setWechatTips(String wechatTips) {
        this.wechatTips = wechatTips;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
