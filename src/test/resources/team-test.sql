insert into team (id, name, country,city)
        SELECT random_uuid(), 'team_ #' || x.id, 'RUSSIA', 'CITY'
        FROM generate_series(1,20) AS x(id);