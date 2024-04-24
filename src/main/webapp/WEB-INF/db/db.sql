DROP TABLE Usr;

CREATE TABLE Usr (
	userId	varchar2(30)		NOT NULL,
	pwd	varchar2(100)		NOT NULL,
	salt	varchar2(200)		NOT NULL,
	status	char(1)	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN Usr.status IS '탈퇴 후 30일 지나면 아이디 삭제';

DROP TABLE UserInfo;

CREATE TABLE UserInfo (
	userId	varchar2(30)		NOT NULL,
	name	varchar2(30)		NOT NULL,
	gender	char(1)		NOT NULL,
	email	varchar2(80)		NOT NULL,
	tel	varchar2(50)		NOT NULL,
	postCode	varchar2(32)		NULL,
	addr1	varchar2(80)		NULL,
	addr2	varchar2(80)		NULL,
	birthday	date		NOT NULL,
	regDate	date		NOT NULL,
	projOwnerNum	number(4)	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN UserInfo.projOwnerNum IS '최대 5개만 생성할 수 있음';

DROP TABLE Project;

CREATE TABLE Project (
	projNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	projName	varchar2(60)		NOT NULL,
	inviteCode	varchar2(60)		NULL,
	detailedDescription	varchar2(150)		NULL
);

COMMENT ON COLUMN Project.detailedDescription IS '프로젝트 관련 상세설명';

DROP TABLE UPM;

CREATE TABLE UPM (
	upmNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	role	char(1)	DEFAULT 1	NOT NULL,
	userId	varchar2(30)		NOT NULL,
	projNo	number		NOT NULL
);

COMMENT ON COLUMN UPM.role IS '1(true)면 Owner';

DROP TABLE Board;

CREATE TABLE Board (
	boardNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	boardTitle	varchar2(100)		NOT NULL,
	projNo	number		NOT NULL,
	boardOrder	number	DEFAULT 50000	NOT NULL
);

DROP TABLE DocPage;

CREATE TABLE DocPage (
	docPageNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	docTitle	varchar2(100)		NULL,
	docContent	varchar2(4000)		NULL,
	updateDate	date		NOT NULL,
	projNo	number		NOT NULL
);

DROP TABLE note;

CREATE TABLE note (
	noteNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	noteTitle	varchar2(60)		NOT NULL,
	startDay	date		NOT NULL,
	endDay	date		NOT NULL,
	noteContent	varchar2(100)		NULL,
	charger	varchar2(30)		NULL,
	noteOrder	number	DEFAULT 50000	NOT NULL,
	boardNo	number		NOT NULL,
	projNo	number		NOT NULL
);

DROP TABLE Profile;

CREATE TABLE Profile (
	userId	varchar2(30)		NOT NULL,
	uuid	varchar2(100)		NOT NULL,
	imagePath	varchar2(100)		NOT NULL,
	thumbnailPath	varchar2(100)		NOT NULL
);

DROP TABLE schedule;

CREATE TABLE schedule (
	scheduleNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	scheduleTitle	varchar2(60)		NOT NULL,
	startDay	date		NOT NULL,
	endDay	date		NOT NULL,
	projNo	number		NOT NULL
);

DROP TABLE Chat;

CREATE TABLE Chat (
	chatNo	number	GENERATED ALWAYS AS IDENTITY	NOT NULL,
	messageType	varchar2(20)		NOT NULL,
	message	varchar2(600)		NOT NULL,
	createdAt	Date		NOT NULL,
	chatRoomNo	number		NOT NULL
);

DROP TABLE ChatRoom;

CREATE TABLE ChatRoom (
	chatRoomNo	number		NOT NULL,
	chatRoomName	varchar2(30)		NOT NULL
);

DROP TABLE ChatUser;

CREATE TABLE ChatUser (
	userId	varchar2(30)		NOT NULL,
	enterTime	varchar2(50)		NOT NULL,
	chatRoomNo	number		NOT NULL
);

ALTER TABLE Usr ADD CONSTRAINT PK_USR PRIMARY KEY (
	userId
);

ALTER TABLE UserInfo ADD CONSTRAINT PK_USERINFO PRIMARY KEY (
	userId
);

ALTER TABLE Project ADD CONSTRAINT PK_PROJECT PRIMARY KEY (
	projNo
);

ALTER TABLE UPM ADD CONSTRAINT PK_UPM PRIMARY KEY (
	upmNo
);

ALTER TABLE Board ADD CONSTRAINT PK_BOARD PRIMARY KEY (
	boardNo
);

ALTER TABLE DocPage ADD CONSTRAINT PK_DOCPAGE PRIMARY KEY (
	docPageNo
);

ALTER TABLE note ADD CONSTRAINT PK_NOTE PRIMARY KEY (
	noteNo
);

ALTER TABLE Profile ADD CONSTRAINT PK_PROFILE PRIMARY KEY (
	userId
);

ALTER TABLE schedule ADD CONSTRAINT PK_SCHEDULE PRIMARY KEY (
	scheduleNo
);

ALTER TABLE Chat ADD CONSTRAINT PK_CHAT PRIMARY KEY (
	chatNo
);

ALTER TABLE ChatRoom ADD CONSTRAINT PK_CHATROOM PRIMARY KEY (
	chatRoomNo
);

ALTER TABLE UserInfo ADD CONSTRAINT FK_Usr_TO_UserInfo_1 FOREIGN KEY (
	userId
)
REFERENCES Usr (
	userId
);

ALTER TABLE UPM ADD CONSTRAINT FK_Usr_TO_UPM_1 FOREIGN KEY (
	userId
)
REFERENCES Usr (
	userId
);

ALTER TABLE UPM ADD CONSTRAINT FK_Project_TO_UPM_1 FOREIGN KEY (
	projNo
)
REFERENCES Project (
	projNo
);

ALTER TABLE Board ADD CONSTRAINT FK_Project_TO_Board_1 FOREIGN KEY (
	projNo
)
REFERENCES Project (
	projNo
);

ALTER TABLE DocPage ADD CONSTRAINT FK_Project_TO_DocPage_1 FOREIGN KEY (
	projNo
)
REFERENCES Project (
	projNo
);

ALTER TABLE note ADD CONSTRAINT FK_Board_TO_note_1 FOREIGN KEY (
	boardNo
)
REFERENCES Board (
	boardNo
);

ALTER TABLE note ADD CONSTRAINT FK_Project_TO_note_1 FOREIGN KEY (
	projNo
)
REFERENCES Project (
	projNo
);

ALTER TABLE Profile ADD CONSTRAINT FK_UserInfo_TO_Profile_1 FOREIGN KEY (
	userId
)
REFERENCES UserInfo (
	userId
);

ALTER TABLE schedule ADD CONSTRAINT FK_Project_TO_schedule_1 FOREIGN KEY (
	projNo
)
REFERENCES Project (
	projNo
);

ALTER TABLE Chat ADD CONSTRAINT FK_ChatRoom_TO_Chat_1 FOREIGN KEY (
	chatRoomNo
)
REFERENCES ChatRoom (
	chatRoomNo
);

ALTER TABLE ChatUser ADD CONSTRAINT FK_Usr_TO_ChatUser_1 FOREIGN KEY (
	userId
)
REFERENCES Usr (
	userId
);

ALTER TABLE ChatUser ADD CONSTRAINT FK_ChatRoom_TO_ChatUser_1 FOREIGN KEY (
	chatRoomNo
)
REFERENCES ChatRoom (
	chatRoomNo
);