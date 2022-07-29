-- MySQL Workbench Synchronization
-- Generated: 2016-12-04 14:25
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Amanda

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `r2d2estoque` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`empresas` (
  `cnpj` VARCHAR(14) NOT NULL,
  `razao_social` VARCHAR(55) NOT NULL,
  `nome_fantasia` VARCHAR(55) NOT NULL,
  `matriz` TINYINT(1) NOT NULL,
  `endereco` VARCHAR(80) NOT NULL,
  `cidade` VARCHAR(55) NOT NULL,
  `uf` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`cnpj`),
  UNIQUE INDEX `empresa_id_UNIQUE` (`cnpj` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`categorias` (
  `categoria_id` INT(11) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`categoria_id`),
  UNIQUE INDEX `categoria_id_UNIQUE` (`categoria_id` ASC),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`produtos` (
  `produto_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rfid` VARCHAR(8) NOT NULL,
  `descricao` VARCHAR(55) NOT NULL,
  `categoria_id` INT(11) NOT NULL,
  `dt_inventario` DATE NOT NULL,
  `pr_custo` DECIMAL(10,2) NOT NULL,
  `pr_medio` DECIMAL(10,2) NULL DEFAULT NULL,
  `pr_venda` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`produto_id`),
  UNIQUE INDEX `produto_id_UNIQUE` (`produto_id` ASC),
  UNIQUE INDEX `rfid_UNIQUE` (`rfid` ASC),
  INDEX `fk_produtos_categorias1_idx` (`categoria_id` ASC),
  CONSTRAINT `fk_produtos_categorias1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `r2d2estoque`.`categorias` (`categoria_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`usuarios` (
  `usuario_id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `telefone` VARCHAR(11) NOT NULL,
  `sexo` ENUM('M', 'F') NOT NULL,
  `email` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE INDEX `usuario_id_UNIQUE` (`usuario_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`usuarios_conta` (
  `usuario_id` INT(11) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `senha` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`usuario_id`, `username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_usuarios_conta_usuarios1_idx` (`usuario_id` ASC),
  UNIQUE INDEX `usuario_id_UNIQUE` (`usuario_id` ASC),
  CONSTRAINT `fk_usuarios_conta_usuarios1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `r2d2estoque`.`usuarios` (`usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`usuarios_acesso` (
  `usuario_id` INT(11) NOT NULL,
  `usuarios` TINYINT(1) NOT NULL,
  `produtos` TINYINT(1) NOT NULL,
  `categorias` TINYINT(1) NOT NULL,
  `empresas` TINYINT(1) NOT NULL,
  `rfid` TINYINT(1) NOT NULL,
  `entrada` TINYINT(1) NOT NULL,
  `saida` TINYINT(1) NOT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE INDEX `usuario_id_UNIQUE` (`usuario_id` ASC),
  CONSTRAINT `fk_usuarios_acesso_usuarios2`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `r2d2estoque`.`usuarios` (`usuario_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`rfid` (
  `path` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`path`),
  UNIQUE INDEX `path_UNIQUE` (`path` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`estoque` (
  `produto_id` INT(11) NOT NULL,
  `empresa_cnpj` VARCHAR(14) NOT NULL,
  `quantidade` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`produto_id`, `empresa_cnpj`),
  INDEX `fk_estoque_empresas1_idx` (`empresa_cnpj` ASC),
  CONSTRAINT `fk_estoque_produtos1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `r2d2estoque`.`produtos` (`produto_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_estoque_empresas1`
    FOREIGN KEY (`empresa_cnpj`)
    REFERENCES `r2d2estoque`.`empresas` (`cnpj`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`entradas` (
  `entrada_id` INT(11) NOT NULL AUTO_INCREMENT,
  `empresa_cnpj` VARCHAR(14) NOT NULL,
  `data` DATE NOT NULL,
  PRIMARY KEY (`entrada_id`),
  INDEX `fk_entrada_empresas1_idx` (`empresa_cnpj` ASC),
  CONSTRAINT `fk_entrada_empresas1`
    FOREIGN KEY (`empresa_cnpj`)
    REFERENCES `r2d2estoque`.`empresas` (`cnpj`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`saidas` (
  `saida_id` INT(11) NOT NULL AUTO_INCREMENT,
  `empresa_cnpj` VARCHAR(14) NOT NULL,
  `data` DATE NOT NULL,
  PRIMARY KEY (`saida_id`),
  INDEX `fk_saida_empresas1_idx` (`empresa_cnpj` ASC),
  CONSTRAINT `fk_saida_empresas1`
    FOREIGN KEY (`empresa_cnpj`)
    REFERENCES `r2d2estoque`.`empresas` (`cnpj`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`saidas_produtos` (
  `saida_produtos_id` INT(11) NOT NULL AUTO_INCREMENT,
  `saida_id` INT(11) NOT NULL,
  `produto_id` INT(11) NOT NULL,
  `pr_venda` DECIMAL(10,2) NOT NULL,
  `quantidade` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`saida_produtos_id`),
  INDEX `fk_saida_itens_saida1_idx` (`saida_id` ASC),
  INDEX `fk_saida_itens_produtos1_idx` (`produto_id` ASC),
  CONSTRAINT `fk_saida_itens_saida1`
    FOREIGN KEY (`saida_id`)
    REFERENCES `r2d2estoque`.`saidas` (`saida_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_saida_itens_produtos1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `r2d2estoque`.`produtos` (`produto_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `r2d2estoque`.`entradas_produtos` (
  `entrada_produtos_id` INT(11) NOT NULL AUTO_INCREMENT,
  `entrada_id` INT(11) NOT NULL,
  `produto_id` INT(11) NOT NULL,
  `pr_custo` DECIMAL(10,2) NOT NULL,
  `quantidade` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`entrada_produtos_id`),
  INDEX `fk_entrada_itens_entrada1_idx` (`entrada_id` ASC),
  INDEX `fk_entrada_itens_produtos1_idx` (`produto_id` ASC),
  CONSTRAINT `fk_entrada_itens_entrada1`
    FOREIGN KEY (`entrada_id`)
    REFERENCES `r2d2estoque`.`entradas` (`entrada_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_entrada_itens_produtos1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `r2d2estoque`.`produtos` (`produto_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
