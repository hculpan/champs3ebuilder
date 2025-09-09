-- password: bcrypt("$2a$10$...") -> fill with your real hash; DO NOT use plaintext here.
insert into users(email, password_hash, enabled, verified_at)
values ('harry@culpan.org', '{bcrypt}$2a$10$VAm0z7qTdP4im5DVxzBAA.1AXay9qQggIc.LJY/DpgvSvUZV9hLt6', true, current_timestamp);

-- grant ROLE_USER
insert into users_roles(user_id, role_id)
select u.id, r.id from users u, roles r
where u.email='harry@culpan.org' and r.name='ROLE_USER';