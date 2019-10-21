package com.example.administrator.matata_android.bean;

import java.io.Serializable;
import java.util.List;

public class ArtCampBean implements Serializable {
    private List<campsiteClassify> campsiteClassify;
    private List<hot> hot;

    public List<ArtCampBean.campsiteClassify> getCampsiteClassify() {
        return campsiteClassify;
    }

    public void setCampsiteClassify(List<ArtCampBean.campsiteClassify> campsiteClassify) {
        this.campsiteClassify = campsiteClassify;
    }

    public List<ArtCampBean.hot> getHot() {
        return hot;
    }

    public void setHot(List<ArtCampBean.hot> hot) {
        this.hot = hot;
    }

    public static class campsiteClassify{
        private String id;
        private String busi_type;
        private String name;
        private String icon;
        private String description;
        private String parent_id;
        private String grade;
        private String sort;
        private String status;
        private String createDt;
        private String updateDt;
        private List<String> child;

        public List<String> getChild() {
            return child;
        }

        public void setChild(List<String> child) {
            this.child = child;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBusi_type() {
            return busi_type;
        }

        public void setBusi_type(String busi_type) {
            this.busi_type = busi_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public String getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(String updateDt) {
            this.updateDt = updateDt;
        }
    }
    public static class hot{
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
        private String discount;
        private List<String> vipPrice;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public List<String> getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(List<String> vipPrice) {
            this.vipPrice = vipPrice;
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

        public ArtCampBean.classify getClassify() {
            return classify;
        }

        public void setClassify(ArtCampBean.classify classify) {
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
