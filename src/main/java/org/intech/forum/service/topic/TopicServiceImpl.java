package org.intech.forum.service.topic;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.TopicDto;
import org.intech.forum.domain.entity.Topic;
import org.intech.forum.domain.repository.TopicRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.intech.forum.utils.ModelMapperUtils.map;
import static org.intech.forum.utils.ModelMapperUtils.mapAll;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/18
 */
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public TopicDto createTopic(TopicDto topicDto) {
        checkTopicTitleForUniqueness(topicDto);

        final Topic topic = map(topicDto, Topic.class);
        topicRepository.save(topic);

        return map(topic, TopicDto.class);
    }

    @Override
    public TopicDto editTopic(int id, TopicDto dto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic with such id doesn't exists."));

        final LocalDateTime lastMessageDateTime = topic.getLastMessageDateTime();

        // check title for uniqueness if it was edited
        if (!isEmpty(dto.getTitle()) && !dto.getTitle().equals(topic.getTitle())) {
            checkTopicTitleForUniqueness(dto);
        }

        topic = map(dto, Topic.class);
        topic.setLastMessageDateTime(lastMessageDateTime);
        topicRepository.save(topic);

        return map(topic, TopicDto.class);
    }

    @Override
    public void deleteTopic(int id) {
        if (topicRepository.existsById(id)) {

            topicRepository.deleteById(id);

        } else throw new IllegalArgumentException("Topic with such id doesn't exists");
    }

    @Override
    public TopicDto getTopic(int id) {
        final Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            return map(topic.get(), TopicDto.class);
        } else throw new IllegalArgumentException("Topic with such id doesn't exists");
    }

    @Override
    public TopicDto getTopicByTitle(String title) {
        final Optional<Topic> topic = topicRepository.findByTitle(title);
        if (topic.isPresent()) {
            return map(topic.get(), TopicDto.class);
        } else throw new IllegalArgumentException("Topic with title id doesn't exists");
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return mapAll(topicRepository.findAll(), TopicDto.class);
    }

    @Override
    public Page<TopicDto> getAllTopicsSortedByLastMessageDate(int page, int size) {
        Pageable sortedByLastMessageDate = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,
                "lastMessageDateTime"));
        Page<Topic> topicPage = topicRepository.findAll(sortedByLastMessageDate);
        return new PageImpl<>(mapAll(topicPage.getContent(), TopicDto.class));
    }

    @Override
    public long getTopicsCount() {
        return topicRepository.count();
    }

    @Override
    public void updateTopicLastMessageDate(Topic topic, LocalDateTime dateTime) {
        topic.setLastMessageDateTime(dateTime);
        topicRepository.save(topic);
    }

    private void checkTopicTitleForUniqueness(TopicDto topicDto) {
        if (!isEmpty(topicDto.getTitle())) {
            final Optional<Topic> topic = topicRepository.findByTitle(topicDto.getTitle());
            if (topic.isPresent()) {
                throw new IllegalArgumentException("Topic with such title already exists.");
            }
        }
    }
}
