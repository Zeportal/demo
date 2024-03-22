package com.example.demo.repos.jpa;

import com.example.demo.entity.Comment;
import com.example.demo.entity.QComment;
import com.example.demo.entity.QTopic;
import com.example.demo.repos.CommentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends SimpleJpaRepository<Comment, Long> implements CommentRepository {
    protected final QComment comment = QComment.comment;
    protected final QTopic topic = QTopic.topic;
    protected final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        super(Comment.class, em);
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Comment> findAllCommentsByTopicAuthor(String topicAuthor) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.topic, topic)
                .where(topic.author.equalsIgnoreCase(topicAuthor))
                .fetch();
    }

    public List<Comment> findCommentsByTextContaining(String searchRequest) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.text.likeIgnoreCase('%' + searchRequest + '%'))
                .fetch();
    }

    @Override
    public List<Comment> findByTopicId(Long topicId) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.topicId.eq(topicId))
                .fetch();
    }
}
