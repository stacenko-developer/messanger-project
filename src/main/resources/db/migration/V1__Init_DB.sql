create table notification.tr_notification (
    id UUID default gen_random_uuid() not null,
    text varchar(255) not null, primary key (id),
    sender_id UUID default gen_random_uuid() not null
);

create table notification.tr_notification_view (
    id UUID default gen_random_uuid() not null,
    is_read boolean not null,
    notification_id UUID default gen_random_uuid() not null,
    user_id UUID default gen_random_uuid() not null,
    primary key (id)
);

create table notification.tr_user (
    id UUID default gen_random_uuid() not null,
    external_id UUID not null,
    primary key (id)
);

alter table if exists notification.tr_notification
    add constraint tr_notification_tr_user_fk
    foreign key (sender_id) references notification.tr_user;

alter table if exists notification.tr_notification_view
    add constraint tr_notification_view_tr_notification_fk
    foreign key (notification_id) references notification.tr_notification;

alter table if exists notification.tr_notification_view
    add constraint tr_notification_view_tr_user_fk
    foreign key (user_id) references notification.tr_user;