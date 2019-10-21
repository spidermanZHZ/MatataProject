package com.example.administrator.matata_android.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 营地活动详情
 */
public class ArtCampInfoBean implements Serializable {
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
    private String discount;
    private List<String> vipPrice;
    private String is_vip;

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

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    private classify classify;

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
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

    public ArtCampInfoBean.classify getClassify() {
        return classify;
    }

    public void setClassify(ArtCampInfoBean.classify classify) {
        this.classify = classify;
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
