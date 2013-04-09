delimiter ;
CREATE DATABASE `finalassignmentdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(20) DEFAULT NULL,
  `post_code` varchar(10) DEFAULT NULL,
  `post_office` varchar(40) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `street_address` varchar(150) DEFAULT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_role` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_student` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Birthday` datetime DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `ManagerId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK6BCD8A7A2A755214` (`ManagerId`),
  CONSTRAINT `FK6BCD8A7A2A755214` FOREIGN KEY (`ManagerId`) REFERENCES `tbl_student` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_studentrole` (
  `StudentId` bigint(20) NOT NULL,
  `RoleId` bigint(20) NOT NULL,
  PRIMARY KEY (`StudentId`,`RoleId`),
  KEY `FK5CD6B2103FC567D6` (`RoleId`),
  KEY `FK5CD6B210D1185922` (`StudentId`),
  CONSTRAINT `FK5CD6B210D1185922` FOREIGN KEY (`StudentId`) REFERENCES `tbl_student` (`Id`),
  CONSTRAINT `FK5CD6B2103FC567D6` FOREIGN KEY (`RoleId`) REFERENCES `tbl_role` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_location` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `ContactPerson` varchar(255) DEFAULT NULL,
  `Telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_course` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_classoffering` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `EndTime` datetime DEFAULT NULL,
  `StartTime` datetime DEFAULT NULL,
  `CourseId` bigint(20) NOT NULL,
  `InstructorId` bigint(20) DEFAULT NULL,
  `locationId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FKCF1DC29DECE09314` (`locationId`),
  KEY `FKCF1DC29D445C3A20` (`CourseId`),
  KEY `FKCF1DC29D73D8D704` (`InstructorId`),
  CONSTRAINT `FKCF1DC29D73D8D704` FOREIGN KEY (`InstructorId`) REFERENCES `tbl_student` (`Id`),
  CONSTRAINT `FKCF1DC29D445C3A20` FOREIGN KEY (`CourseId`) REFERENCES `tbl_course` (`Id`),
  CONSTRAINT `FKCF1DC29DECE09314` FOREIGN KEY (`locationId`) REFERENCES `tbl_location` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_curriculum` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_curriculumcourse` (
  `CurriculumId` bigint(20) NOT NULL,
  `CourseId` bigint(20) NOT NULL,
  PRIMARY KEY (`CurriculumId`,`CourseId`),
  KEY `FKE5CFDED7445C3A20` (`CourseId`),
  KEY `FKE5CFDED7577CCAE0` (`CurriculumId`),
  CONSTRAINT `FKE5CFDED7577CCAE0` FOREIGN KEY (`CurriculumId`) REFERENCES `tbl_curriculum` (`Id`),
  CONSTRAINT `FKE5CFDED7445C3A20` FOREIGN KEY (`CourseId`) REFERENCES `tbl_course` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_classofferingregistration` (
  `ClassOfferingId` bigint(20) NOT NULL DEFAULT '0',
  `StudentId` bigint(20) NOT NULL DEFAULT '0',
  `EnrolledDate` date DEFAULT NULL,
  PRIMARY KEY (`ClassOfferingId`,`StudentId`),
  KEY `FK5634FBF67707B648` (`ClassOfferingId`),
  KEY `FK5634FBF6D1185922` (`StudentId`),
  CONSTRAINT `FK5634FBF6D1185922` FOREIGN KEY (`StudentId`) REFERENCES `tbl_student` (`Id`),
  CONSTRAINT `FK5634FBF67707B648` FOREIGN KEY (`ClassOfferingId`) REFERENCES `tbl_classoffering` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_curriculumregistration` (
  `CurriculumId` bigint(20) NOT NULL DEFAULT '0',
  `student` bigint(20) NOT NULL DEFAULT '0',
  `EnrolledDate` date DEFAULT NULL,
  PRIMARY KEY (`CurriculumId`,`student`),
  KEY `FK14EDEA75D6AFEEC7` (`student`),
  KEY `FK14EDEA75577CCAE0` (`CurriculumId`),
  CONSTRAINT `FK14EDEA75577CCAE0` FOREIGN KEY (`CurriculumId`) REFERENCES `tbl_curriculum` (`Id`),
  CONSTRAINT `FK14EDEA75D6AFEEC7` FOREIGN KEY (`student`) REFERENCES `tbl_student` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;