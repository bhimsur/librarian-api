CREATE SCHEMA library_management_service AUTHORIZATION library_management_service;

CREATE SEQUENCE library_management_service.book_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

ALTER SEQUENCE library_management_service.book_seq OWNER TO library_management_service;
GRANT ALL ON SEQUENCE library_management_service.book_seq TO library_management_service;

CREATE SEQUENCE library_management_service.member_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

ALTER SEQUENCE library_management_service.member_seq OWNER TO library_management_service;
GRANT ALL ON SEQUENCE library_management_service.member_seq TO library_management_service;

CREATE SEQUENCE library_management_service.transaction_hist_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

ALTER SEQUENCE library_management_service.transaction_hist_seq OWNER TO library_management_service;
GRANT ALL ON SEQUENCE library_management_service.transaction_hist_seq TO library_management_service;

CREATE TABLE library_management_service.book (
	id numeric(20) NOT NULL DEFAULT nextval('book_seq'::regclass),
	"name" varchar(150) NULL,
	isbn varchar(13) NOT NULL,
	stock numeric(4) NOT NULL DEFAULT 0,
	book_id varchar(36) NOT NULL,
	created_date timestamp NOT NULL,
	modified_date timestamp NULL,
	modified_reason varchar(150) NULL,
	CONSTRAINT book_pk PRIMARY KEY (id)
);

ALTER TABLE library_management_service.book OWNER TO library_management_service;
GRANT ALL ON TABLE library_management_service.book TO library_management_service;

CREATE TABLE library_management_service."member" (
	id numeric(20) NOT NULL DEFAULT nextval('member_seq'::regclass),
	"name" varchar(150) NULL,
	member_id varchar(25) NOT NULL,
	id_card_number varchar(16) NULL,
	CONSTRAINT member_pk PRIMARY KEY (id)
);


ALTER TABLE library_management_service."member" OWNER TO library_management_service;
GRANT ALL ON TABLE library_management_service."member" TO library_management_service;

CREATE TABLE library_management_service.transaction_history (
	id numeric(20) NOT NULL DEFAULT nextval('transaction_hist_seq'::regclass),
	transaction_id varchar(25) NOT NULL,
	member_id numeric(20) NOT NULL,
	book_id numeric(20) NOT NULL,
	quantity numeric(3) NOT NULL,
	status numeric(2) NOT NULL,
	state numeric(2) NOT NULL,
	duration_in_day numeric(3) NOT NULL,
	return_date timestamp NULL,
	transaction_date timestamp NOT NULL,
	modified_date timestamp NULL,
	modified_reason varchar(150) NULL,
	CONSTRAINT transaction_history_pk PRIMARY KEY (id)
);

ALTER TABLE library_management_service.transaction_history OWNER TO library_management_service;
GRANT ALL ON TABLE library_management_service.transaction_history TO library_management_service;

GRANT ALL ON SCHEMA library_management_service TO library_management_service;
