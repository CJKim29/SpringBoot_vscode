/*
	
	drop table photo
	
	-- 테이블 생성
	create table photo
	(
		p_idx		int primary key auto_increment,		-- 일련번호
		p_title		varchar(200)	not null,			-- 제목
		p_content	varchar(2000)	not null,			-- 내용
		p_filename	varchar(200)	not null,			-- 파일명
		p_ip		varchar(100)	not null,			-- ip
		p_regdate	datetime,							-- 등록일자
		mem_idx		int,								-- 회원번호
		mem_name	varchar(100)	not null			-- 회원명
	)
		
	memberalter table photo
  		add constraint fk_photo_mem_idx foreign key(mem_idx)
                                  		references member(mem_idx)
                                  		on delete cascade	-- 이 키를 참조하는 자식 게시물들이 같이 삭제
*/