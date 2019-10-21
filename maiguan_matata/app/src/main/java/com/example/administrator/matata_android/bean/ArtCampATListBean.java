package com.example.administrator.matata_android.bean;

import java.io.Serializable;
import java.util.List;

public class ArtCampATListBean  implements Serializable{
    private String current_page;
    private List<data> data;
    private String first_page_url;
    private String from;
    private String last_page;
    private String last_page_url;
    private String next_page_url;
    private String path;
    private String per_page;
    private String prev_page_url;
    private String to;
    private String total;

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<ArtCampATListBean.data> getData() {
        return data;
    }

    public void setData(List<ArtCampATListBean.data> data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static class data{
        private String id;
        private String name;
        private String class_id;
        private String object;
        private String people;
        private String tag;
        private String addr;
        private String distribution_ratio1;
        private String distribution_ratio2;
        private String sort;
        private String description;
        private String profile;
        private List<String> attribute;
        private List<String> date;
        private List<String> price;
        private String status;
        private String updateDt;
        private String createDt;
        private String cover_pic;
        private List<String> detail_pic;
        private classify classify;

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

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getDistribution_ratio1() {
            return distribution_ratio1;
        }

        public void setDistribution_ratio1(String distribution_ratio1) {
            this.distribution_ratio1 = distribution_ratio1;
        }

        public String getDistribution_ratio2() {
            return distribution_ratio2;
        }

        public void setDistribution_ratio2(String distribution_ratio2) {
            this.distribution_ratio2 = distribution_ratio2;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public List<String> getAttribute() {
            return attribute;
        }

        public void setAttribute(List<String> attribute) {
            this.attribute = attribute;
        }

        public List<String> getDate() {
            return date;
        }

        public void setDate(List<String> date) {
            this.date = date;
        }

        public List<String> getPrice() {
            return price;
        }

        public void setPrice(List<String> price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(String updateDt) {
            this.updateDt = updateDt;
        }

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public String getCover_pic() {
            return cover_pic;
        }

        public void setCover_pic(String cover_pic) {
            this.cover_pic = cover_pic;
        }

        public List<String> getDetail_pic() {
            return detail_pic;
        }

        public void setDetail_pic(List<String> detail_pic) {
            this.detail_pic = detail_pic;
        }

        public ArtCampATListBean.classify getClassify() {
            return classify;
        }

        public void setClassify(ArtCampATListBean.classify classify) {
            this.classify = classify;
        }
    }

    private static class classify{
        private String id;
        private String name;

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
    }
}
