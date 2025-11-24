CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS speciality
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    icon VARCHAR(200) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    version INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS business
(
    id            VARCHAR(255) PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    overview      VARCHAR(255) NOT NULL,
    joined_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS business_service
(
    id          VARCHAR(255) PRIMARY KEY,
    business_id VARCHAR(255) NOT NULL,
    service_id  VARCHAR(255) NOT NULL,
    UNIQUE (business_id, service_id),
    FOREIGN KEY (business_id) REFERENCES business (id),
    FOREIGN KEY (service_id) REFERENCES speciality (id),
    version INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS review
(
    id         VARCHAR(255) PRIMARY KEY,
    review     VARCHAR(255) NOT NULL,
    rating     INT          NOT NULL,
    service_id VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,
    FOREIGN KEY (service_id) REFERENCES speciality (id),
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS address
(
    id         VARCHAR(255) PRIMARY KEY,
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL,
    state      VARCHAR(255) NOT NULL,
    zip        VARCHAR(255) NOT NULL,
    type       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS business_address
(
    id          VARCHAR(255) PRIMARY KEY,
    business_id VARCHAR(255) NOT NULL,
    address_id  VARCHAR(255) NOT NULL,
    UNIQUE (business_id, address_id),
    FOREIGN KEY (business_id) REFERENCES business (id),
    FOREIGN KEY (address_id) REFERENCES address (id),
    version INT NOT NULL DEFAULT 0
);



CREATE TABLE IF NOT EXISTS social_account
(
    username    VARCHAR(100) NOT NULL,
    url         VARCHAR(255) NOT NULL,
    business_id VARCHAR(255)         NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (business_id) REFERENCES business (id),
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS outbox_event
(
    id             VARCHAR(255) PRIMARY KEY,
    aggregate_id   VARCHAR(100) NOT NULL,
    aggregate_type VARCHAR(100) NOT NULL,
    event_type     VARCHAR(100) NOT NULL,
    payload        TEXT         NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_published   BOOLEAN      NOT NULL DEFAULT FALSE,
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS customer
(
    id            VARCHAR(255) PRIMARY KEY,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    birth_date    DATE NOT NULL,
    mobile_number VARCHAR(100) UNIQUE NOT NULL,
    email_address VARCHAR(255) UNIQUE NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS account
(
    id          VARCHAR(255) PRIMARY KEY,
    type        VARCHAR(100) NOT NULL,
    status      VARCHAR(100) NOT NULL DEFAULT 'ACTIVE',
    customer_id VARCHAR(255),
    business_id VARCHAR(255),
    supabase_user_id VARCHAR(255) UNIQUE,
    version INT NOT NULL DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (business_id) REFERENCES business (id)
);

CREATE TABLE IF NOT EXISTS category
(
  id          VARCHAR(255) PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  version INT NOT NULL DEFAULT 0
);

