CREATE SEQUENCE"AccessLogValve_id_seq"MAXVALUE 9223372036854775807 CYCLE;
CREATE TABLE"AccessLogValve"(
	"id"int8 DEFAULT"nextval"('"AccessLogValve_id_seq"'::"regclass")PRIMARY KEY,
	"remoteHost"varchar,
	"userName"varchar,
	"timestamp"timestamp NOT NULL DEFAULT"now"(),
	"virtualHost"varchar,
	"method"varchar,
	"query"varchar,
	"status"int2,
	"bytes"int8 NOT NULL DEFAULT'0',
	"referer"varchar,
	"userAgent"varchar
);

/**
 * 行政區劃
 */
CREATE SEQUENCE"District_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"District"(
	"id"int2 DEFAULT"nextval"('"District_id_seq"'::"regclass")PRIMARY KEY,
	"upperLevel"int2 REFERENCES"District"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"name"varchar NOT NULL,
	UNIQUE("upperLevel","name")
);
ALTER SEQUENCE"District_id_seq"OWNED BY"District"."id";
COMMENT ON COLUMN"District"."id"IS'主鍵^16';
COMMENT ON COLUMN"District"."upperLevel"IS'上層行政區';
COMMENT ON COLUMN"District"."name"IS'行政區名稱';
COMMENT ON TABLE"District"IS'行政區劃';
-- 
INSERT INTO"District"("upperLevel","name")VALUES
(NULL,E'臺北市'),-- 1
(NULL,E'新北市'),-- 2
(NULL,E'桃園市'),-- 3
(NULL,E'臺中市'),-- 4
(NULL,E'臺南市'),-- 5
(NULL,E'高雄市'),-- 6
(NULL,E'新竹市'),-- 7
(NULL,E'新竹縣'),-- 8
(NULL,E'苗栗縣'),-- 9
(NULL,E'彰化縣'),-- 10
(NULL,E'南投縣'),-- 11
(NULL,E'雲林縣'),-- 12
(NULL,E'嘉義縣'),-- 13
(NULL,E'嘉義市'),-- 14
(NULL,E'屏東縣'),-- 15
(NULL,E'宜蘭縣'),-- 16
(NULL,E'花蓮縣'),-- 17
(NULL,E'臺東縣'),-- 18
(NULL,E'基隆市'),-- 19
(NULL,E'澎湖縣'),-- 20
(NULL,E'金門縣'),-- 21
(NULL,E'連江縣');-- 22

/**
 * 工業區
 */
CREATE SEQUENCE"IndustrialDistrict_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"IndustrialDistrict"(
	"id"int2 DEFAULT"nextval"('"IndustrialDistrict_id_seq"'::"regclass")PRIMARY KEY,
	"district"int2 NOT NULL REFERENCES"District"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"name"varchar NOT NULL,
	UNIQUE("district","name")
);
ALTER SEQUENCE"IndustrialDistrict_id_seq"OWNED BY"IndustrialDistrict"."id";
COMMENT ON COLUMN"IndustrialDistrict"."id"IS'主鍵^16';
COMMENT ON COLUMN"IndustrialDistrict"."district"IS'行政區劃';
COMMENT ON COLUMN"IndustrialDistrict"."name"IS'工業區名稱';
COMMENT ON TABLE"IndustrialDistrict"IS'工業區';
-- 
INSERT INTO"IndustrialDistrict"("district","name")VALUES
('19',E'六堵科技園區'),
('1',E'南港軟體工業園區'),
('2',E'新北產業園區'),
('3',E'新竹科學工業園區龍潭園區'),
('3',E'中壢工業區'),
('3',E'平鎮工業區'),
('7',E'新竹科學工業園區'),
('7',E'新竹生物醫學園區'),
('7',E'新竹工業區'),
('9',E'新竹科學工業園區竹南園區'),
('9',E'新竹科學工業園區銅鑼園區'),
('4',E'中部科學工業園區'),
('4',E'台中工業區'),
('10',E'彰濱工業區'),
('5',E'南部科學工業園區'),
('5',E'安平工業區'),
('5',E'台南科技工業區'),
('5',E'永康工業區'),
('5',E'官田工業區'),
('5',E'新營工業區'),
('6',E'南部科學工業園區'),
('6',E'臨海工業區'),
('6',E'仁大工業區'),
('6',E'林園工業區'),
('6',E'高雄軟體科技園區'),
('16',E'新竹科學工業園區宜蘭園區');

/**
 * 廠商
 */
