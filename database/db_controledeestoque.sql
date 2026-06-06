-- Criacao do banco de dados
CREATE DATABASE IF NOT EXISTS db_controledeestoque;
USE db_controledeestoque;

-- Criacao da tabela de categorias
CREATE TABLE IF NOT EXISTS `tb_categoria` (
  `id` INT NOT NULL,
  `nome` VARCHAR(100) DEFAULT NULL,
  `embalagem` VARCHAR(100) DEFAULT NULL,
  `tamanho` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Criacao da tabela de produtos
CREATE TABLE IF NOT EXISTS `tb_produto` (
  `id` INT NOT NULL,
  `nome` VARCHAR(100) DEFAULT NULL,
  `preco` DOUBLE DEFAULT NULL,
  `quantidade` INT DEFAULT NULL,
  `min` INT DEFAULT NULL,
  `max` INT DEFAULT NULL,
  `unidade` VARCHAR(50) DEFAULT NULL,
  `categoria_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `tb_produto_ibfk_1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `tb_categoria` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Criacao da tabela de usuarios
CREATE TABLE IF NOT EXISTS `tb_usuarios` (
  `id_cadastro` INT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(100) DEFAULT NULL,
  `username` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) DEFAULT NULL,
  `senha` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id_cadastro`),
  UNIQUE KEY `id_cadastro_UNIQUE` (`id_cadastro`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  UNIQUE KEY `uk_tb_usuarios_username` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
