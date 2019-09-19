package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.TopicMessageDto;
import org.intech.forum.service.topic.TopicMessageService;
import org.intech.forum.validation.OnCreate;
import org.intech.forum.validation.OnUpdate;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/18
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/topic-messages")
public class TopicMessageController {

    private final TopicMessageService topicMessageService;

    @Validated(OnCreate.class)
    @PostMapping
    public TopicMessageDto createTopicMessage(@Valid @RequestBody TopicMessageDto dto) {
        return topicMessageService.createTopicMessage(dto);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public TopicMessageDto editTopicMessage(@PathVariable int id, @Valid @RequestBody TopicMessageDto dto) {
        return topicMessageService.editTopicMessage(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTopicMessage(@PathVariable int id) {
        topicMessageService.deleteTopicMessage(id);
    }

    @GetMapping("/{id}")
    public TopicMessageDto getTopicMessage(@PathVariable int id) {
        return topicMessageService.getTopicMessage(id);
    }

    @GetMapping("/id/{title}")
    public TopicMessageDto getTopicMessageByTitle(@PathVariable String title) {
        return topicMessageService.getTopicMessageByTitle(title);
    }

    @GetMapping
    public List<TopicMessageDto> getAllOAuthClients() {
        return topicMessageService.getAllTopicMessages();
    }

    @GetMapping("/page")
    public Page<TopicMessageDto> getAllTopicMessagesOrderByUpdateDateDesc(@RequestParam int page, @RequestParam int size) {
        return topicMessageService.getAllTopicMessagesOrderByUpdateDateDesc(page, size);
    }
}

