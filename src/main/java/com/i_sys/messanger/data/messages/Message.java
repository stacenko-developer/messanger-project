package com.i_sys.messanger.data.messages;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "message")
public class Message extends BaseEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true,
        fetch = FetchType.EAGER)
    private List<MessageView> messageViews = new ArrayList<>();

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
    }

    public Message() {

    }
}
