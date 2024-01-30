CREATE TABLE program (
    id INT AUTO_INCREMENT PRIMARY KEY,
    program_name VARCHAR(30)
);

CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    program_id INTEGER NOT NULL,
    international BOOLEAN,
    program_year INTEGER,
    program_coop BOOLEAN,
    FOREIGN KEY(program_id) REFERENCES program(id)
);

CREATE TABLE app_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
);

CREATE TABLE app_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE app_user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES app_user(id),
    FOREIGN KEY(role_id) REFERENCES app_role(id)
);

CREATE TABLE persistent_logins (
    username varchar(100) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
);


