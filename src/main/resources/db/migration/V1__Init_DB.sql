create table notification.tr_notification (
    id UUID default gen_random_uuid() not null,
    text varchar(255) not null,
    sender varchar(255) not null,
    primary key (id)
);

create table notification.tr_notification_view (
    id UUID default gen_random_uuid() not null,
    is_read boolean not null,
    notification_id UUID not null,
    user_id UUID not null,
    primary key (id)
);

create table notification.tr_user (
    id UUID default gen_random_uuid() not null,
    external_id UUID not null,
    primary key (id)
);

alter table if exists notification.tr_notification_view
    add constraint tr_notification_view_tr_notification_fk
    foreign key (notification_id) references notification.tr_notification;

alter table if exists notification.tr_notification_view
    add constraint tr_notification_view_tr_user_fk
    foreign key (user_id) references notification.tr_user;

comment on table notification.tr_user is 'Пользователи';
comment on column notification.tr_user.id is 'Идентификатор пользователя';
comment on column notification.tr_user.external_id is 'Внешний идентификатор пользователя';

comment on table notification.tr_notification is 'Уведомления';
comment on column notification.tr_notification.id is 'Идентификатор уведомления';
comment on column notification.tr_notification.sender is 'Отправитель уведомления';
comment on column notification.tr_notification.text is 'Текст уведомления';

comment on table notification.tr_notification_view is 'Информация о прочтении уведомлений';
comment on column notification.tr_notification_view.id is 'Идентификатор записи о прочтении уведомлений';
comment on column notification.tr_notification_view.is_read is 'Прочитано ли уведомление';
comment on column notification.tr_notification_view.notification_id is 'Идентификатор уведомления';
comment on column notification.tr_notification_view.user_id is 'Идентификатор пользователя';