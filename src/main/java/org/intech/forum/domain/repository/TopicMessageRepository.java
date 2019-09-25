package org.intech.forum.domain.repository;

import org.intech.forum.domain.entity.TopicMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Repository
public interface TopicMessageRepository extends JpaRepository<TopicMessage, Integer> {

    Optional<TopicMessage> findByMessageTitle(String messageTitle);

    Page<TopicMessage> findAllByTopicId(int topicId, Pageable pageable);

    long countAllByTopicId(int topicId);
}