CREATE SEQUENCE"Vendor_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Vendor"(
	"id"int2 DEFAULT"nextval"('"Vendor_id_seq"'::"regclass")PRIMARY KEY,
	"name"varchar NOT NULL,
	"unifiedBusinessNumber"varchar UNIQUE,
	"address"varchar,
	"industrialDistrict"int2 REFERENCES"IndustrialDistrict"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"employeeCount"int2,
	"contactTitle"varchar,
	"contactName"varchar,
	"contactNumber"varchar,
	"contactEmail"varchar,
	"contactFax"varchar,
	"capital"int4,
	"turnover"int4,
	"business"text,
	"shadow"varchar NOT NULL DEFAULT''
);
ALTER SEQUENCE"Vendor_id_seq"OWNED BY"Vendor"."id";
COMMENT ON COLUMN"Vendor"."id"IS'主鍵^16';
COMMENT ON COLUMN"Vendor"."name"IS'廠商名稱';
COMMENT ON COLUMN"Vendor"."unifiedBusinessNumber"IS'統一編號';
COMMENT ON COLUMN"Vendor"."address"IS'廠商地址';
COMMENT ON COLUMN"Vendor"."industrialDistrict"IS'工業區';
COMMENT ON COLUMN"Vendor"."employeeCount"IS'員工人數';
COMMENT ON COLUMN"Vendor"."contactTitle"IS'聯絡人職稱';
COMMENT ON COLUMN"Vendor"."contactName"IS'聯絡人姓名';
COMMENT ON COLUMN"Vendor"."contactNumber"IS'聯絡人電話';
COMMENT ON COLUMN"Vendor"."contactEmail"IS'聯絡人電子郵件';
COMMENT ON COLUMN"Vendor"."contactFax"IS'聯絡人傳真';
COMMENT ON COLUMN"Vendor"."capital"IS'資本額';
COMMENT ON COLUMN"Vendor"."turnover"IS'營業額';
COMMENT ON COLUMN"Vendor"."business"IS'營業項目';
COMMENT ON COLUMN"Vendor"."shadow"IS'密碼';
COMMENT ON TABLE"Vendor"IS'廠商';

/**
 * 輔導案例
 */
CREATE SEQUENCE"Counseling_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Counseling"(
	"id"int2 DEFAULT"nextval"('"Counseling_id_seq"'::"regclass")PRIMARY KEY,
	"taiwaneseYear"int2,
	"name"varchar NOT NULL,
	"vendor"int2 NOT NULL REFERENCES"Vendor"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"amount"int4,
	"condition"text,
	"items"text,
	"performance"text,
	"photo"bytea,
	"closed"bool NOT NULL DEFAULT'0'
);
ALTER SEQUENCE"Counseling_id_seq"OWNED BY"Counseling"."id";
COMMENT ON COLUMN"Counseling"."id"IS'主鍵^16';
COMMENT ON COLUMN"Counseling"."taiwaneseYear"IS'年度';
COMMENT ON COLUMN"Counseling"."name"IS'計畫名稱';
COMMENT ON COLUMN"Counseling"."vendor"IS'受輔導廠商';
COMMENT ON COLUMN"Counseling"."amount"IS'補助金額';
COMMENT ON COLUMN"Counseling"."condition"IS'業者現況';
COMMENT ON COLUMN"Counseling"."items"IS'輔導項目';
COMMENT ON COLUMN"Counseling"."performance"IS'輔導效益';
COMMENT ON COLUMN"Counseling"."photo"IS'相關照片';
COMMENT ON COLUMN"Counseling"."closed"IS'案件狀態';
COMMENT ON TABLE"Counseling"IS'輔導案例';

/**
 * 手風琴
 */
CREATE SEQUENCE"Accordion_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Accordion"(
	"id"int2 DEFAULT"nextval"('"Accordion_id_seq"'::"regclass")PRIMARY KEY,
	"name"varchar NOT NULL UNIQUE,
	"ordinal"int2 NOT NULL UNIQUE
);
ALTER SEQUENCE"Accordion_id_seq"OWNED BY"Accordion"."id";
COMMENT ON COLUMN"Accordion"."id"IS'主鍵^16';
COMMENT ON COLUMN"Accordion"."name"IS'手風琴名稱';
COMMENT ON COLUMN"Accordion"."ordinal"IS'排序';
COMMENT ON TABLE"Accordion"IS'手風琴';
-- 
INSERT INTO"Accordion"("name","ordinal")VALUES(E'功能管理','1'),(E'其它','2'),(E'存取管理','3');

/**
 * 路由
 */
