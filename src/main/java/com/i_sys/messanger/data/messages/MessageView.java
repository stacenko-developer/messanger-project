package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.users.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "message_view")
public class MessageView extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;
}
