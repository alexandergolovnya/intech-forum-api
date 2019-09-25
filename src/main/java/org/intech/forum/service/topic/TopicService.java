package org.intech.forum.service.topic;

import org.intech.forum.domain.dto.TopicDto;
import org.intech.forum.domain.entity.Topic;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/18
 */
public interface TopicService {

    TopicDto createTopic(TopicDto topicDto);

    TopicDto editTopic(int id, TopicDto topicDto);

    void deleteTopic(int id);

    TopicDto getTopic(int id);

    TopicDto getTopicByTitle(String title);

    List<TopicDto> getAllTopics();

    Page<TopicDto> getAllTopicsSortedByLastMessageDate(int page, int size);

    long getTopicsCount();

    void updateTopicLastMessageDate(Topic topic, LocalDateTime dateTime);
}
