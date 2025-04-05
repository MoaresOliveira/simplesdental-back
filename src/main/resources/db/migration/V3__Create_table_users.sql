CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
   password VARCHAR(255) NOT NULL,
   role VARCHAR(5) NOT NULL CHECK (role IN ('ADMIN', 'USER'))
);