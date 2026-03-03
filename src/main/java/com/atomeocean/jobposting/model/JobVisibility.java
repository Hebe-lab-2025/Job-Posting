package com.atomeocean.jobposting.model;

/**
 * 职位可见性：
 * 用来控制 JobPost 是否对前台用户可见。
 *
 * 在我们的查询逻辑中，如果前端没有显式传入 visibility，
 * 会默认只查询 PUBLISHED 的职位，避免把草稿/下线的岗位暴露出去。
 */
public enum JobVisibility {

    /**
     * 草稿状态：仅内部可见，通常还在编辑中或审核中。
     */
    DRAFT,

    /**
     * 已发布：对外公开展示在职位列表页 / 详情页中。
     */
    PUBLISHED,

    /**
     * 已归档：历史记录，不再对外展示，但数据仍然保留。
     */
    ARCHIVED
}

