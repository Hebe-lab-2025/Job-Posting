package com.atomeocean.jobposting.dto;

import com.atomeocean.jobposting.model.JobVisibility;

/**
 * 职位查询参数封装：
 * 把 Controller 接收到的一堆 query 参数（department / location / page / size / sort 等）
 * 聚合成一个对象，传递到 Service / Repository。
 *
 * 这样可以避免方法签名里塞一长串参数，也方便后续扩展新的过滤条件。
 */
public class JobPostQuery {

    /**
     * 按部门过滤（可选）。
     */
    private String department;

    /**
     * 按地点过滤（可选）。
     */
    private String location;

    /**
     * 按可见性过滤（可选）。
     * 若为 null，在 Repository 中会用默认的 PUBLISHED 进行过滤。
     */
    private JobVisibility visibility;

    /**
     * 第几页（从 0 开始），对应前端的 page 参数。
     */
    private int page = 0;

    /**
     * 每页多少条记录，对应前端的 size 参数。
     */
    private int size = 10;

    /**
     * 按哪个字段排序，例如 createdAt。
     */
    private String sortBy = "createdAt";

    /**
     * 是否降序排序，默认 true（最新的职位排在最前）。
     */
    private boolean sortDesc = true;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(JobVisibility visibility) {
        this.visibility = visibility;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(boolean sortDesc) {
        this.sortDesc = sortDesc;
    }
}

