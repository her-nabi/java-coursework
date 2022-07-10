delete from content_page;
delete from page;
delete from content;
delete from users;
delete from role;

insert into content(id, data, state)
values ('00000000-0000-0000-0000-000000000000',
        '{"header": "Header 0", "backgroundImg": "0.jpeq","text": "text 0" }',
        'DRAFT'),
       ('10000000-0000-0000-0000-000000000000',
        '{"header": "Header 1", "backgroundImg": "1.jpeq","text": "text 1" }',
        'DRAFT'),
       ('20000000-0000-0000-0000-000000000000',
        '{"header": "Header 2", "backgroundImg": "2.jpeq","text": "text 2" }',
        'DRAFT');
insert into page(id, name)
values ('00000000-0000-0000-0000-000000000000',
        'HOME_PAGE'),
       ('00000000-0000-0000-0000-100000000000',
        'OK_PAGE'),
       ('00000000-0000-0000-0000-200000000000',
        'MAIN_PAGE');
insert into content_page(page_id, content_id)
values ('00000000-0000-0000-0000-000000000000',
        '00000000-0000-0000-0000-000000000000'),
       ('00000000-0000-0000-0000-000000000000',
        '10000000-0000-0000-0000-000000000000'),
       ('00000000-0000-0000-0000-000000000000',
        '20000000-0000-0000-0000-000000000000'),
       ('00000000-0000-0000-0000-100000000000',
        '10000000-0000-0000-0000-000000000000'),
       ('00000000-0000-0000-0000-200000000000',
        '10000000-0000-0000-0000-000000000000');

insert into role (id, name)
values ('e7ac64fa-5063-47f4-95e9-8ede21fa1059', 'ROLE_ADMIN'),
       ('ff7f16a7-98f3-4c88-b9e0-c0f7ab6e8522', 'ROLE_USER');

insert into users (id, login, password, role_id)
values ('a6ad6141-4bc7-46c0-ab5c-66489b4ca334',
        'admin',
        '$2y$10$pdwvB4wov5x7eOJ.3NtybO9ukH2hTf5CQ3XEE/zmNxcEZOAOVCDHm',
        'e7ac64fa-5063-47f4-95e9-8ede21fa1059'),
       ('ab3fb9c7-bcf6-41fd-8ac1-18e12f09fada',
        'user',
        '$2y$10$wI022LxsKfBrl6nTf/dt4ethnF4KFUSBsua.b.UwYMiagjSSVqIT6',
        'ff7f16a7-98f3-4c88-b9e0-c0f7ab6e8522');

