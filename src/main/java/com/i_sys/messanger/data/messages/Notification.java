package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = "notification", name = "tr_notification")
public class Notification extends BaseEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationView> notificationViews = new ArrayList<>();
}
