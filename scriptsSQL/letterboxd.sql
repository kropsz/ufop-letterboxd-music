CREATE TABLE `usuarios` (
  `Nome` varchar(155) NOT NULL,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
);
-- Criação da tabela "Músicas"
CREATE TABLE `musicas` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Titulo` varchar(100) NOT NULL,
  `Artista` varchar(100) NOT NULL,
  `Estilo` varchar(100) NOT NULL,
  `AnoLançamento` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
  );
  
  CREATE TABLE `reviews` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `MusicaID` int DEFAULT NULL,
  `Username` varchar(45) DEFAULT NULL,
  `Comentario` text,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `MusicaID` (`MusicaID`),
  KEY `Username` (`Username`),
  FOREIGN KEY (`MusicaID`) REFERENCES `musicas` (`ID`),
  FOREIGN KEY (`Username`) REFERENCES `usuarios` (`Username`)
);

CREATE TABLE `playlists` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(255) DEFAULT NULL,
  `Descriacao` mediumtext,
  `Username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `Username` (`Username`),
  FOREIGN KEY (`Username`) REFERENCES `usuarios` (`Username`)
);

CREATE TABLE `musicas_playlists` (
  `PlaylistID` int DEFAULT NULL,
  `MusicaID` int DEFAULT NULL,
  KEY `PlaylistID` (`PlaylistID`),
  KEY `MusicaID` (`MusicaID`),
  FOREIGN KEY (`PlaylistID`) REFERENCES `playlists` (`ID`),
  FOREIGN KEY (`MusicaID`) REFERENCES `musicas` (`ID`)
)
