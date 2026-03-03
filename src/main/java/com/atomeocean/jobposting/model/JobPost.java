package com.atomeocean.jobposting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

/**
 * JobPost 文档模型：
 * 映射到 MongoDB 中的 job_posts 集合。
 *
 * 设计要点：
 * - 使用 MongoDB 存储半结构化的职位信息（职责、要求字段可能常变化）
 * - 字段设计既要方便前端展示，也要支持常用维度的过滤（department / location / visibility）
 *
 * 在面试中你可以说：
 * I modeled job posts as a MongoDB document so the description and
 * requirement fields can evolve over time without rigid schema migrations.
 */
@Document(collection = "job_posts")
public class JobPost {

    /**
     * MongoDB 文档主键，由 Mongo 自动生成。
     */
    @Id
    private String id;

    /**
     * 职位标题，例如 "Software Engineer Intern – Job Posting Backend"。
     */
    private String title;

    /**
     * 部门，例如 "Engineering"、"Platform"。
     * 用于列表页按部门过滤。
     */
    private String department;

    /**
     * 工作地点，例如 "Remote"、"Shanghai"。
     * 用于列表页按地点过滤。
     */
    private String location;

    /**
     * 用工类型，例如 "Intern" / "Full-time"。
     */
    private String employmentType;

    /**
     * 可见性状态，用于控制前台是否展示该职位。
     */
    private JobVisibility visibility;

    /**
     * 职位总体描述，可以是纯文本或 markdown 等。
     */
    private String description;

    /**
     * 职责列表（Responsibilities）。
     */
    private List<String> responsibilities;

    /**
     * 任职资格列表（Qualifications）。
     */
    private List<String> qualifications;

    /**
     * 创建时间，用于排序 & 统计。
     */
    private Instant createdAt;

    /**
     * 最近一次更新时间。
     */
    private Instant updatedAt;

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

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public JobVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(JobVisibility visibility) {
        this.visibility = visibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<String> qualifications) {
        this.qualifications = qualifications;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

