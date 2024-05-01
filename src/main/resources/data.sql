INSERT INTO
    `application_user` (id, username, hashed_password, version,first_name,last_name)
SELECT
    1,
    'admin',
    '$2a$10$L13sLRe8LwyQMOYrfWM4bO6dZ/nWuWqlMGgrnPheYC.qa0YlfxiPS',
    1,
    'Administrator',
    'Administrator'
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            application_user
        WHERE
            id = 1
    );

INSERT INTO
    user_roles (user_id, roles)
SELECT
    1,
    'ADMIN'
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            user_roles
        WHERE
            user_id = 1
    );