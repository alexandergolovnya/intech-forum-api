package org.intech.forum.domain.dto;

import lombok.Data;
import org.intech.forum.domain.entity.Account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class TopicMessageDto {

    private int id;

    @NotBlank(message = "Topic message title may not be blank")
    private String messageTitle;

    @NotBlank(message = "Topic message body may not be blank")
    private String messageBody;

    @NotNull(message = "Topic account may not be null")
    private Account account;
}
