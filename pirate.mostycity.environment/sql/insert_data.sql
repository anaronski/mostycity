use mostycity;
insert into sex values(1, 'male');
insert into sex values(2, 'female');

insert into account_type values(1, 'guest');
insert into account_type values(2, 'user');
insert into account_type values(3, 'moderator');
insert into account_type values(4, 'admin');
insert into account_type values(5, 'super admin');

insert into account_status values(1, 'active');
insert into account_status values(2, 'deleted');
insert into account_status values(3, 'inactive');

insert into news_item_status values(1, 'active');
insert into news_item_status values(2, 'deleted');
insert into news_item_status values(3, 'archived');

insert into account value(1, 'Guest', '', 1,  1, '2011.02.17', '2011.02.17', 1);
insert into account value(2, 'Andrei', 'Naronski', 5,  1, '2011.02.17', '2011.02.17', 1);

insert into account_info value(1, null, 1, 'guest@guest.com', null, null, null, null, null);
insert into account_info value(2, null, 1, 'pirateweter@gmail.com', null, null, null, null, null);

insert into user_auth value(2, 'pirate', 'pass123word');