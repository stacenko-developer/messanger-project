insert into messenger.users (id, login)
    values  ('bc9802a2-6c94-494d-b88d-a7edc25009b3', 'artem'),
            ('bc9802a3-6c94-494d-b88d-a7edc25009b3', 'viktor'),
            ('bc9802a4-6c94-494d-b88d-a7edc25009b3', 'sergey'),
            ('bc9802a5-6c94-494d-b88d-a7edc25009b3', 'alex');

insert into messenger.message (id, text, sender_id)
    values ('bc1802a2-6c94-494d-b88d-a7edc25009b3', 'Hello!',
        'bc9802a2-6c94-494d-b88d-a7edc25009b3'),
            ('bc2802a2-6c94-494d-b88d-a7edc25009b3', 'Hello world!',
        'bc9802a3-6c94-494d-b88d-a7edc25009b3'),
            ('bc3802a2-6c94-494d-b88d-a7edc25009b3', 'Hello Russia!',
        'bc9802a4-6c94-494d-b88d-a7edc25009b3'),
            ('bc4802a2-6c94-494d-b88d-a7edc25009b3', 'Halo!',
        'bc9802a5-6c94-494d-b88d-a7edc25009b3');
