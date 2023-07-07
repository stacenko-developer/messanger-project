package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "notification", name = "tr_notification_view")
public class NotificationView extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;
}
