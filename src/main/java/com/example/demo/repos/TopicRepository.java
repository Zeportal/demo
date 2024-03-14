package com.example.demo.repos;

import com.example.demo.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    @Query("SELECT DISTINCT t FROM Topic t JOIN t.comments c WHERE c.text LIKE %:searchRequest%")
    List<Topic> findTopicsByCommentTextContaining(@Param("searchRequest") String searchRequest);
}
