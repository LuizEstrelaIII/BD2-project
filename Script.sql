CREATE DATABASE IF NOT EXISTS empresa; -- ve com os cara esse "if not"
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


