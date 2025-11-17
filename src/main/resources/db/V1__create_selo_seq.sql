CREATE TABLE selo_seq (
    id serial PRIMARY KEY,
    value bigint
)

INSERT INTO selo_seq (
value
) VALUES (0)
    ON CONFLICT DO NOTHING;