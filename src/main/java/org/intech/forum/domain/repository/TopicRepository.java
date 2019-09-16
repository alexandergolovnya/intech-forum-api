package org.intech.forum.domain.repository;

import org.intech.forum.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    Optional<Topic> findByTitle(String title);
}
