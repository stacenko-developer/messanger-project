package com.i_sys.messanger.data.users;

import com.i_sys.messanger.data.BaseEntity;
import com.i_sys.messanger.data.messages.Message;
import com.i_sys.messanger.data.messages.MessageView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "login", nullable = false)
    private String login;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true
        , fetch = FetchType.EAGER)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,
        fetch = FetchType.EAGER)
    private List<MessageView> messageViews = new ArrayList<>();

    public User(String login) {
        this.login = login;
    }

    public User() {

    }
}
