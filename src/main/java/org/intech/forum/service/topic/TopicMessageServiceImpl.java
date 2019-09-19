package org.intech.forum.service.topic;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.TopicMessageDto;
import org.intech.forum.domain.entity.Account;
import org.intech.forum.domain.entity.Topic;
import org.intech.forum.domain.entity.TopicMessage;
import org.intech.forum.domain.repository.AccountRepository;
import org.intech.forum.domain.repository.TopicMessageRepository;
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
public class TopicMessageServiceImpl implements TopicMessageService {

    private final TopicMessageRepository topicMessageRepository;
    private final TopicService topicService;
    private final TopicRepository topicRepository;
    private final AccountRepository accountRepository;

    @Override
    public TopicMessageDto createTopicMessage(TopicMessageDto dto) {
        checkTopicMessageTitleForUniqueness(dto);

        final TopicMessage topicMessage = map(dto, TopicMessage.class);

        final Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("Topic with such id doesn't exist"));
        final Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account with such id doesn't exist"));

        topicMessage.setTopic(topic);
        topicMessage.setAccount(account);
        topicMessageRepository.save(topicMessage);

        topicService.updateTopicLastMessageDate(topic, topicMessage.getCreateDate());

        TopicMessageDto dtoToReturn = map(topicMessage, TopicMessageDto.class);
        dtoToReturn.setAccountId(account.getId());
        dtoToReturn.setTopicId(topic.getId());
        dtoToReturn.setCreateDate(topicMessage.getCreateDate());
        dtoToReturn.setUpdateDate(topicMessage.getUpdateDate());

        return dtoToReturn;
    }

    @Override
    public TopicMessageDto editTopicMessage(int id, TopicMessageDto dto) {
        final Optional<TopicMessage> messageToEdit = topicMessageRepository.findById(id);

        if (messageToEdit.isPresent()) {
            TopicMessage topicMessage = messageToEdit.get();
            LocalDateTime createDate = topicMessage.getCreateDate();

            // check title for uniqueness if it was edited
            if (!dto.getMessageTitle().equals(topicMessage.getMessageTitle())) {
                checkTopicMessageTitleForUniqueness(dto);
            }

            topicMessage = map(dto, TopicMessage.class);

            final Topic topic = topicRepository.findById(dto.getTopicId())
                    .orElseThrow(IllegalArgumentException::new);
            final Account account = accountRepository.findById(dto.getAccountId())
                    .orElseThrow(IllegalArgumentException::new);

            topicMessage.setTopic(topic);
            topicMessage.setAccount(account);
            topicMessage.setCreateDate(createDate);

            final TopicMessage editedMessage = topicMessageRepository.save(topicMessage);

            topicService.updateTopicLastMessageDate(topic, editedMessage.getUpdateDate());

            TopicMessageDto dtoToReturn = map(editedMessage, TopicMessageDto.class);
            dtoToReturn.setAccountId(account.getId());
            dtoToReturn.setTopicId(topic.getId());

            return dtoToReturn;

        } else throw new IllegalArgumentException("Topic message with such id doesn't exists.");
    }

    @Override
    public void deleteTopicMessage(int id) {
        if (topicMessageRepository.existsById(id)) {

            topicMessageRepository.deleteById(id);

        } else throw new IllegalArgumentException("Topic message with such id doesn't exists");
    }

    @Override
    public TopicMessageDto getTopicMessage(int id) {
        final Optional<TopicMessage> topicMessage = topicMessageRepository.findById(id);
        if (topicMessage.isPresent()) {
            return map(topicMessage.get(), TopicMessageDto.class);
        } else throw new IllegalArgumentException("Topic message with such id doesn't exists");
    }

    @Override
    public TopicMessageDto getTopicMessageByTitle(String title) {
        final Optional<TopicMessage> topicMessage = topicMessageRepository.findByMessageTitle(title);
        if (topicMessage.isPresent()) {
            return map(topicMessage.get(), TopicMessageDto.class);
        } else throw new IllegalArgumentException("Topic message with such title doesn't exists");
    }

    @Override
    public List<TopicMessageDto> getAllTopicMessages() {
        return mapAll(topicMessageRepository.findAll(), TopicMessageDto.class);
    }

    @Override
    public Page<TopicMessageDto> getAllTopicMessagesOrderByUpdateDateDesc(int page, int size) {
        Pageable sortedByUpdateDate = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        Page<TopicMessage> topicMessagePage = topicMessageRepository.findAll(sortedByUpdateDate);
        return new PageImpl<>(mapAll(topicMessagePage.getContent(), TopicMessageDto.class));
    }

    private void checkTopicMessageTitleForUniqueness(TopicMessageDto dto) {
        if (!isEmpty(dto.getMessageTitle())) {
            final Optional<TopicMessage> topicMessage = topicMessageRepository.findByMessageTitle(dto.getMessageTitle());
            if (topicMessage.isPresent()) {
                throw new IllegalArgumentException("Topic message with such title already exists.");
            }
        }
    }
}
