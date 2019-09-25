package org.intech.forum.domain.dto;

import lombok.Data;
import org.intech.forum.validation.marker.OnCreate;
import org.intech.forum.validation.marker.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class TopicMessageDto {

    @Null(groups = OnCreate.class, message = "On topic message creating id should be null")
    @NotNull(groups = OnUpdate.class, message = "On topic message editing id couldn't be null")
    private Integer id;

    @NotBlank(message = "Topic message title may not be blank")
    private String messageTitle;

    @NotBlank(message = "Topic message body may not be blank")
    private String messageBody;

    @NotNull(message = "Topic message field 'account id' may not be null")
    private Integer accountId;

    @NotNull(groups = OnUpdate.class, message = "Topic message field 'account full name' may not be null on update")
    private String accountFullName;

    @NotNull(message = "Topic message field 'topic id' may not be null")
    private Integer topicId;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
