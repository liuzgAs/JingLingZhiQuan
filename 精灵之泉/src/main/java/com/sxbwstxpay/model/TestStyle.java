package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuzhigang on 2018/9/20/020.
 *
 * @author LiuZG
 */
public class TestStyle implements Serializable{

    /**
     * data : [{"id":"1","title":"沉稳、大方、知性","type":"1","img":""},{"id":"2","title":"温婉、优雅、温柔","type":"1","img":""},{"id":"3","title":"活泼、甜美、可爱","type":"1","img":""},{"id":"4","title":"狂野、大气、不羁","type":"1","img":""},{"id":"5","title":"性感、妩媚、撩人","type":"1","img":""},{"id":"6","title":"利落、直率、中性","type":"1","img":""},{"id":"7","title":"寡淡、文艺、文静","type":"1","img":""},{"id":"8","title":"新潮、特立独行、个性","type":"1","img":""},{"id":"9","title":"张扬、霸气、强势","type":"1","img":""},{"id":"10","title":"A","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba204606aa26.jpg"},{"id":"11","title":"B","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba206d1393e7.jpg"},{"id":"12","title":"C","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba206f026a02.jpg"},{"id":"13","title":"D","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba207e1e7769.jpg"},{"id":"14","title":"E","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba208429f926.jpg"},{"id":"15","title":"F","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba2085958e8f.jpg"},{"id":"16","title":"G","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba20869bec80.jpg"},{"id":"17","title":"H","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba2089fe024c.jpg"},{"id":"18","title":"I","type":"2","img":"http://api.jlzquan.com/Uploads/test/5ba208b1e61ab.jpg"},{"id":"19","title":"A","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20a7989f0d.jpg"},{"id":"20","title":"B","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20a8f538f3.jpg"},{"id":"21","title":"C","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20aac30ad7.jpg"},{"id":"22","title":"D","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20ac24ad9e.jpg"},{"id":"23","title":"E","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20addf1b32.jpg"},{"id":"24","title":"F","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20af775d21.jpg"},{"id":"25","title":"G","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20b1d4e867.jpg"},{"id":"26","title":"H","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20b3605424.jpg"},{"id":"27","title":"I","type":"3","img":"http://api.jlzquan.com/Uploads/test/5ba20b46cf554.jpg"},{"id":"28","title":"A、脸部轮廓偏大，五官突出醒目","type":"4","img":"http://api.jlzquan.com/Uploads/test/5ba20db1400c1.jpg"},{"id":"29","title":"B、脸部轮廓偏大，五官适中","type":"4","img":"http://api.jlzquan.com/Uploads/test/5ba20dce82127.jpg"},{"id":"30","title":"C、脸部轮廓偏小，五官小巧细致","type":"4","img":"http://api.jlzquan.com/Uploads/test/5ba20de9a3426.jpg"},{"id":"31","title":"D、脸部轮廓偏小，五官偏大","type":"4","img":"http://api.jlzquan.com/Uploads/test/5ba20e12be42c.jpg"}]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * title : 沉稳、大方、知性
         * type : 1
         * img :
         */

        private String id;
        private String title;
        private String type;
        private String img;
        private boolean isc;
        public boolean getIsc() {
            return isc;
        }

        public void setIsc(boolean isc) {
            this.isc = isc;
        }
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
