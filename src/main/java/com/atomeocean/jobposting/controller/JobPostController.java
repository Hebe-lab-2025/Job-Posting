package com.atomeocean.jobposting.controller;

import com.atomeocean.jobposting.dto.JobPostQuery;
import com.atomeocean.jobposting.model.JobPost;
import com.atomeocean.jobposting.model.JobVisibility;
import com.atomeocean.jobposting.service.JobPostService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 职位 REST 控制器：
 * 向前端暴露一组纯 JSON 的 API，用于管理 JobPost。
 *
 * 主要端点：
 * - POST   /api/job-posts        创建职位
 * - PUT    /api/job-posts/{id}   更新职位
 * - GET    /api/job-posts/{id}   获取职位详情
 * - DELETE /api/job-posts/{id}   删除职位
 * - GET    /api/job-posts        分页 + 过滤查询职位列表
 */
@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    private final JobPostService service;

    public JobPostController(JobPostService service) {
        this.service = service;
    }

    /**
     * 创建职位：
     * - 请求体中携带 JobPost 的各类字段
     * - 委托给 Service 做业务处理和持久化
     */
    @PostMapping
    public JobPost create(@RequestBody JobPost jobPost) {
        return service.create(jobPost);
    }

    /**
     * 更新职位：
     * - 路径参数 id 指定要更新的职位
     * - 请求体中携带新的字段值
     */
    @PutMapping("/{id}")
    public JobPost update(@PathVariable String id, @RequestBody JobPost jobPost) {
        return service.update(id, jobPost);
    }

    /**
     * 根据 id 获取职位详情。
     */
    @GetMapping("/{id}")
    public JobPost getById(@PathVariable String id) {
        return service.findById(id);
    }

    /**
     * 根据 id 删除职位。
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    /**
     * 分页 + 过滤查询职位列表。
     *
     * 查询参数说明：
     * - department：按部门过滤（可选）
     * - location：按地点过滤（可选）
     * - visibility：按可见性过滤（可选，不传则默认只查 PUBLISHED）
     * - page：第几页（从 0 开始），例如 page=0 表示第一页
     * - size：每页条数，例如 size=10
     * - sortBy：排序字段，默认 createdAt
     * - sortDesc：是否降序，默认 true（最新的职位排在最前）
     */
    @GetMapping
    public Page<JobPost> search(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobVisibility visibility,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortDesc
    ) {
        // 将一堆 query 参数封装到 JobPostQuery 中，方便传递给 Service / Repository 层
        JobPostQuery query = new JobPostQuery();
        query.setDepartment(department);
        query.setLocation(location);
        query.setVisibility(visibility);
        query.setPage(page);
        query.setSize(size);
        query.setSortBy(sortBy);
        query.setSortDesc(sortDesc);
        return service.search(query);
    }
}

