DROP DATABASE IF EXISTS empresa;
CREATE DATABASE IF NOT EXISTS empresa; 
USE empresa;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    sexo ENUM('m', 'f', 'o') NOT NULL,
    idade INT,
    nascimento DATE
);

CREATE TABLE clienteespecial (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    sexo ENUM('m', 'f', 'o') NOT NULL,
    idade INT,
    id_cliente INT,
    cashback DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    idade INT,
    sexo ENUM('m', 'f', 'o') NOT NULL,
    cargo ENUM('vendedor', 'gerente', 'CEO') NOT NULL,
    salario DECIMAL(10,2),
    nascimento DATE
);

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    quantidade INT,
    descricao VARCHAR(255),
    valor DECIMAL(10,2)
);

CREATE TABLE venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_vendedor INT,
    id_cliente INT,
    data DATE,
    FOREIGN KEY (id_vendedor) REFERENCES funcionario(id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

-- prof não pediu para criar mas na trigger temos que usar
CREATE TABLE funcionarioespecial (
    id INT PRIMARY KEY,
    nome VARCHAR(100),
    idade INT,
    sexo ENUM('m', 'f', 'o') NOT NULL,
    cargo ENUM('vendedor', 'gerente', 'CEO') NOT NULL,
    salario DECIMAL(10,2),
    nascimento DATE,
    bonus DECIMAL(10,2)
);

-- prof não pediu para criar mas na trigger temos que usar
CREATE TABLE venda_item (
    id_venda INT,
    id_produto INT,
    quantidade INT,
    valor_unitario DECIMAL(10,2),
    PRIMARY KEY (id_venda, id_produto),
    FOREIGN KEY (id_venda) REFERENCES venda(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id)
);

INSERT INTO produto (id, nome, quantidade, descricao, valor) VALUES
('Teclado Gamer', 50, 'Teclado mecânico RGB', 250.00),
('Mouse Óptico', 100, 'Mouse 16000 DPI', 150.00),
('Monitor 24"', 30, 'Monitor Full HD', 800.00),
('Cadeira Gamer', 20, 'Cadeira ergonômica', 1200.00),
('Notebook', 15, 'Notebook i5 8GB 256GB SSD', 3500.00),
('Headset', 60, 'Headset Surround 7.1', 300.00),
('Smartphone', 40, 'Smartphone 128GB', 2000.00),
('Tablet', 25, 'Tablet 10 polegadas', 1300.00),
('Impressora', 10, 'Impressora Multifuncional', 700.00),
('Webcam', 70, 'Webcam Full HD', 200.00),
('HD Externo 1TB', 50, 'HD USB 3.0', 350.00),
('Pen Drive 64GB', 150, 'Pen Drive rápido', 70.00),
('Placa de Vídeo', 8, 'GPU 8GB VRAM', 2500.00),
('Fonte 600W', 20, 'Fonte PFC Ativo', 400.00),
('Memória RAM 8GB', 60, 'DDR4 8GB 2666MHz', 250.00),
('Processador i7', 10, 'Intel Core i7', 1800.00),
('SSD 500GB', 30, 'SSD SATA III 500GB', 450.00),
('Placa Mãe', 15, 'Placa para Intel 10ª Geração', 900.00),
('Gabinete Gamer', 25, 'Gabinete com RGB', 500.00),
('Controle de Xbox', 40, 'Controle sem fio', 350.00);

INSERT INTO funcionario (id, nome, idade, sexo, cargo, salario, nascimento) VALUES
('Ana Silva', 30, 'f', 'vendedor', 2000.00, '1995-02-10'),
('Carlos Souza', 45, 'm', 'gerente', 4000.00, '1980-07-15'),
('João Mendes', 28, 'm', 'vendedor', 2200.00, '1997-11-20'),
('Mariana Costa', 35, 'f', 'CEO', 10000.00, '1989-04-05'),
('Patrícia Lima', 32, 'f', 'vendedor', 2100.00, '1993-09-12');

INSERT INTO cliente (nome, sexo, idade, nascimento) VALUES
('Cliente 01', 'm', 25, '1999-01-01'),
('Cliente 02', 'f', 30, '1994-02-02'),
('Cliente 03', 'o', 22, '2002-03-03'),
('Cliente 04', 'm', 28, '1996-04-04'),
('Cliente 05', 'f', 35, '1989-05-05'),
('Cliente 06', 'o', 20, '2004-06-06'),
('Cliente 07', 'm', 40, '1984-07-07'),
('Cliente 08', 'f', 45, '1979-08-08'),
('Cliente 09', 'o', 50, '1974-09-09'),
('Cliente 10', 'm', 18, '2006-10-10'),
('Cliente 11', 'f', 27, '1997-11-11'),
('Cliente 12', 'o', 33, '1991-12-12'),
('Cliente 13', 'm', 29, '1995-01-13'),
('Cliente 14', 'f', 31, '1993-02-14'),
('Cliente 15', 'o', 24, '2000-03-15'),
('Cliente 16', 'm', 26, '1998-04-16'),
('Cliente 17', 'f', 36, '1988-05-17'),
('Cliente 18', 'o', 21, '2003-06-18'),
('Cliente 19', 'm', 41, '1983-07-19'),
('Cliente 20', 'f', 46, '1978-08-20'),
('Cliente 21', 'o', 48, '1976-09-21'),
('Cliente 22', 'm', 19, '2005-10-22'),
('Cliente 23', 'f', 28, '1996-11-23'),
('Cliente 24', 'o', 34, '1990-12-24'),
('Cliente 25', 'm', 32, '1992-01-25'),
('Cliente 26', 'f', 23, '2001-02-26'),
('Cliente 27', 'o', 38, '1986-03-27'),
('Cliente 28', 'm', 43, '1981-04-28'),
('Cliente 29', 'f', 47, '1977-05-29'),
('Cliente 30', 'o', 18, '2006-06-30'),
('Cliente 31', 'm', 26, '1998-07-31'),
('Cliente 32', 'f', 30, '1994-08-01'),
('Cliente 33', 'o', 22, '2002-09-02'),
('Cliente 34', 'm', 28, '1996-10-03'),
('Cliente 35', 'f', 35, '1989-11-04'),
('Cliente 36', 'o', 20, '2004-12-05'),
('Cliente 37', 'm', 40, '1984-01-06'),
('Cliente 38', 'f', 45, '1979-02-07'),
('Cliente 39', 'o', 50, '1974-03-08'),
('Cliente 40', 'm', 18, '2006-04-09'),
('Cliente 41', 'f', 27, '1997-05-10'),
('Cliente 42', 'o', 33, '1991-06-11'),
('Cliente 43', 'm', 29, '1995-07-12'),
('Cliente 44', 'f', 31, '1993-08-13'),
('Cliente 45', 'o', 24, '2000-09-14'),
('Cliente 46', 'm', 26, '1998-10-15'),
('Cliente 47', 'f', 36, '1988-11-16'),
('Cliente 48', 'o', 21, '2003-12-17'),
('Cliente 49', 'm', 41, '1983-01-18'),
('Cliente 50', 'f', 46, '1978-02-19'),
('Cliente 51', 'o', 48, '1976-03-20'),
('Cliente 52', 'm', 19, '2005-04-21'),
('Cliente 53', 'f', 28, '1996-05-22'),
('Cliente 54', 'o', 34, '1990-06-23'),
('Cliente 55', 'm', 32, '1992-07-24'),
('Cliente 56', 'f', 23, '2001-08-25'),
('Cliente 57', 'o', 38, '1986-09-26'),
('Cliente 58', 'm', 43, '1981-10-27'),
('Cliente 59', 'f', 47, '1977-11-28'),
('Cliente 60', 'o', 18, '2006-12-29'),
('Cliente 61', 'm', 25, '1999-01-30'),
('Cliente 62', 'f', 30, '1994-02-01'),
('Cliente 63', 'o', 22, '2002-03-02'),
('Cliente 64', 'm', 28, '1996-04-03'),
('Cliente 65', 'f', 35, '1989-05-04'),
('Cliente 66', 'o', 20, '2004-06-05'),
('Cliente 67', 'm', 40, '1984-07-06'),
('Cliente 68', 'f', 45, '1979-08-07'),
('Cliente 69', 'o', 50, '1974-09-08'),
('Cliente 70', 'm', 18, '2006-10-09'),
('Cliente 71', 'f', 27, '1997-11-10'),
('Cliente 72', 'o', 33, '1991-12-11'),
('Cliente 73', 'm', 29, '1995-01-12'),
('Cliente 74', 'f', 31, '1993-02-13'),
('Cliente 75', 'o', 24, '2000-03-14'),
('Cliente 76', 'm', 26, '1998-04-15'),
('Cliente 77', 'f', 36, '1988-05-16'),
('Cliente 78', 'o', 21, '2003-06-17'),
('Cliente 79', 'm', 41, '1983-07-18'),
('Cliente 80', 'f', 46, '1978-08-19'),
('Cliente 81', 'o', 48, '1976-09-20'),
('Cliente 82', 'm', 19, '2005-10-21'),
('Cliente 83', 'f', 28, '1996-11-22'),
('Cliente 84', 'o', 34, '1990-12-23'),
('Cliente 85', 'm', 32, '1992-01-24'),
('Cliente 86', 'f', 23, '2001-02-25'),
('Cliente 87', 'o', 38, '1986-03-26'),
('Cliente 88', 'm', 43, '1981-04-27'),
('Cliente 89', 'f', 47, '1977-05-28'),
('Cliente 90', 'o', 18, '2006-06-29'),
('Cliente 91', 'm', 25, '1999-07-30'),
('Cliente 92', 'f', 30, '1994-08-31'),
('Cliente 93', 'o', 22, '2002-09-01'),
('Cliente 94', 'm', 28, '1996-10-02'),
('Cliente 95', 'f', 35, '1989-11-03'),
('Cliente 96', 'o', 20, '2004-12-04'),
('Cliente 97', 'm', 40, '1984-01-05'),
('Cliente 98', 'f', 45, '1979-02-06'),
('Cliente 99', 'o', 50, '1974-03-07'),
('Cliente 100', 'm', 18, '2006-04-08');


DELIMITER //

CREATE TRIGGER vendedor_especial
AFTER INSERT ON venda_item
FOR EACH ROW
BEGIN
    DECLARE total_vendido DECIMAL(10,2);
    DECLARE vendedor_id INT;
    DECLARE msg TEXT;

    -- Obter o id do vendedor
    SELECT id_vendedor INTO vendedor_id
    FROM venda
    WHERE id = NEW.id_venda;

    -- Calcular total vendido pelo vendedor
    SELECT SUM(iv.quantidade * p.valor)
    INTO total_vendido
    FROM item_venda iv
    JOIN venda v ON iv.id_venda = v.id
    JOIN produto p ON iv.id_produto = p.id
    WHERE v.id_vendedor = vendedor_id;

    -- Se total vendido > 1000 e ainda não estiver na tabela especial
    IF total_vendido > 1000.00 THEN
        -- Aqui você pode apenas emitir a mensagem
        SET msg = CONCAT('Bônus salarial total necessário: R$ ', CAST(ROUND(0.05 * total_vendido, 2) AS CHAR));
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER cliente_especial
AFTER INSERT ON venda_item
FOR EACH ROW
BEGIN
    DECLARE total_gasto DECIMAL(10,2);
    DECLARE msg TEXT;
    DECLARE cliente_id INT;

    -- Obter id do cliente da venda atual
    SELECT id_cliente INTO cliente_id
    FROM venda
    WHERE id = NEW.id_venda;

    -- Soma total gasto por esse cliente
    SELECT SUM(iv.quantidade * p.valor)
    INTO total_gasto
    FROM item_venda iv
    JOIN venda v ON iv.id_venda = v.id
    JOIN produto p ON iv.id_produto = p.id
    WHERE v.id_cliente = cliente_id;

    -- Se gastou > 500 e ainda não for especial, insere
    IF total_gasto > 500.00 AND NOT EXISTS (
        SELECT 1 FROM clienteespecial WHERE id_cliente = cliente_id
    ) THEN
        INSERT INTO clienteespecial (nome, sexo, idade, id_cliente, cashback)
        SELECT nome, sexo, idade, id, 0.02 * total_gasto
        FROM cliente
        WHERE id = cliente_id;

        -- Preparar e emitir mensagem
        SET msg = CONCAT('Valor necessário para cashback: R$ ', CAST(ROUND(0.02 * total_gasto, 2) AS CHAR));
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;
END //

DELIMITER ;


DELIMITER $$

CREATE TRIGGER remove_cliente_especial
AFTER UPDATE ON clienteespecial
FOR EACH ROW
BEGIN
    IF NEW.cashback = 0 THEN
        DELETE FROM clienteespecial WHERE id = NEW.id;
    END IF;
END;

DELIMITER ;

CREATE VIEW view_produtos_quantidade AS
SELECT p.nome, p.quantidade
FROM produto p;

CREATE VIEW view_vendas_funcionario AS
SELECT f.nome AS vendedor, COUNT(v.id) AS total_vendas
FROM funcionario f
JOIN venda v ON f.id = v.id_vendedor
GROUP BY f.nome;

CREATE VIEW view_cashback_cliente AS
SELECT c.nome, ce.cashback
FROM cliente c
JOIN clienteespecial ce ON c.id = ce.id_cliente;

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin123';
CREATE USER 'gerente'@'localhost' IDENTIFIED BY 'gerente123';
CREATE USER 'funcionario'@'localhost' IDENTIFIED BY 'funcionario123';

GRANT ALL PRIVILEGES ON empresa.* TO 'admin'@'localhost';
GRANT SELECT, DELETE, UPDATE ON empresa.* TO 'gerente'@'localhost';
GRANT INSERT, SELECT ON empresa.* TO 'funcionario'@'localhost';

FLUSH PRIVILEGES;

