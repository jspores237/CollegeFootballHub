CREATE TABLE IF NOT EXISTS teams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    market VARCHAR(255),
    alias VARCHAR(255)
    );

INSERT INTO teams (name, market, alias) VALUES
    ('Crimson Tide', 'Alabama', 'Bama'),
    ('Bulldogs', 'Georgia', 'UGA'),
    ('Buckeyes', 'Ohio State', 'OSU'),
    ('Wolverines', 'Michigan', 'UM'),
    ('Tigers', 'Louisiana State', 'LSU'),
    ('Cougars', 'Washington State', 'WST');
