package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Comment("Информация о прочтении уведомлений")
@Table(schema = "notification", name = "tr_notification_view")
public class NotificationView extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("Идентификатор пользователя")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    @Comment("Идентификатор уведомления")
    private Notification notification;

    @Column(name = "is_read", nullable = false)
    @Comment("Прочитано ли уведомление")
    private boolean isRead;
}