CREATE SEQUENCE"Route_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Route"(
	"id"int2 DEFAULT"nextval"('"Route_id_seq"'::"regclass")PRIMARY KEY,
	"accordion"int2 NOT NULL REFERENCES"Accordion"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"label"varchar NOT NULL UNIQUE,
	"hyperlink"varchar NOT NULL UNIQUE,
	"ordinal"int2 NOT NULL,
	UNIQUE("accordion","ordinal")
);
ALTER SEQUENCE"Route_id_seq"OWNED BY"Route"."id";
COMMENT ON COLUMN"Route"."id"IS'主鍵^16';
COMMENT ON COLUMN"Route"."accordion"IS'手風琴';;
COMMENT ON COLUMN"Route"."label"IS'顯示名稱';
COMMENT ON COLUMN"Route"."hyperlink"IS'連結';
COMMENT ON COLUMN"Route"."ordinal"IS'排序';
COMMENT ON TABLE"Route"IS'路由';
-- 
INSERT INTO"Route"("accordion","label","hyperlink","ordinal")VALUES
('1',E'計畫簡介',E'/JiHuaJianJie/','1'),-- 1
('1',E'最新消息',E'/ZuiXinXiaoXi/','2'),-- 2
('1',E'活動花絮',E'/HuoDongHuaXu/','3'),-- 3
('1',E'輔導流程',E'/FuDaoLiuCheng/','4'),-- 4
('1',E'輔導團隊',E'/FuDaoTuanDui/','5'),
('1',E'下載專區',E'/XiaZaiZhuanQu/','6'),
('1',E'輔導案例',E'/FuDaoAnLi/','7'),-- 7
('1',E'友站連結',E'/YouZhanLianJie/','8'),
('1',E'聯絡我們',E'/LianLuoWoMen/','9'),
('2',E'群組',E'/QunZu/','1'),-- 10
('2',E'單位',E'/DanWei/','2'),-- 11
('2',E'人員',E'/RenYuan/','3'),
('2',E'廠商',E'/ChangShang/','4'),-- 13
('2',E'工業區',E'/GongYeQu/','5'),-- 14
('2',E'行政區',E'/XingZhengQu/','6'),-- 15
('3',E'路由',E'/LuYou/','2'),-- 16
('3',E'手風琴',E'/ShouFengQin/','3');-- 17

/**
 * 群組
 */
CREATE SEQUENCE"Clan_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Clan"(
	"id"int2 DEFAULT"nextval"('"Clan_id_seq"'::"regclass")PRIMARY KEY,
	"name"varchar NOT NULL UNIQUE,
	"ordinal"int2 NOT NULL UNIQUE
);
ALTER SEQUENCE"Clan_id_seq"OWNED BY"Clan"."id";
COMMENT ON COLUMN"Clan"."id"IS'主鍵^16';
COMMENT ON COLUMN"Clan"."name"IS'群組名稱';
COMMENT ON COLUMN"Clan"."ordinal"IS'排序';
COMMENT ON TABLE"Clan"IS'群組';
-- 
INSERT INTO"Clan"("name","ordinal")VALUES(E'管理員','1'),(E'專家','2'),(E'評審委員','3'),(E'工讀生','4');

/**
 * 權限
 */
CREATE SEQUENCE"Privilege_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Privilege"(
	"id"int2 DEFAULT"nextval"('"Privilege_id_seq"'::"regclass")PRIMARY KEY,
	"route"int2 NOT NULL REFERENCES"Route"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"clan"int2 NOT NULL REFERENCES"Clan"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	UNIQUE("route","clan")
);
ALTER SEQUENCE"Privilege_id_seq"OWNED BY"Privilege"."id";
COMMENT ON COLUMN"Privilege"."id"IS'主鍵^16';
COMMENT ON COLUMN"Privilege"."route"IS'路由';
COMMENT ON COLUMN"Privilege"."clan"IS'排序';
COMMENT ON TABLE"Privilege"IS'權限';
-- 
INSERT INTO"Privilege"("route","clan")VALUES('1','1'),('2','1'),('3','1'),('4','1'),('7','1'),('10','1'),('11','1'),('13','1'),('14','1'),('15','1'),('16','1'),('17','1');

/**
 * 單位
 */
CREATE SEQUENCE"Institution_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Institution"(
	"id"int2 DEFAULT"nextval"('"Institution_id_seq"'::"regclass")PRIMARY KEY,
	"district"int2 REFERENCES"District"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"name"varchar NOT NULL UNIQUE
);
ALTER SEQUENCE"Institution_id_seq"OWNED BY"Institution"."id";
COMMENT ON COLUMN"Institution"."id"IS'主鍵^16';
COMMENT ON COLUMN"Institution"."district"IS'行政區';
COMMENT ON COLUMN"Institution"."name"IS'單位名稱';
COMMENT ON TABLE"Institution"IS'單位';
-- 
INSERT INTO"Institution"("name")VALUES(E'國立聯合大學'),(E'亞太創意技術學院'),(E'育達科技大學'),(E'工業技術研究院'),(E'商業發展研究院'),(E'國家衛生研究院'),(E'農業科技研究院');

