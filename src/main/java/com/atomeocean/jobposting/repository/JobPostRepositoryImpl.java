package com.atomeocean.jobposting.repository;

import com.atomeocean.jobposting.dto.JobPostQuery;
import com.atomeocean.jobposting.model.JobPost;
import com.atomeocean.jobposting.model.JobVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 Repository 实现：
 * 把 JobPost 的「过滤 + 排序 + 分页」查询集中到这里，
 * 避免把复杂查询逻辑写在 Controller / Service 里。
 */
public class JobPostRepositoryImpl implements JobPostRepositoryCustom {

    /**
     * 通过 MongoTemplate 可以直接构造 Query / Criteria，
     * 实现更灵活的动态查询。
     */
    private final MongoTemplate mongoTemplate;

    @Autowired
    public JobPostRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 根据 JobPostQuery 中的条件进行动态查询，并返回分页结果。
     *
     * 查询逻辑包括：
     * - 过滤：department / location / visibility
     * - 排序：sortBy + sortDesc
     * - 分页：page（第几页，从 0 开始）+ size（每页条数）
     */
    @Override
    public Page<JobPost> search(JobPostQuery query) {
        // 收集所有的过滤条件，后面用 AND 组合
        List<Criteria> criteriaList = new ArrayList<>();

        // 按部门精确匹配（可选条件）
        if (query.getDepartment() != null && !query.getDepartment().isBlank()) {
            criteriaList.add(Criteria.where("department").is(query.getDepartment()));
        }

        // 按地点精确匹配（可选条件）
        if (query.getLocation() != null && !query.getLocation().isBlank()) {
            criteriaList.add(Criteria.where("location").is(query.getLocation()));
        }

        // 可见性：
        // - 如果前端有显式传 visibility，就按传入值过滤
        // - 否则默认只查 PUBLISHED（避免把草稿 / 下线职位暴露给前台）
        if (query.getVisibility() != null) {
            criteriaList.add(Criteria.where("visibility").is(query.getVisibility()));
        } else {
            criteriaList.add(Criteria.where("visibility").is(JobVisibility.PUBLISHED));
        }

        // 如果没有任何过滤条件，就使用一个空 Criteria（等价于「查全部」）
        // 否则把所有条件用 AND 连接：c1 AND c2 AND ...
        Criteria criteria = criteriaList.isEmpty()
                ? new Criteria()
                : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        // 基于上面的 Criteria 构造 MongoDB 查询对象
        Query mongoQuery = new Query(criteria);

        // 构造排序规则：
        // sortBy 指定排序字段，例如 "createdAt"
        // sortDesc 决定升序还是降序
        Sort sort = Sort.by(
                query.isSortDesc() ? Sort.Direction.DESC : Sort.Direction.ASC,
                query.getSortBy()
        );
        mongoQuery.with(sort);

        // 分页参数做一次下限保护，避免传入负数导致异常
        int page = Math.max(query.getPage(), 0);
        int size = Math.max(query.getSize(), 1);

        // MongoDB 分页核心逻辑：
        // skip(N)  表示跳过前 N 条记录，相当于 SQL 的 OFFSET
        // limit(M) 表示本次只取 M 条，相当于 SQL 的 LIMIT
        // page 从 0 开始，因此：
        //   第 0 页：skip = 0 * size = 0
        //   第 1 页：skip = 1 * size
        mongoQuery.skip((long) page * size);
        mongoQuery.limit(size);

        // 查询当前页的数据列表
        List<JobPost> results = mongoTemplate.find(mongoQuery, JobPost.class);

        // 计算总记录数：
        // 只能带过滤条件 criteria，不能带 skip / limit，
        // 否则 total 只会等于当前页的条数，而不是整体条数。
        long total = mongoTemplate.count(new Query(criteria), JobPost.class);

        // 构造 Pageable，封装 page / size / sort，
        // 方便前端使用 Page 对象里的分页元数据（totalPages / hasNext 等）
        Pageable pageable = PageRequest.of(page, size, sort);

        // 用 PageImpl 包装结果：
        // - content：当前页数据
        // - pageable：分页与排序信息
        // - total：总记录数
        return new PageImpl<>(results, pageable, total);
    }
}

