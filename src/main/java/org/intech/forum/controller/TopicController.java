package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.TopicDto;
import org.intech.forum.service.topic.TopicService;
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
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @Validated(OnCreate.class)
    @PostMapping
    public TopicDto createTopic(@Valid @RequestBody TopicDto dto) {
        return topicService.createTopic(dto);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public TopicDto editTopic(@PathVariable int id, @Valid @RequestBody TopicDto dto) {
        return topicService.editTopic(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable int id) {
        topicService.deleteTopic(id);
    }

    @GetMapping("/{id}")
    public TopicDto getTopic(@PathVariable int id) {
        return topicService.getTopic(id);
    }

    @GetMapping("/id/{title}")
    public TopicDto getTopicByTitle(@PathVariable String title) {
        return topicService.getTopicByTitle(title);
    }

    @GetMapping
    public List<TopicDto> getAllOAuthClients() {
        return topicService.getAllTopics();
    }

    @GetMapping("/page")
    public Page<TopicDto> getAllTopicsSortedByLastMessageDate(int page, int size) {
        return topicService.getAllTopicsSortedByLastMessageDate(page, size);
    }
}

