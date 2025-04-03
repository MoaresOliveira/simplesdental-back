CREATE TABLE categories (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

-- Inserir categorias
INSERT INTO categories (name, description) VALUES 
('Eletrônicos', 'Produtos eletrônicos e gadgets'),
('Informática', 'Produtos para computadores e acessórios'),
('Smartphones', 'Telefones celulares e acessórios'),
('Eletrodomésticos', 'Aparelhos para casa'),
('Móveis', 'Móveis para escritório e casa');

CREATE TABLE products (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       DECIMAL(19, 2) NOT NULL,
    status      BOOLEAN      NOT NULL,
    code        VARCHAR(50),
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- Inserir produtos
INSERT INTO products (name, description, price, status, code, category_id) VALUES 
('Smartphone XYZ', 'Smartphone com 8GB RAM e 128GB de armazenamento', 1299.99, true, 'PROD-001', 3),
('Notebook ABC', 'Notebook com processador i7, 16GB RAM e SSD 512GB', 3999.99, true, 'PROD-002', 2),
('Smart TV 50"', 'Smart TV LED 4K com 50 polegadas', 2499.99, true, 'PROD-003', 1),
('Geladeira Frost Free', 'Geladeira Duplex Frost Free 400L', 3299.99, true, 'PROD-004', 4),
('Mesa de Escritório', 'Mesa para escritório com gavetas', 499.99, true, 'PROD-005', 5),
('Cadeira Ergonômica', 'Cadeira ergonômica para escritório', 899.99, true, 'PROD-006', 5),
('Tablet Premium', 'Tablet com tela de 10 polegadas e 64GB', 1899.99, true, 'PROD-007', 1),
('Monitor 27"', 'Monitor LED Full HD 27 polegadas', 1199.99, true, 'PROD-008', 2),
('Fone de Ouvido Bluetooth', 'Fone de ouvido sem fio com cancelamento de ruído', 399.99, true, 'PROD-009', 1),
('Microondas 30L', 'Forno microondas com 30 litros e múltiplas funções', 599.99, true, 'PROD-010', 4);