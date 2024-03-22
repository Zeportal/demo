package com.example.demo.repos.jpa;

import com.example.demo.entity.QComment;
import com.example.demo.entity.QTopic;
import com.example.demo.entity.Topic;
import com.example.demo.repos.TopicRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class TopicRepositoryImpl extends SimpleJpaRepository<Topic, Long> implements TopicRepository {
    protected final QComment comment = QComment.comment;
    protected final QTopic topic = QTopic.topic;
    protected final JPAQueryFactory queryFactory;

    public TopicRepositoryImpl(EntityManager em) {
        super(Topic.class, em);
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Topic> findTopicsByCommentTextContaining(String searchRequest) {
        return queryFactory
                .selectDistinct(topic)
                .from(topic)
                .where(topic.topicId.in(
                        JPAExpressions.select(comment.topicId)
                                .from(comment)
                                .where(comment.text.likeIgnoreCase('%' + searchRequest + '%'))
                ))
                .fetch();
    }

    @Override
    public void deleteById(@NonNull Long topicId) {
        queryFactory
                .delete(comment)
                .where(comment.topicId.eq(topicId))
                .execute();
        super.deleteById(topicId);
    }
}
