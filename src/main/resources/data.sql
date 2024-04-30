-- insert into
--     application_user (id, username, hashed_password, version)
-- VALUES
--     (
--         1,
--         'admin',
--         '$2a$10$L13sLRe8LwyQMOYrfWM4bO6dZ/nWuWqlMGgrnPheYC.qa0YlfxiPS',
--         1
--     )
-- WHERE
--     NOT EXISTS (
--         SELECT
--             *
--         FROM
--             application_user
--         WHERE
--             id = 1
--     );
INSERT INTO
    `application_user` (id, username, hashed_password, version)
SELECT
    1,
    'admin',
    '$2a$10$L13sLRe8LwyQMOYrfWM4bO6dZ/nWuWqlMGgrnPheYC.qa0YlfxiPS',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            application_user
        WHERE
            id = 1
    );