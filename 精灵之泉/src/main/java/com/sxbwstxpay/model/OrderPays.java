package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/27 0027.
 */
public class OrderPays {
    /**
     * info : 返回成功！
     * payStatus : 1
     * recomm : [{"id":"1","img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","price":"100.00","title":"测试商品"},{"id":"2","img":"http://api.jlzquan.com/Uploads/goods/59cafeb58da0b.jpg","price":"6.00","title":"日光岩椰子饼厦门鼓浪屿特产馅饼伴手礼糕点零食办公室点心小吃"},{"id":"3","img":"http://api.jlzquan.com/Uploads/goods/59cb08c758024.jpg","price":"28.80","title":"海洋蓝渐变玻璃杯便携大容量韩版随手杯女创意学生情侣简约水杯子"},{"id":"4","img":"http://api.jlzquan.com/Uploads/goods/59cb09a9e89d0.png","price":"5.00","title":"雀巢咖啡1+2奶香榛果三合一速溶咖啡20条*15g*三盒"},{"id":"6","img":"http://api.jlzquan.com/Uploads/goods/59cb0cdb69737.jpg","price":"9.00","title":"儿童袜秋冬纯棉中大男女童全棉中筒袜0-1-3岁宝宝袜子"},{"id":"7","img":"http://api.jlzquan.com/Uploads/goods/59cb0d5f3b780.jpg","price":"9.00","title":"百洁布厨房抽取式抹布不粘油洗碗布无纺布加厚吸水"},{"id":"8","img":"http://api.jlzquan.com/Uploads/goods/59cb0f1b591ec.png","price":"6.00","title":"防水防尘挂式空调罩 清新印花防尘罩 全包保护罩子套"},{"id":"9","img":"http://api.jlzquan.com/Uploads/goods/59cb0fdb1f535.jpg","price":"9.00","title":"小猪佩奇儿童专用牙膏佩佩猪水果牙膏无氟防蛀可吞咽"},{"id":"10","img":"http://api.jlzquan.com/Uploads/goods/59cb11667209d.jpg","price":"9.00","title":"BOB凡士林修护润唇膏保湿滋润淡化唇纹膜补水无色女男士"}]
     * status : 1
     * statusText : 支付成功！
     */

    private String info;
    private int payStatus;
    private int status;
    private int isVip;
    private String statusText;
    private String vipText;
    private List<RecommBean> recomm;

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getVipText() {
        return vipText;
    }

    public void setVipText(String vipText) {
        this.vipText = vipText;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public List<RecommBean> getRecomm() {
        return recomm;
    }

    public void setRecomm(List<RecommBean> recomm) {
        this.recomm = recomm;
    }

}
