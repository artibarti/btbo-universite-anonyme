/***********************************************************************************************/
/***********************************************************************************************/
/****         ***       *** ********************************************************************/
/****  ********** ***** *** ********************************************************************/
/****         *** ***** *** ********************************************************************/
/***********  *** ***** *** ********************************************************************/
/****         ***        **      ***************************************************************/
/************************ ***************************************************** 2018.10.28 *****/
/***********************************************************************************************/

/*******************************************************************************************************/
/*** warning: do not run this script if u have important data in the database *******************************/
/*******************************************************************************************************/


/* clear tables */
DELETE FROM `session`;
DELETE FROM `comment`;
DELETE FROM `course`;
DELETE FROM `question`;
DELETE FROM `admin`;
DELETE FROM `user`;
DELETE FROM `permissions`;
DELETE FROM `rating`;
DELETE FROM `anon_user`;
COMMIT;

/* add users */
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (101,"hashedpwd","kistetu123@gmail.com","Kiss Tetu");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (102,"hashedpwd","adamkovacs@gmail.com","Tibódi Purk");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (103,"hashedpwd","kisskarola@gmail.com","Rejszting Ádám");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (104,"hashedpwd","nemazalany@gmail.com","Horvát Lót");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (105,"hashedpwd","deftones34@gmail.com","Kovács Aranka");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (106,"hashedpwd","kisfiu@gmail.com","Kiss Fia");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (107,"hashedpwd","kislany@gmail.com","Kiss Lany");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (108,"hashedpwd","anya@gmail.com","Anya");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (109,"hashedpwd","Lanadelraj@gmail.com","Hesteg Tchaj");
INSERT INTO `user`(`id`, `double_hashed_password`, `email`, `name`) VALUES (110,"hashedpwd","vonattamas@gmail.com","Vonat Tamás");

/* add anon user */
INSERT INTO `anon_user`(`id`, `anon_name`, `hashed_password`) VALUES (0, "anonymous", "hashdpwd");

/* add courses */
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (101, "A4Bf1", "Kalkulus 1", 101);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (102, "FFe56", "Kalkulus 2", 101);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (103, "AABBC", "Dimat", 102);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (104, "1433S", "Python", 102);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (105, "a9H4a", "XML", 102);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (106, "AcBD3", "RFT", 103);
INSERT INTO `course`(`id`, `invite_code`, `name`, `owner_id`) VALUES (107, "Ssq23", "Automataelméleti alkalmazások", 104);

/* add sessions */
INSERT INTO `session`(`id`, `counter`, `is_active`, `course_id`) VALUES (101, 10, true, 101);
INSERT INTO `session`(`id`, `counter`, `is_active`, `course_id`) VALUES (102, 20, false, 101);

/* add permissions */
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (101, 123456);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (102, 234115);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (103, 102231);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (104, 123);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (105, 9867);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (106, 334);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (107, 56);
INSERT INTO `permissions`(`id`, `permission_store`) VALUES (108, 156);


/* add admins */
INSERT INTO `admin`(`id`, `course_id`, `permissions_id`, `user_id`) VALUES (101, 1001, 102, 101);
INSERT INTO `admin`(`id`, `course_id`, `permissions_id`, `user_id`) VALUES (102, 1002, 102, 101);
INSERT INTO `admin`(`id`, `course_id`, `permissions_id`, `user_id`) VALUES (103, 1003, 105, 102);

/* add comments */
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (101, "Lol", 0, 101);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (102, "idk man", 0, 101);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (103, "What does x stand for?", 0, 102);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (104, "still darkness", 0, 102);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (105, "i just wanna go home", 0, 103);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (106, "thats kinda interesting man", 0, 103);
INSERT INTO `comment`(`id`, `message`, `anon_user_id`, `user_id`) VALUES (107, "this girl hot af", 0, 101);

/* add ratings */
INSERT INTO `rating`(`id`, `value`, `anon_user_id`, `user_id`) VALUES (101, 5, 0, 101);
INSERT INTO `rating`(`id`, `value`, `anon_user_id`, `user_id`) VALUES (102, 1, 0, 102);
INSERT INTO `rating`(`id`, `value`, `anon_user_id`, `user_id`) VALUES (103, 2, 0, 103);
INSERT INTO `rating`(`id`, `value`, `anon_user_id`, `user_id`) VALUES (104, 4, 0, 101);
INSERT INTO `rating`(`id`, `value`, `anon_user_id`, `user_id`) VALUES (105, 5, 0, 109);

/* add questions */

INSERT INTO `question`(`id`, `answer`, `message`, `rating`, `time_stamp`, `anon_user_id`, `session_id`) 
  VALUES (101, "baby dont hurt me", "What is love?", 30, CURRENT_TIMESTAMP, 0, 101);

COMMIT;
