
### User表

| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  uid| char(32) | N | 用户id |
|  utype| bigint not null default 0 | N | 用户类型（0普通用户、1居委会） |
|  username| char(50) | N | 用户昵称 |
|  password| longtext | N | 用户密码 |
|  email| char(50) | Y | 用户邮箱 |
|  community_name| char(50) | N | 用户所在社区 |
|  avatar_url| longtext | Y | 用户头像地址 |
|  email_valid| boolean default 0 | Y | 邮箱是否验证 |
|  create_date| datetime| N | 创建时间 |



```sql
create table user(
uid char(32) not null primary key,
utype bigint not null default 0,
username char(50) not null,
password longtext not null,
email char(50),
community_name char(50) not null,
house_addr char(50) not null,
avatar_url longtext,
email_valid boolean default 0,
create_date datetime not null);
```
可能要进行的更新
```sql
create table user(
uid char(32) not null primary key,
utype bigint not null default 0,
username char(50) not null,
password longtext not null,
email char(50) not null,
community_name char(50),
house_addr char(50),
avatar_url longtext,
email_valid boolean default 0,
create_date datetime not null);
```





### 帖子表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  post_id| char(32)| N |帖子id |
|  uid| char(32)| N | 帖子所属用户id |
|  post_type| long| N |帖子类型（1文字 2图片） |
|  post_title |longtext | N | 标题|
|  post_content| longtext | N | 内容 |
|  post_tag| char(50) | Y | 帖子标签 |
|  view_times| bigint| Y | 帖子浏览次数 |
|  post_date| datetime | N | 创建时间 |
|  like_cnt| bigint = 0| Y | 喜欢个数|
```sql
create table post_table(
post_id char(32) not null primary key,
uid char(32) not null, 
post_type bigint not null,
post_title longtext not null, 
post_content longtext not null,
post_tag char(50), 
view_times bigint,
post_date datetime not null,
like_cnt bigint default 0);
```


### 图片表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  img_id| bigint | N |图片id |
|  post_id| char(32) | N | 图片所属的帖子 |
|  img_order| bigint | N | 图片在一张帖子中的顺序 |
|  url |longtext | N | 图片地址|
|  width |long | Y | 图片宽度|
|  height |long | Y | 图片高度|

```sql
create table img_table(
img_id bigint not null auto_increment primary key,
post_id char(32) not null,
img_order bigint,
url longtext not null,
width bigint,
height bigint);

```



---------------------------------------------------------

### 评论表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  comment_id| char(32)| N |评论id |
|  uid| char(32) | N | 发表该评论的用户id |
|  post_id| char(32) | N | 评论所属的帖子 |
|  content| longtext| N | 评论内容 |
|  like_cnt| bigint default 0| Y | 评论点赞数 |
|  father_id| char(32)| Y| 父评论id |
| c_level| bigint default 0| Y| 评论层级 |
|comment_date| datetime| N | 评论时间|

```sql
create table comment_table(
comment_id char(32) not null,
uid char(32) not null,
post_id char(32) not null,
content longtext not null,
like_cnt bigint default 0,
father_id char(32),
comment_level bigint default 0,
comment_date datetime not null);
```
------------------------
### 收藏表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  collect_id| bigint| N |收藏id |
|  post_id| char(32) | N | 收藏的帖子 |
|  uid| char(32) | N | 用户id |
|collect_date| datetime| N | 收藏时间|

```sql
create table collect_table(
collect_id bigint primary key auto_increment,
post_id char(32) not null,
uid char(32) not null,
collect_date datetime not null);
```
--------------------
### 用户喜欢表-帖子
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint primary key auto_increment| N |id |
| uid | char(32) | N | 用户id |
| post_id | char(32)| N |帖子id |
| like_status| bigint| N | 0不喜欢， 1喜欢|

```sql
create table user_like_post(
id bigint primary key auto_increment,
uid char(32) not null,
post_id char(32) not null,
like_status bigint not null);
```

