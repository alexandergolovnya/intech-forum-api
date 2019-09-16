package org.intech.forum.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
@Entity
@Table(name = "topic_messages")
public class TopicMessage {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(nullable = false, unique = true, columnDefinition = "int")
    private int id;

    @Column(nullable = false, unique = true, length = 60)
    private String messageTitle;

    private String messageBody;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
