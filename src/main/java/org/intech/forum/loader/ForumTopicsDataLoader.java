package org.intech.forum.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.intech.forum.domain.entity.Account;
import org.intech.forum.domain.entity.Topic;
import org.intech.forum.domain.entity.TopicMessage;
import org.intech.forum.domain.repository.AccountRepository;
import org.intech.forum.domain.repository.TopicMessageRepository;
import org.intech.forum.domain.repository.TopicRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class creates initial data for topic and topic messages
 * if there are no default topic and topic messages at the database
 *
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/19
 */
@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class ForumTopicsDataLoader implements ApplicationRunner {

    private final TopicRepository topicRepository;
    private final TopicMessageRepository topicMessageRepository;
    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (topicRepository.count() == 0) {
            log.info("Creating test data for topic and topic messages");

            for (int i = 1; i <= 10; i++) {
                final Topic topic = new Topic();
                topic.setTitle(String.format("Topic %s", i));
                topic.setDescription("It is a long established fact that a reader will be distracted by the readable " +
                        "content of a page when looking at its layout. The point of using Lorem Ipsum is that " +
                        "it has a more-or-less normal distribution of letters, as opposed to using 'Content here, " +
                        "content here', making it look like readable English.");
                topicRepository.save(topic);
            }

            final Account account = accountRepository.findByEmail("user@user")
                    .orElseThrow(() -> new IllegalArgumentException("User account for test data doesn't exists"));

            final String accountFullName = String.format("%s %s", account.getFirstName(), account.getLastName());

            final List<Topic> topicList = topicRepository.findAll();
            topicList.forEach(topic -> {
                for (int i = 1; i <= 15; i++) {

                    final TopicMessage message = new TopicMessage();
                    message.setMessageTitle(String.format("Message %d for topic %d", i, topic.getId()));
                    message.setMessageBody("Many desktop publishing packages and web page editors now use Lorem " +
                            "Ipsum as their default model text, and a search for 'lorem ipsum' will uncover " +
                            "many web sites still in their infancy.");
                    message.setTopic(topic);
                    message.setAccount(account);
                    message.setAccountFullName(accountFullName);

                    final TopicMessage createdMessage = topicMessageRepository.save(message);

                    topic.setLastMessageDateTime(createdMessage.getUpdateDate());
                    topicRepository.save(topic);
                }
            });
        }
    }
}