----------------------
### 用户喜欢表-评论
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint primary key auto_increment| N |id |
| uid | char(32) | N | 用户id |
| comment_id | char(32)| N |评论id |
| like_status| long | N | 0不喜欢， 1喜欢|

```sql
create table user_like_comment(
id bigint primary key auto_increment,
uid char(32) not null,
comment_id char(32) not null,
like_status bigint not null);
```

-------------------------------------
### 用户收藏表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint primary key auto_increment| N |id |
| uid | char(32) | N | 用户id |
| post_id | char(32)| N |收藏的帖子id |

```sql
create table user_collect_post(
id bigint primary key auto_increment,
uid char(32) not null,
post_id char(32) not null);
```
-------------------------------------
                                                       投票功能

### 投票表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
|  vote_id| char(32)| N |投票id |
|  uid| char(32)| N | 投票所属用户id |
|  vote_type| bigint default 0| Y |投票类型 |
|  vote_title |longtext | N | 标题|
|  vote_content| longtext | N | 内容 |
|  vote_tag| char(50) | Y | 投票标签 |
|  view_times| bigint| Y | 浏览次数 |
|  vote_cnt| bigint| Y | 投票次数 |
|  vote_status| char(50)| Y | 投票状态 |
|  create_date| datetime | N | 创建时间 |
|  end_date| datetime | N | 结束时间 |
```sql
create table vote_table(
vote_id char(32) not null primary key, 
uid char(32) not null,
vote_type bigint default 0,
vote_title longtext not null,
vote_content longtext not null,
vote_tag char(50),
view_times bigint default 0,
vote_cnt bigint default 0,
vote_status char(50),
create_date datetime not null,
end_date datetime not null);
```

### 投票选项表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| option_id | char(32)| N |选项id |
| vote_id | char(32)| N |选项所属的post/vote |
| option_content | longtext | N |选项内容 |
| option_order | bigint| N |选项顺序 |
| cnt | bigint | N |被选次数 |
```sql
create table vote_option(
option_id char(32) primary key,
vote_id char(32) not null,
option_content longtext not null,
option_order bigint not null,
cnt bigint default 0);
```
### 投票记录表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint| N |记录id |
| uid | char(32)| N |用户id |
| vote_id | char(32) | N |选项id |
| option_id | char(32) | N |选项id |
```sql
create table vote_record(
id bigint primary key auto_increment,
uid char(32) not null,
vote_id char(32) not null,
option_id char(32) not null);
```

----------------------------
### 帖子推荐表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint| N |id |
| post_id | char(32)| N |帖子id |
| recomm_id | char(32) | N |推荐给该帖子的帖子id |
| similarity | double | N |相似度 |


```sql
create table recomm_table(
id bigint primary key auto_increment,
post_id char(32) not null, 
recomm_id char(32) not null, 
similarity double not null);
```

--------------------------

### 用户浏览历史记录表（暂时没用）
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| id | bigint| N |id |
| uid | char(32)| N |用户id |
| post_id | char(32) | N |帖子id |

```sql
create table user_view_record (
id bigint primary key auto_increment,
uid char(32) not null, 
post_id char(32) not null,
create_date datetime not null);

```

------------------------
### 社区公告表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| note_id | char(32)| N |id |
| community_name | char(50)| N |公告所属社区 |
| read_cnt | bigint | N |已读人数 |
| create_date | datetime| N |发布时间 |

```sql
create table community_note(
note_id char(32) primary key,
community_name char(50) not null,
content longtext not null, 
read_cnt bigint not null default 0, 
create_date datetime not null);
```

----------
### 用户预约表
| 字段 | 类型 |  null | 含义 |
|--|--|--|--|
| reserve_id | char(32)| N |预约id |
| uid | char(32)| N |预约用户 |
| qr_url | longtext| N |二维码地址 |

```sql
create table reserve_table(
reserve_id char(32) primary key, 
uid char(32) not null,
qr_url longtext not null);
```
















































































