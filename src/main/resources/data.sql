INSERT INTO
    user (
        id,
        username,
        hashed_password,
        version,
        first_name,
        last_name,
        important
    )
SELECT
    1,
    'admin',
    '$2a$10$L13sLRe8LwyQMOYrfWM4bO6dZ/nWuWqlMGgrnPheYC.qa0YlfxiPS',
    1,
    'Administrator',
    'Administrator',
    TRUE
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            user
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

--Default menue
INSERT INTO
    menue (id, name, version, is_favourit)
SELECT
    1,
    'Mittagsmenue',
    1,
    FALSE
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            menue
        WHERE
            id = 1
    );

--Default meals
INSERT INTO
    meal (
        id,
        name,
        version,
        allergene,
        description,
        price,
        picture,
        menue_id
    )
SELECT
    1,
    'Halbes Grillhendel mit Kartoffelsalat',
    1,
    'Enthält Senf/-Erzeugnisse',
    'Halbes Grillhendel mit Kartoffelsalat',
    13.50,
    'https://imgs.search.brave.com/bRY5E4WyyCjviC5z5w5vqO5zQakL_DckLB18sN2G-jk/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/c2VydnVzLmNvbS9z/dG9yYWdlL3JlY2lw/ZS9oYXVwdHNwZWlz/ZS1odWhuLWJyYXRo/ZW5kbC1zb21tZXJn/ZW11ZXNlLmpwZz9p/bXBvbGljeT1yZWNp/cGVfaGVhZA',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            meal
        WHERE
            id = 1
    );

INSERT INTO
    meal (
        id,
        name,
        version,
        allergene,
        description,
        price,
        picture,
        menue_id
    )
SELECT
    2,
    'Wiener Schnitzel',
    1,
    'Enthält glutenhaltige/s Getreide/-Erzeugnisse Weizen Ei/-Erzeugnisse',
    '',
    14.50,
    'https://imgs.search.brave.com/JL5_2WLt034Cp6x37Qk5HfUXCdWqxwz9zrjdY2DGgQw/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTg1/MDk3NDUyL3Bob3Rv/L3NjaG5pdHplbC5q/cGc_cz02MTJ4NjEy/Jnc9MCZrPTIwJmM9/R2s0T3BoQ19oLUJK/b0cxelhFTDVpZVR0/UV81M3FFSVJkdUNG/Rm5VdS1qQT0',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            meal
        WHERE
            id = 2
    );

INSERT INTO
    meal (
        id,
        name,
        version,
        allergene,
        description,
        price,
        picture,
        menue_id
    )
SELECT
    3,
    'Pizza Diavolo',
    1,
    'Enthält glutenhaltige/s Getreide/-Erzeugnisse Weizen Enthält Milch/-Erzeugnisse (laktosehaltig)',
    '',
    11.50,
    'https://imgs.search.brave.com/ArQ8BTINPogwDXCdg3pEwSbxsY5xdmouDkUo0sKCilw/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9hc3Nl/dHMudG1lY29zeXMu/Y29tL2ltYWdlL3Vw/bG9hZC90X3dlYjc2/N3g2MzkvaW1nL3Jl/Y2lwZS9yYXMvQXNz/ZXRzLzM2REZFODBB/LTc3NkEtNDlGNy1C/NEJCLTI3M0MwQTcw/MkJFRS9EZXJpdmF0/ZXMvYjZjM2JjMDYt/NTgxZS00ZGE5LWEz/OGYtOGI2ZDQ3ZDYy/ODkxLmpwZw',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            meal
        WHERE
            id = 3
    );

INSERT INTO
    meal (
        id,
        name,
        version,
        allergene,
        description,
        price,
        picture,
        menue_id
    )
SELECT
    4,
    'Pizza Americano',
    1,
    'Enthält glutenhaltige/s Getreide/-Erzeugnisse Weizen Enthält Milch/-Erzeugnisse (laktosehaltig)',
    '',
    10.50,
    'https://imgs.search.brave.com/J_uiUNwmImvt9k4TPmwBBeWk1Danc2qjE10RA6VMbG8/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9pbWFn/ZXMubGVja2VyLmRl/L2Nob3Jpem8tcGl6/emEtaW50ZXJuYXRp/b25hbGUtZjExMDYz/NjAxLWtvY2hlbi11/bmQtZ2VuaWVzc2Vu/LTAzLTIwMjMsaWQ9/NGJmMjY5MjgsYj1s/ZWNrZXIsdz00NzUs/Y2E9MCwxMS43Mywx/MDAuMDAsODYuNjcs/cm09c2suanBlZw',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            meal
        WHERE
            id = 4
    );

INSERT INTO
    meal (
        id,
        name,
        version,
        allergene,
        description,
        price,
        picture,
        menue_id
    )
SELECT
    5,
    'Insalata Verde',
    1,
    '',
    'Grüner Salat',
    6.50,
    'https://imgs.search.brave.com/XevDMjSiFxTlCYlAAiAU-BCzr7JZRv3LkBN46PReUYE/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9zdGF0/aWMwMS5ueXQuY29t/L2ltYWdlcy8yMDE5/LzEyLzExL2Rpbmlu/Zy8xMW1hZy1lYXQt/MS8xMm1hZy1lYXQt/MS1hcnRpY2xlTGFy/Z2UtdjIucG5nP3dp/ZHRoPTEyODAmcXVh/bGl0eT03NSZhdXRv/PXdlYnA',
    1
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            meal
        WHERE
            id = 5
    );