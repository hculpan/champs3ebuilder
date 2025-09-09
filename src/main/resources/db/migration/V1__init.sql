-- Users & Roles
create table users (
  id            bigint not null auto_increment primary key,
  email         varchar(255) not null unique,
  password_hash varchar(255) not null,
  enabled       boolean not null default true,
  verified_at   timestamp null,
  created_at    timestamp not null default current_timestamp,
  updated_at    timestamp not null default current_timestamp
);

create table roles (
  id   bigint not null auto_increment primary key,
  name varchar(64) not null unique
);

create table users_roles (
  user_id bigint not null,
  role_id bigint not null,
  primary key (user_id, role_id),
  constraint fk_ur_user foreign key (user_id) references users(id),
  constraint fk_ur_role foreign key (role_id) references roles(id)
);

-- Email verification tokens (for registration/verify flow)
create table email_verification_tokens (
  id          bigint not null auto_increment primary key,
  user_id     bigint not null,
  token       varchar(64) not null unique,
  expires_at  timestamp not null,
  consumed_at timestamp null,
  created_at  timestamp not null default current_timestamp,
  constraint fk_evt_user foreign key (user_id) references users(id)
);

create index idx_evt_user on email_verification_tokens(user_id);
create index idx_evt_token on email_verification_tokens(token);

-- Domain starter: Characters (expand later)
create table characters (
  id          bigint not null auto_increment primary key,
  user_id     bigint not null,
  name        varchar(128) not null,
  status      varchar(32) not null default 'DRAFT',
  created_at  timestamp not null default current_timestamp,
  updated_at  timestamp not null default current_timestamp,
  constraint fk_char_user foreign key (user_id) references users(id)
);

create index idx_char_user on characters(user_id);
create index idx_char_name on characters(name);