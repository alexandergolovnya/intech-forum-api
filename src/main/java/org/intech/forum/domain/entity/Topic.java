package org.intech.forum.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(nullable = false, unique = true, columnDefinition = "int")
    private int id;

    @Column(nullable = false, unique = true, length = 60)
    private String title;

    @Column(length = 4096)
    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastMessageDateTime;
}
