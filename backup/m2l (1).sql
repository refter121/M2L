-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 25 sep. 2024 à 12:41
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `m2l`
--

-- --------------------------------------------------------

--
-- Structure de la table `appartient`
--

CREATE TABLE `appartient` (
  `IDEQUIPE` int NOT NULL,
  `IDPERSONNE` int NOT NULL,
  PRIMARY KEY (`IDEQUIPE`,`IDPERSONNE`)
);

--
-- Déchargement des données de la table `appartient`
--

INSERT INTO `appartient` (`IDEQUIPE`, `IDPERSONNE`) VALUES
(14, 1),
(14, 2),
(14, 11),
(14, 12),
(14, 37),
(47, 12),
(47, 49),
(47, 50),
(47, 51),
(47, 53);

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--


CREATE TABLE `candidat` (
  `IDCANDIDAT` int NOT NULL,
  PRIMARY KEY (`IDCANDIDAT`)
);

--
-- Déchargement des données de la table `candidat`
--

INSERT INTO `candidat` (`IDCANDIDAT`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20),
(21),
(22),
(23),
(24),
(25),
(26),
(27),
(28),
(29),
(30),
(31),
(32),
(33),
(34),
(35),
(36),
(37),
(38),
(39),
(40),
(41),
(42),
(43),
(44),
(45),
(46),
(47),
(48),
(49),
(50),
(51),
(52),
(53);

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--


CREATE TABLE `competition` (
  `IDCOMPETITION` int NOT NULL,
  `INTITULECOMP` text NOT NULL,
  `DATECOMP` text NOT NULL,
  `ENEQUIPE` tinyint(1) NOT NULL,
  PRIMARY KEY (`IDCOMPETITION`)
);

--
-- Déchargement des données de la table `competition`
--

INSERT INTO `competition` (`IDCOMPETITION`, `INTITULECOMP`, `DATECOMP`, `ENEQUIPE`) VALUES
(1, 'meeting amical des fans de valorant de Pre-La-Fontaine', '30/09/2024', 1);

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--


CREATE TABLE `equipe` (
  `IDCANDIDAT` int NOT NULL,
  `IDEQUIPE` int NOT NULL,
  `NOM` text CHARACTER SET utf8mb4,
  PRIMARY KEY (`IDCANDIDAT`,`IDEQUIPE`),
  KEY `HERITAGECANDIDAT_FK` (`IDCANDIDAT`)
);

--
-- Déchargement des données de la table `equipe`
--

INSERT INTO `equipe` (`IDCANDIDAT`, `IDEQUIPE`, `NOM`) VALUES
(14, 14, 'rageuxValorant'),
(47, 47, 'DragoBongo');

-- --------------------------------------------------------

--
-- Structure de la table `participe`
--


CREATE TABLE `participe` (
  `IDCANDIDAT` int NOT NULL,
  `IDCOMPETITION` int NOT NULL,
  PRIMARY KEY (`IDCANDIDAT`,`IDCOMPETITION`),
  KEY `PARTICIPE_FK` (`IDCANDIDAT`),
  KEY `PARTICIPE2_FK` (`IDCOMPETITION`)
);

--
-- Déchargement des données de la table `participe`
--

INSERT INTO `participe` (`IDCANDIDAT`, `IDCOMPETITION`) VALUES
(14, 1),
(47, 1);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--


CREATE TABLE `personne` (
  `IDCANDIDAT` int NOT NULL,
  `IDPERSONNE` int NOT NULL,
  `NOMPERSONE` varchar(20) DEFAULT NULL,
  `PRENOMPERSONNE` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDCANDIDAT`,`IDPERSONNE`),
  KEY `HERITAGECANDIDAT2_FK` (`IDCANDIDAT`)
);

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`IDCANDIDAT`, `IDPERSONNE`, `NOMPERSONE`, `PRENOMPERSONNE`, `EMAIL`) VALUES
(1, 1, 'Aymare', 'jean', 'jeanAymare@mail.mail'),
(11, 11, 'dechevre', 'fromage', 'fromagerie@mail.com'),
(12, 12, 'Michel', 'jean', 'jeanmichel@mail.com'),
(37, 37, 'TheHedgehog', 'Sonic', 'GotagoFast@mail.com'),
(45, 2, 'soul', 'Samantha', 'sam.soul@mail.com'),
(49, 49, 'abonneheure', 'Hal', 'Hal.abonneheure@mail.fr'),
(50, 50, 'ceptlescondition', 'Jacque', 'J.ceptlesconditions@mail.fr'),
(51, 51, 'celere', 'Jacque', 'Jacque.celere@mail.com'),
(52, 52, 'Bienassez', 'David', 'mrbienassez@mail.com'),
(53, 53, 'Kalvin', 'Charles', 'tgegreatestplan@mail.com');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD CONSTRAINT `FK_HERITAGECANDIDAT` FOREIGN KEY (`IDCANDIDAT`) REFERENCES `candidat` (`IDCANDIDAT`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `participe`
--
ALTER TABLE `participe`
  ADD CONSTRAINT `FK_PARTICIPE` FOREIGN KEY (`IDCANDIDAT`) REFERENCES `candidat` (`IDCANDIDAT`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `FK_PARTICIPE2` FOREIGN KEY (`IDCOMPETITION`) REFERENCES `competition` (`IDCOMPETITION`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `FK_HERITAGECANDIDAT2` FOREIGN KEY (`IDCANDIDAT`) REFERENCES `candidat` (`IDCANDIDAT`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
