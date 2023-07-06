create table messenger.message (
    id UUID default gen_random_uuid() not null,
    text varchar(255) not null, primary key (id),
    sender_id UUID default gen_random_uuid() not null
);

create table messenger.message_view (
    id UUID default gen_random_uuid() not null,
    is_read boolean not null,
    message_id UUID default gen_random_uuid() not null,
    user_id UUID default gen_random_uuid() not null,
    primary key (id)
);

create table messenger.users (
    id UUID default gen_random_uuid() not null,
    login varchar(255) not null,
    primary key (id)
);

alter table if exists messenger.message
    add constraint message_user_fk
    foreign key (sender_id) references messenger.users;

alter table if exists messenger.message_view
    add constraint message_view_message_fk
    foreign key (message_id) references messenger.message;

alter table if exists messenger.message_view
    add constraint message_view_user_fk
    foreign key (user_id) references messenger.users;