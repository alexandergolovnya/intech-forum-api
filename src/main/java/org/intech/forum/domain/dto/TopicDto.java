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
public class TopicDto {

    @Null(groups = OnCreate.class, message = "On topic creating id should be null")
    @NotNull(groups = OnUpdate.class, message = "On topic editing id couldn't be null")
    private Integer id;

    @NotBlank(message = "Topic title may not be blank")
    private String title;

    @NotNull(groups = OnUpdate.class, message = "On topic editing description couldn't be null")
    private String description;

    @Null(groups = OnCreate.class,
            message = "Topic filed 'lastMessageDateTime' couldn't be assigned directly on creation, it should be null")
    @Null(groups = OnUpdate.class, message = "Topic filed 'lastMessageDateTime' couldn't be null on editing")
    private LocalDateTime lastMessageDateTime;
}
