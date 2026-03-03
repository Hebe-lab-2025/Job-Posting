package com.atomeocean.jobposting.repository;

import com.atomeocean.jobposting.model.JobPost;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * JobPost 的主 Repository 接口。
 *
 * 继承自：
 * - MongoRepository：提供基础的 CRUD 能力（save/findById/delete 等）
 * - JobPostRepositoryCustom：提供我们自己扩展的 search(…) 动态查询。
 *
 * 在面试中可以这样说：
 * I extended Spring Data's MongoRepository and plugged in a custom repository
 * implementation for dynamic, paginated search on top of the basic CRUD methods.
 */
public interface JobPostRepository extends MongoRepository<JobPost, String>, JobPostRepositoryCustom {
}

