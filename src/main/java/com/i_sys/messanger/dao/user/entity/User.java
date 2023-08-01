package com.i_sys.messanger.dao.user.entity;

import com.i_sys.messanger.dao.BaseEntity;
import com.i_sys.messanger.dao.notification.entity.NotificationView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Comment("Пользователи")
@Table(schema = "notification", name = "tr_user")
public class User extends BaseEntity {

    @Column(name = "external_id", nullable = false, unique = true)
    @Comment("Внешний идентификатор пользователя")
    private UUID externalId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationView> notificationViews = new ArrayList<>();
}
