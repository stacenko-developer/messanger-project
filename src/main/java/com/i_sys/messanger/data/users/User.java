package com.i_sys.messanger.data.users;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.messages.Notification;
import com.i_sys.messanger.data.messages.NotificationView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(schema = "notification", name = "tr_user")
public class User extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private UUID externalId;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationView> notificationViews = new ArrayList<>();
}
