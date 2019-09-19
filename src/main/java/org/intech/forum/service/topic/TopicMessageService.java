package org.intech.forum.service.topic;

import org.intech.forum.domain.dto.TopicMessageDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/18
 */
public interface TopicMessageService {

    TopicMessageDto createTopicMessage(TopicMessageDto topicMessageDto);

    TopicMessageDto editTopicMessage(int id, TopicMessageDto topicMessageDto);

    void deleteTopicMessage(int id);

    TopicMessageDto getTopicMessage(int id);

    TopicMessageDto getTopicMessageByTitle(String title);

    List<TopicMessageDto> getAllTopicMessages();

    Page<TopicMessageDto> getAllTopicMessagesOrderByUpdateDateDesc(int page, int size);
}
