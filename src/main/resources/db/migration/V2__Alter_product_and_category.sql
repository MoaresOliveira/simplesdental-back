-- Alterando tabela 'categories'
ALTER TABLE categories
ALTER COLUMN name TYPE VARCHAR(100);

ALTER TABLE categories
ALTER COLUMN description TYPE VARCHAR(255);

-- Alterando tabela 'products'
ALTER TABLE products
ALTER COLUMN name TYPE VARCHAR(100);

-- Validar se o nome do produto não está vazio
ALTER TABLE products
ADD CONSTRAINT product_name_not_empty CHECK (TRIM(name) <> '');

ALTER TABLE products
ALTER COLUMN description TYPE VARCHAR(255);

-- Validar se o preço do produto é maior que zero
ALTER TABLE products
ADD CONSTRAINT price_greater_than_zero CHECK ( price > 0 );

ALTER TABLE products
ALTER COLUMN category_id SET NOT NULL;