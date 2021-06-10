CREATE EXTENSION "uuid-ossp";

CREATE TABLE payment
(
    id           uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    status       text        NOT NULL,
    ccy          text        NOT NULL,
    amount       decimal     NOT NULL,
    reference    text        NOT NULL,
    account_id   uuid        NOT NULL,
    payer        jsonb,
    bank_account jsonb,
    create_time  timestamptz NOT NULL,
    last_update  timestamptz NOT NULL,
    version      bigint      NOT NULL DEFAULT 1
);

CREATE TABLE "order"
(
    id          uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    status      text        NOT NULL,
    amount      decimal     NOT NULL,
    metadata    jsonb,
    create_time timestamptz NOT NULL,
    last_update timestamptz NOT NULL,
    version     bigint      NOT NULL DEFAULT 1
);
