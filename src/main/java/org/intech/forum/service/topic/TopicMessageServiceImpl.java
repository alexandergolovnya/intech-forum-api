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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.intech.forum.utils.ModelMapperUtils.map;
import static org.intech.forum.utils.ModelMapperUtils.mapAll;
import static org.intech.forum.utils.SecurityUtils.checkUserAccessRightsToThisMethod;

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

        final String accountFullName = String.format("%s %s", account.getFirstName(), account.getLastName());

        topicMessage.setTopic(topic);
        topicMessage.setAccount(account);
        topicMessage.setAccountFullName(accountFullName);
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
    public TopicMessageDto editTopicMessage(int id, TopicMessageDto dto, Principal principal) {
        TopicMessage topicMessage = topicMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic message with such id doesn't exists."));

        final LocalDateTime createDate = topicMessage.getCreateDate();
        final String topicMessageCreatorEmail = topicMessage.getAccount().getEmail();

        // check if current user has access rights to this method
        checkUserAccessRightsToThisMethod(principal, topicMessageCreatorEmail);

        // check title for uniqueness if it was edited
        if (!dto.getMessageTitle().equals(topicMessage.getMessageTitle())) {
            checkTopicMessageTitleForUniqueness(dto);
        }

        topicMessage = map(dto, TopicMessage.class);

        final Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("Topic with such id doesn't exist"));
        final Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account with such id doesn't exist"));

        final String accountFullName = String.format("%s %s", account.getFirstName(), account.getLastName());

        topicMessage.setAccountFullName(accountFullName);
        topicMessage.setTopic(topic);
        topicMessage.setAccount(account);
        topicMessage.setCreateDate(createDate);

        final TopicMessage editedMessage = topicMessageRepository.save(topicMessage);

        topicService.updateTopicLastMessageDate(topic, editedMessage.getUpdateDate());

        TopicMessageDto dtoToReturn = map(editedMessage, TopicMessageDto.class);
        dtoToReturn.setAccountId(account.getId());
        dtoToReturn.setTopicId(topic.getId());

        return dtoToReturn;
    }

    @Override
    public void deleteTopicMessage(int id, Principal principal) {
        TopicMessage topicMessage = topicMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic message with such id doesn't exists."));

        String topicMessageCreatorEmail = topicMessage.getAccount().getEmail();

        // check if current user has access rights to this method
        checkUserAccessRightsToThisMethod(principal, topicMessageCreatorEmail);

        topicMessageRepository.deleteById(id);
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
    public Page<TopicMessageDto> getAllTopicMessagesPageable(int page, int size) {
        Pageable sortedByUpdateDate = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        Page<TopicMessage> topicMessagePage = topicMessageRepository.findAll(sortedByUpdateDate);
        return new PageImpl<>(mapAll(topicMessagePage.getContent(), TopicMessageDto.class));
    }

    @Override
    public Page<TopicMessageDto> getAllTopicMessagesByTopicIdPageable(int topicId, int page, int size) {
        Pageable sortedByUpdateDate = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        Page<TopicMessage> topicMessagePage = topicMessageRepository.findAllByTopicId(topicId, sortedByUpdateDate);
        return new PageImpl<>(mapAll(topicMessagePage.getContent(), TopicMessageDto.class));
    }

    @Override
    public long getTopicMessagesCount() {
        return topicMessageRepository.count();
    }

    @Override
    public long getTopicMessagesCountByTopicId(int topicID) {
        return topicMessageRepository.countAllByTopicId(topicID);
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
