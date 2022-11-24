insert into users(role, creation_date, modification_date, is_deleted, termination_date, login, password)
values ('ROLE_ADMINISTRATOR', current_timestamp, null, false, null, 'admin', '$2a$10$8uEdhARf.z5Va4jBIPvEDuLGFac9NJbceWsFvpSBC/Ar7bkqBSaW6');
insert into users(role, creation_date, modification_date, is_deleted, termination_date, login, password)
values ('ROLE_VIEWER', current_timestamp, null, false, null, 'user', '$2a$10$EpgvzV4wvHghD5ZlpNVpE.EK1oQ0CCrocu9UFcuxD.aLtuPBzEERe');
