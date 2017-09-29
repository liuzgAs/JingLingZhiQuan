package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/29 0029.
 */
public class IndexSearch {
    /**
     * data : [{"id":"1","title":"测试商品"},{"id":"11","title":"鲜花饼云南特产玫瑰饼现烤手工美食小吃传统糕点心500g中秋节礼盒"},{"id":"14","title":"【半斤包邮】福建菠萝干水果干蜜饯果脯菠萝凤梨片休闲零食 250g"},{"id":"16","title":"预售山东沾化冬枣新鲜特产5斤枣子脆现摘富硒孕妇水果大果包邮"},{"id":"18","title":"新生婴儿礼盒刚出生男女宝宝玩具满月礼物游戏毯母婴"},{"id":"19","title":"日本厨房用品可调节伸缩下水槽洗碗池置物架卫生间置地式落地层架"},{"id":"55","title":"香港ctapstar真空不锈钢保温杯轻量弹跳杯男女生儿童焖烧杯随手杯"},{"id":"20","title":"韩版可爱夏季遮阳防晒透气口罩女夏防紫外线棉布纯棉薄款防尘骑行"}]
     * data2 : [{"id":"1","title":"测试商品"},{"id":"11","title":"鲜花饼云南特产玫瑰饼现烤手工美食小吃传统糕点心500g中秋节礼盒"},{"id":"14","title":"【半斤包邮】福建菠萝干水果干蜜饯果脯菠萝凤梨片休闲零食 250g"},{"id":"16","title":"预售山东沾化冬枣新鲜特产5斤枣子脆现摘富硒孕妇水果大果包邮"},{"id":"18","title":"新生婴儿礼盒刚出生男女宝宝玩具满月礼物游戏毯母婴"},{"id":"19","title":"日本厨房用品可调节伸缩下水槽洗碗池置物架卫生间置地式落地层架"},{"id":"55","title":"香港ctapstar真空不锈钢保温杯轻量弹跳杯男女生儿童焖烧杯随手杯"},{"id":"20","title":"韩版可爱夏季遮阳防晒透气口罩女夏防紫外线棉布纯棉薄款防尘骑行"}]
     * info : 返回成功！
     * status : 1
     */

    private String info;
    private int status;
    private List<DataBean> data;
    private List<DataBean> data2;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<DataBean> getData2() {
        return data2;
    }

    public void setData2(List<DataBean> data2) {
        this.data2 = data2;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 测试商品
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
