/*
	
	drop table member
	
	-- 회원테이블
	create table member
	(
		mem_idx		int primary key auto_increment,		-- 회원번호
		mem_name	varchar(100)  not null,				-- 회원명
		mem_id		varchar(100)  not null,				-- 아아디
		mem_pwd		varchar(100)  not null,				-- 비밀번호
		mem_zipcode	char(5)		  not null,				-- 우편번호
		mem_addr	varchar(1000) not null,				-- 주소
		mem_ip		varchar(100)  not null,				-- 아이피
		mem_regdate	datetime default now(),				-- 가입일자
		mem_grade	varchar(100) default '일반'			-- 회원등급
	)
	
	select * from member
	
	-- 아이디(unique)
	alter table member
		add constraint unique_member_id unique(mem_id);
	
	-- 회원등급(check)
	alter table member
		add constraint ck_member_grade check(mem_grade in('일반', '관리자'));
		
	-- sample data
	insert into member values(null, '김관리', 'admin', 'admin', '00000', '서울시 관악구', '192.168.219.170', now(), '관리자');
	insert into member values(null, '일길동', 'one', '1234', '00000', '서울시 관악구', '192.168.219.170', default, default);
	insert into member values(null, '박정환', '내가조장', 'leader', '00000', '서울시 동작구', '192.168.219.224', default, '관리자');
	insert into member values(null, '가짜관리자', 'fakeadmin', '1234', '00000', '서울시 동작구', '192.168.219.224', default, '관리자');
	
	commit
	
	select * from member where mem_id='one'
*/