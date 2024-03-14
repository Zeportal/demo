package com.example.demo.repos;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTopicId(Long topicId);
    @Query("SELECT c FROM Comment c JOIN c.topic t WHERE t.author = :topicAuthor")
    List<Comment> findAllCommentsByTopicAuthor(@Param("topicAuthor") String topicAuthor);

    @Query("SELECT c FROM Comment c WHERE c.text LIKE %:searchRequest%")
    List<Comment> findCommentsByTextContaining(@Param("searchRequest") String searchRequest);
}
