package com.example.administrator.matata_android.bean;

import java.io.Serializable;
import java.util.List;

public class ChildDetailsBean implements Serializable {

    private String id;
    private String user_id;
    private String name;
    private String sex;
    private String age;
    private String signature;
    private String head_file;
    private String grade;
    private Plan plan;
    private String createDt;
    private String updateDt;
    private Time time;
    private List<Plans> plans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHead_file() {
        return head_file;
    }

    public void setHead_file(String head_file) {
        this.head_file = head_file;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public List<Plans> getPlans() {
        return plans;
    }

    public void setPlans(List<Plans> plans) {
        this.plans = plans;
    }

    public static class Plan{
        private List<String> day;
        private String time;

        public List<String> getDay() {
            return day;
        }

        public void setDay(List<String> day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class Time{
        private String sumTime;
        private String exerciseTime;
        private String onlineTime;
        private String offlineTime;

        public String getSumTime() {
            return sumTime;
        }

        public void setSumTime(String sumTime) {
            this.sumTime = sumTime;
        }

        public String getExerciseTime() {
            return exerciseTime;
        }

        public void setExerciseTime(String exerciseTime) {
            this.exerciseTime = exerciseTime;
        }

        public String getOnlineTime() {
            return onlineTime;
        }

        public void setOnlineTime(String onlineTime) {
            this.onlineTime = onlineTime;
        }

        public String getOfflineTime() {
            return offlineTime;
        }

        public void setOfflineTime(String offlineTime) {
            this.offlineTime = offlineTime;
        }
    }

    public static class Plans{
        private String day;
        private String time;
        private String week;
        private String sum_time;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getSum_time() {
            return sum_time;
        }

        public void setSum_time(String sum_time) {
            this.sum_time = sum_time;
        }
    }
}
