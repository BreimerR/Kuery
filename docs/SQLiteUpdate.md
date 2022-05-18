# Update

###### Update

```sqlite
CREATE TABLE users
(
    _id  INTEGER PRIMARY KEY UNIQUE NOT NULL,
    name TEXT
);

UPDATE users
SET users.name = 'Breimer'
WHERE _id == 12
```

###### Bulk Update `Syntax Also Applies to MySQLi`

```sqlite
CREATE TABLE timezones
(
    _id         INTEGER PRIMARY KEY UNIQUE NOT NULL,
    time_zone   TEXT,
    location_id INTEGER
);

UPDATE timezones
SET time_zone = CASE location_id
                    WHEN 173567 THEN '-7.000000'
                    WHEN 173568 THEN '-8.000000'
                    WHEN 173569 THEN '-6.000000'
                    WHEN 173570 THEN '-5.000000'
                    WHEN 173571 THEN '-6.000000'
    END
WHERE location_id IN (173567, 173568, 173569, 173570, 173571)
```