package org.intech.forum.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class TopicDto {

    private int id;

    @NotBlank(message = "Topic title may not be blank")
    private String title;

    private String description;
}
