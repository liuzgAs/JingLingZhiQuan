package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class IndexCitylist implements Serializable{
    /**
     * city : [{"list":[{"act":"1","id":"55","name":"南平","pid":"4","type":"2"},{"act":"1","id":"56","name":"宁德","pid":"4","type":"2"}],"title":"N"},{"list":[],"title":"O"},{"list":[],"title":"P"},{"list":[],"title":"Q"},{"list":[],"title":"R"},{"list":[],"title":"S"},{"list":[],"title":"T"},{"list":[],"title":"U"},{"list":[],"title":"V"},{"list":[],"title":"W"},{"list":[{"act":"1","id":"60","name":"厦门","pid":"4","type":"2"}],"title":"X"},{"list":[],"title":"Y"},{"list":[],"title":"Z"}]
     * info : 操作成功！
     * status : 1
     */

    private String info;
    private int status;
    /**
     * list : [{"act":"1","id":"55","name":"南平","pid":"4","type":"2"},{"act":"1","id":"56","name":"宁德","pid":"4","type":"2"}]
     * title : N
     */

    private List<CityEntity> city;

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

    public List<CityEntity> getCity() {
        return city;
    }

    public void setCity(List<CityEntity> city) {
        this.city = city;
    }

    public static class CityEntity implements Serializable{
        private String title;
        /**
         * act : 1
         * id : 55
         * name : 南平
         * pid : 4
         * type : 2
         */

        private List<ListEntity> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity implements Serializable{
            private String act;
            private String id;
            private String name;
            private String pid;
            private String type;

            public String getAct() {
                return act;
            }

            public void setAct(String act) {
                this.act = act;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
