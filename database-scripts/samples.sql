if exist (
    select 1 from information_schema.tables
    where table_name = 'meeting')