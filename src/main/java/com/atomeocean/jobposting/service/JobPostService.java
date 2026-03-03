package com.atomeocean.jobposting.service;

import com.atomeocean.jobposting.dto.JobPostQuery;
import com.atomeocean.jobposting.model.JobPost;
import com.atomeocean.jobposting.repository.JobPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * 职位业务层：
 * 负责处理 JobPost 相关的业务规则，
 * 对外隐藏 Repository 的细节，为 Controller 提供清晰的接口。
 */
@Service
public class JobPostService {

    /**
     * 通过注入 Repository 实现数据访问。
     */
    private final JobPostRepository repository;

    public JobPostService(JobPostRepository repository) {
        this.repository = repository;
    }

    /**
     * 创建职位：
     * - 初始化创建时间/更新时间
     * - 确保 ID 由数据库生成（手动置空）
     */
    public JobPost create(JobPost jobPost) {
        Instant now = Instant.now();
        jobPost.setId(null);
        jobPost.setCreatedAt(now);
        jobPost.setUpdatedAt(now);
        return repository.save(jobPost);
    }

    /**
     * 更新职位：
     * - 根据 id 先查出已有记录，不存在则抛异常
     * - 更新各字段，再写回数据库
     */
    public JobPost update(String id, JobPost updated) {
        JobPost existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobPost not found: " + id));

        existing.setTitle(updated.getTitle());
        existing.setDepartment(updated.getDepartment());
        existing.setLocation(updated.getLocation());
        existing.setEmploymentType(updated.getEmploymentType());
        existing.setVisibility(updated.getVisibility());
        existing.setDescription(updated.getDescription());
        existing.setResponsibilities(updated.getResponsibilities());
        existing.setQualifications(updated.getQualifications());
        existing.setUpdatedAt(Instant.now());

        return repository.save(existing);
    }

    /**
     * 根据 id 查询职位详情。
     */
    public JobPost findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobPost not found: " + id));
    }

    /**
     * 根据 id 删除职位。
     */
    public void delete(String id) {
        repository.deleteById(id);
    }

    /**
     * 按查询参数分页搜索职位。
     */
    public Page<JobPost> search(JobPostQuery query) {
        return repository.search(query);
    }
}