/**
 * 人員
 */
CREATE SEQUENCE"Individual_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Individual"(
	"id"int2 DEFAULT"nextval"('"Individual_id_seq"'::"regclass")PRIMARY KEY,
	"clan"int2 NOT NULL REFERENCES"Clan"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"name"varchar NOT NULL,
	"email"varchar NOT NULL UNIQUE,
	"contactNumber"varchar,
	"contactCellular"varchar,
	"major"varchar,
	"title"varchar,
	"institution"int2 REFERENCES"Institution"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"specialty"text,
	"experience"text,
	"shadow"varchar NOT NULL DEFAULT''
);
ALTER SEQUENCE"Individual_id_seq"OWNED BY"Individual"."id";
COMMENT ON COLUMN"Individual"."id"IS'主鍵^16';
COMMENT ON COLUMN"Individual"."clan"IS'群組';
COMMENT ON COLUMN"Individual"."name"IS'人員姓名';
COMMENT ON COLUMN"Individual"."email"IS'電子郵件(帳號)';
COMMENT ON COLUMN"Individual"."contactNumber"IS'聯絡電話';
COMMENT ON COLUMN"Individual"."contactCellular"IS'聯絡手機';
COMMENT ON COLUMN"Individual"."major"IS'系/所/中心';
COMMENT ON COLUMN"Individual"."title"IS'職稱';
COMMENT ON COLUMN"Individual"."institution"IS'單位';
COMMENT ON COLUMN"Individual"."specialty"IS'專長';
COMMENT ON COLUMN"Individual"."experience"IS'經歷';
COMMENT ON COLUMN"Individual"."shadow"IS'密碼';
COMMENT ON TABLE"Individual"IS'人員';
-- 
INSERT INTO"Individual"("clan","name","email","shadow")VALUES('1',E'林欣宜',E'x@y.z',E'116f370e4c3134219fcb871dde3133a7');

/**
 * 最新消息
 */
CREATE SEQUENCE"News_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"News"(
	"id"int2 DEFAULT"nextval"('"News_id_seq"'::"regclass")PRIMARY KEY,
	"title"varchar NOT NULL,
	"content"text,
	"createdOn"timestamp NOT NULL DEFAULT"now"(),
	"since"date NOT NULL,
	"until"date NOT NULL,
	"censored"bool NOT NULL DEFAULT'0',
	CHECK("since"<"until")
);
ALTER SEQUENCE"News_id_seq"OWNED BY"News"."id";
COMMENT ON COLUMN"News"."id"IS'主鍵^16';
COMMENT ON COLUMN"News"."title"IS'標題';
COMMENT ON COLUMN"News"."content"IS'內容';
COMMENT ON COLUMN"News"."createdOn"IS'建立時戳';
COMMENT ON COLUMN"News"."since"IS'上架日期';
COMMENT ON COLUMN"News"."until"IS'下架日期';
COMMENT ON COLUMN"News"."censored"IS'發佈審查';
COMMENT ON TABLE"News"IS'最新消息';

/**
 * 活動花絮
 */
CREATE SEQUENCE"Event_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Event"(
	"id"int2 DEFAULT"nextval"('"Event_id_seq"'::"regclass")PRIMARY KEY,
	"title"varchar NOT NULL,
	"content"text,
	"createdOn"timestamp NOT NULL DEFAULT"now"(),
	"since"date NOT NULL,
	"until"date NOT NULL,
	"censored"bool NOT NULL DEFAULT'0',
	"image"bytea NOT NULL,
	CHECK("since"<"until")
);
ALTER SEQUENCE"Event_id_seq"OWNED BY"Event"."id";
COMMENT ON COLUMN"Event"."id"IS'主鍵^16';
COMMENT ON COLUMN"Event"."title"IS'標題';
COMMENT ON COLUMN"Event"."content"IS'內容';
COMMENT ON COLUMN"Event"."createdOn"IS'建立時戳';
COMMENT ON COLUMN"Event"."since"IS'上架日期';
COMMENT ON COLUMN"Event"."until"IS'下架日期';
COMMENT ON COLUMN"Event"."censored"IS'發佈審查';
COMMENT ON COLUMN"Event"."image"IS'列表圖片';
COMMENT ON TABLE"Event"IS'活動花絮';

/**
 * 附件
 */
