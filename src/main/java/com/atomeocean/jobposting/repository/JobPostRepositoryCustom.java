package com.atomeocean.jobposting.repository;

import com.atomeocean.jobposting.dto.JobPostQuery;
import com.atomeocean.jobposting.model.JobPost;
import org.springframework.data.domain.Page;

/**
 * 自定义查询接口：
 * 定义职位搜索的抽象方法（带过滤 + 排序 + 分页），
 * 具体实现由 JobPostRepositoryImpl 完成。
 */
public interface JobPostRepositoryCustom {

    /**
     * 按 JobPostQuery 中的参数搜索职位，并返回分页结果。
     */
    Page<JobPost> search(JobPostQuery query);
}

