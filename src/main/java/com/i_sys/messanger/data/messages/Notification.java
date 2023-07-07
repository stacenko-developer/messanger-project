package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Comment("Уведомления")
@Table(schema = "notification", name = "tr_notification")
public class Notification extends BaseEntity {

    @Column(name = "text", nullable = false)
    @Comment("Текст уведомления")
    private String text;

    @Column(name = "sender", nullable = false)
    @Comment("Отправитель уведомления")
    private String sender;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationView> notificationViews = new ArrayList<>();
}
