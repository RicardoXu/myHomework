This is a homework of javaweb.

# 部署地址

http://59.111.103.99:8080/

# 图片素材来源于

http://cn.freeimages.com/image/landscapes-nature

# 数据库表结构：

在原数据库的基础上，修改了price列类型int为decimal(14,2)、text列类型blob为text。

用户表

	create table person(
	id int auto_increment primary key comment "主键", 
	userName varchar(100) comment "用户名", 
	password varchar(100) comment "密码md5加密",
	nickName varchar(50) comment "用户昵称",
	userType tinyint(3) comment "类型，买家0，卖家1") 
	ENGINE=InnoDB  DEFAULT CHARSET=utf8;

内容表

	create table content(
	id int auto_increment primary key comment "主键",  
	price decimal(14,2) comment "当前价格",
	title varchar(100) comment "标题",
	icon blob comment "图片",
	abstract varchar(200) comment "摘要",
	text text comment "正文"  )
	ENGINE=InnoDB  DEFAULT CHARSET=utf8;

交易记录表

	create table trx(
	id int auto_increment primary key comment "主键",  
	contentId int  comment "内容ID",
	personId int comment "用户ID",
	price decimal(14,2) comment "购买价格",
	time bigint comment "购买时间",
	num bigint comment "购买数量",
	buy varchar(10) comment "购买状态"
	)
	ENGINE=InnoDB  DEFAULT CHARSET=utf8;

用户数据

	insert into `person` (`userName`, `password`, `nickName`, `userType`) values('buyer','37254660e226ea65ce6f1efd54233424','buyer','0');
	insert into `person` (`userName`, `password`, `nickName`, `userType`) values('seller','981c57a5cfb0f868e064904b8745766f','seller','1');