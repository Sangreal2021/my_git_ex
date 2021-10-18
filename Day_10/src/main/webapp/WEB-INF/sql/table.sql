drop table attaches;
drop table board;
drop table member;


-- DATABASE 생성
create database board;
use board;


-- MEMBER TABLE 생성
create table member (
	id varchar(30) primary key,
    password char(30) not null,
    name varchar(30) not null,
    age int null,
    phone varchar(30) not null,
    regDate datetime not null
);


-- BOARDT TABLE 생성
create table board (
	id int auto_increment primary key,
    title varchar(100) not null,
    content varchar(5000) null,
    writer varchar(30) not null,
    regDate datetime not null,
    lastUpdateTime datetime null,
    readCount int not null,
    
    foreign key (writer) references member (id)
    -- update cascade delete cascade
);


-- ATTACHES TABLE 생성
create table attaches (
    id int auto_increment primary key,
    path varchar(1000) not null,
    board_id int not null,
    
    foreign key (board_id) references board (id)
);


-- EX) DUMMY DATA OF BOARD (writer는 실제 회원가입한 아이디를 써야함)
insert into board
values (null, '1번째 게시글', '안녕하세요', 'aaa', now(), null, 0);


-- 게시판 글쓴 회원과 가입된 회원 비교
select *
from member join board
on member.id = board.writer;


-- 게시판 글쓰기 및 파일업로드한 회원과 가입된 회원 비교
select *
from member join board
on member.id = board.writer
join attaches
on board.id = attaches.board_id;