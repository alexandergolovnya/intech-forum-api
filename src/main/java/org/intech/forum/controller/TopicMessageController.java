package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.TopicMessageDto;
import org.intech.forum.service.topic.TopicMessageService;
import org.intech.forum.validation.marker.OnCreate;
import org.intech.forum.validation.marker.OnUpdate;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping
    public TopicMessageDto createTopicMessage(@Valid @RequestBody TopicMessageDto dto) {
        return topicMessageService.createTopicMessage(dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public TopicMessageDto editTopicMessage(@PathVariable int id,
                                            @Valid @RequestBody TopicMessageDto dto, Principal principal) {
        return topicMessageService.editTopicMessage(id, dto, principal);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTopicMessage(@PathVariable int id, Principal principal) {
        topicMessageService.deleteTopicMessage(id, principal);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public TopicMessageDto getTopicMessage(@PathVariable int id) {
        return topicMessageService.getTopicMessage(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/id/{title}")
    public TopicMessageDto getTopicMessageByTitle(@PathVariable String title) {
        return topicMessageService.getTopicMessageByTitle(title);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<TopicMessageDto> getAllTopicMessages() {
        return topicMessageService.getAllTopicMessages();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/page")
    public Page<TopicMessageDto> getAllTopicMessagesOrderByUpdateDateDesc(@RequestParam int page, @RequestParam int size) {
        return topicMessageService.getAllTopicMessagesOrderByUpdateDateDesc(page, size);
    }
}