CREATE SEQUENCE"Attachment_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Attachment"(
	"id"int8 DEFAULT"nextval"('"Attachment_id_seq"'::"regclass")PRIMARY KEY,
	"news"int2 REFERENCES"News"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"event"int2 REFERENCES"Event"("id")ON DELETE RESTRICT ON UPDATE CASCADE,
	"filename"varchar NOT NULL,
	"content"bytea NOT NULL,
	UNIQUE("news","filename"),
	UNIQUE("event","filename")
);
ALTER SEQUENCE"Attachment_id_seq"OWNED BY"Attachment"."id";
COMMENT ON COLUMN"Attachment"."id"IS'主鍵^64';
COMMENT ON COLUMN"Attachment"."news"IS'最新消息';
COMMENT ON COLUMN"Attachment"."event"IS'活動花絮';
COMMENT ON COLUMN"Attachment"."filename"IS'原始檔名';
COMMENT ON COLUMN"Attachment"."content"IS'檔案內容';
COMMENT ON TABLE"Attachment"IS'附件';

/**
 * 下載
 */
CREATE SEQUENCE"Download_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Download"(
	"id"int2 DEFAULT"nextval"('"Download_id_seq"'::"regclass")PRIMARY KEY,
	"filename"varchar NOT NULL,
-- 	"content"bytea NOT NULL,
	"contentType"varchar,
	"since"date NOT NULL,
	"until"date NOT NULL,
	"censored"bool NOT NULL DEFAULT'0',
	CHECK("since"<"until")
);
ALTER SEQUENCE"Download_id_seq"OWNED BY"Download"."id";
COMMENT ON COLUMN"Download"."id"IS'主鍵^16';
COMMENT ON COLUMN"Download"."filename"IS'原始檔名';
-- COMMENT ON COLUMN"Download"."content"IS'內容';
COMMENT ON COLUMN"Download"."contentType"IS'檔案類型';
COMMENT ON COLUMN"Download"."since"IS'上架日期';
COMMENT ON COLUMN"Download"."until"IS'下架日期';
COMMENT ON COLUMN"Download"."censored"IS'發佈審查';
COMMENT ON TABLE"Download"IS'下載';

/**
 * 友站連結
 */
CREATE SEQUENCE"Ally_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Ally"(
	"id"int2 DEFAULT"nextval"('"Ally_id_seq"'::"regclass")PRIMARY KEY,
	"title"varchar NOT NULL,
	"phone"varchar,
	"image"bytea NOT NULL,
	"fqdn"varchar NOT NULL,
	"since"date NOT NULL,
	"until"date NOT NULL,
	"censored"bool NOT NULL DEFAULT'0',
	CHECK("since"<"until")
);
ALTER SEQUENCE"Ally_id_seq"OWNED BY"Ally"."id";
COMMENT ON COLUMN"Ally"."id"IS'主鍵^16';
COMMENT ON COLUMN"Ally"."title"IS'標題';
COMMENT ON COLUMN"Ally"."phone"IS'聯絡電話';
COMMENT ON COLUMN"Ally"."image"IS'縮圖';
COMMENT ON COLUMN"Ally"."fqdn"IS'參考網址';
COMMENT ON COLUMN"Ally"."since"IS'上架日期';
COMMENT ON COLUMN"Ally"."until"IS'下架日期';
COMMENT ON COLUMN"Ally"."censored"IS'發佈審查';
COMMENT ON TABLE"Ally"IS'友站連結';

/**
 * 聯絡我們
 */
CREATE SEQUENCE"Reach_id_seq"MAXVALUE 32767 CYCLE;
CREATE TABLE"Reach"(
	"id"int4 DEFAULT"nextval"('"Reach_id_seq"'::"regclass")PRIMARY KEY,
	"company"varchar,
	"name"varchar,
	"title"varchar,
	"phone"varchar,
	"email"varchar,
	"address"varchar,
	"content"varchar
);
ALTER SEQUENCE"Reach_id_seq"OWNED BY"Reach"."id";
COMMENT ON COLUMN"Reach"."id"IS'主鍵^32';
COMMENT ON COLUMN"Reach"."company"IS'公司名稱';
COMMENT ON COLUMN"Reach"."name"IS'大名';
COMMENT ON COLUMN"Reach"."title"IS'職稱';
COMMENT ON COLUMN"Reach"."phone"IS'聯絡電話';
COMMENT ON COLUMN"Reach"."email"IS'電子郵件';
COMMENT ON COLUMN"Reach"."address"IS'地址';
COMMENT ON COLUMN"Reach"."content"IS'諮詢或發問的內容';
COMMENT ON TABLE"Reach"IS'聯絡我們';
