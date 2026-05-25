CREATE DATABASE IF NOT EXISTS db_controledeestoque
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE db_controledeestoque;

CREATE TABLE IF NOT EXISTS tb_usuarios (
  id_cadastro INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(150) NULL,
  senha VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_cadastro),
  UNIQUE KEY uk_tb_usuarios_username (username),
  UNIQUE KEY uk_tb_usuarios_email (email)
);

CREATE TABLE IF NOT EXISTS tb_categoria (
  id INT NOT NULL,
  nome VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_tb_categoria_nome (nome)
);

CREATE TABLE IF NOT EXISTS tb_produto (
  id INT NOT NULL,
  nome VARCHAR(150) NOT NULL,
  preco DOUBLE NOT NULL,
  quantidade INT NOT NULL,
  min INT NOT NULL,
  max INT NOT NULL,
  unidade VARCHAR(50) NOT NULL,
  categoria_id INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_tb_produto_nome (nome),
  KEY fk_tb_produto_categoria_idx (categoria_id),
  CONSTRAINT fk_tb_produto_categoria
    FOREIGN KEY (categoria_id)
    REFERENCES tb_categoria (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);
