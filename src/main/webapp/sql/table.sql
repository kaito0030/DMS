CREATE TABLE users (
    user_name VARCHAR(50) PRIMARY KEY,
    real_name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE documents (
    document_id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publish_year INTEGER,
    abstract_text TEXT,
    pdf_path VARCHAR(255)
);

CREATE TABLE view_histories (
    history_id SERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    document_id VARCHAR(50) NOT NULL,
    viewed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_name) REFERENCES users(user_name)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    FOREIGN KEY (document_id) REFERENCES documents(document_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE document_permissions (
    user_name VARCHAR(50) NOT NULL,
    document_id VARCHAR(50) NOT NULL,
    is_allowed BOOLEAN NOT NULL DEFAULT TRUE,

    PRIMARY KEY (user_name, document_id),

    FOREIGN KEY (user_name) REFERENCES users(user_name)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    FOREIGN KEY (document_id) REFERENCES documents(document_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

alter table users add is_admin boolean default false;
ALTER TABLE document_permissions
ALTER COLUMN is_allowed
SET DEFAULT FALSE;

drop table document_permissions;

CREATE TABLE document_permissions (
    user_name VARCHAR(50) NOT NULL,
    document_id VARCHAR(50) NOT NULL,
    

    PRIMARY KEY (user_name, document_id),

    FOREIGN KEY (user_name) REFERENCES users(user_name)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    FOREIGN KEY (document_id) REFERENCES documents(document_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
ALTER TABLE users
ALTER COLUMN password TYPE VARCHAR(64);
drop table view_histories;