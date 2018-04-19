--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.5
-- Dumped by pg_dump version 9.6.5

SET statement_timeout = 0;
SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

--
-- Name: id_generator(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION id_generator(OUT result bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
 DECLARE
   seq_id int;
   now_millis bigint;
 BEGIN
   SELECT nextval('id_sequence') % 1024 INTO seq_id;
   SELECT FLOOR(EXTRACT(EPOCH FROM clock_timestamp()) * 1000) INTO now_millis;
 
   result:= now_millis << 20;
   result:= result | (10000000000);
   result:= result | (seq_id);
 END;
 $$;


ALTER FUNCTION public.id_generator(OUT result bigint) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: attr_object_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

CREATE TABLE "ATTR_OBJECT_TYPES" (
    "OBJECT_TYPE_ID" bigint NOT NULL,
    "ATTR_ID" bigint NOT NULL
);


ALTER TABLE "ATTR_OBJECT_TYPES" OWNER TO postgres;

--
-- Name: attr_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "ATTR_TYPES" (
    "ATTR_TYPE_ID" bigint DEFAULT id_generator() NOT NULL,
    "NAME" character varying(200) NOT NULL
);


ALTER TABLE "ATTR_TYPES" OWNER TO postgres;

--
-- Name: attributes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "ATTRIBUTES" (
    "ATTR_ID" bigint DEFAULT id_generator() NOT NULL,
    "NAME" character varying(200) NOT NULL,
    "ATTR_TYPE_ID" bigint NOT NULL
);


ALTER TABLE "ATTRIBUTES" OWNER TO postgres;

--
-- Name: enum_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "ENUM_TYPES" (
    "ENUM_TYPE_ID" bigint DEFAULT id_generator() NOT NULL,
    "NAME" character varying(200) NOT NULL
);


ALTER TABLE "ENUM_TYPES" OWNER TO postgres;

--
-- Name: enums; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "ENUMS" (
    "ENUM_ID" bigint DEFAULT id_generator() NOT NULL,
    "NAME" character varying(200) NOT NULL,
    "ENUM_TYPE_ID" bigint NOT NULL
);


ALTER TABLE "ENUMS" OWNER TO postgres;

--
-- Name: id_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE id_sequence OWNER TO postgres;

--
-- Name: object_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "OBJECT_TYPES" (
    "NAME" character varying(200) NOT NULL,
    "OBJECT_TYPE_ID" bigint DEFAULT id_generator() NOT NULL,
    "PARENT_TYPE_ID" bigint
);


ALTER TABLE "OBJECT_TYPES" OWNER TO postgres;

--
-- Name: objects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "OBJECTS" (
    "NAME" character varying(200) NOT NULL,
    "OBJECT_ID" bigint DEFAULT id_generator() NOT NULL,
    "PARENT_ID" bigint,
    "OBJECT_TYPE_ID" bigint NOT NULL
);


ALTER TABLE "OBJECTS" OWNER TO postgres;

--
-- Name: parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "PARAMETERS" (
    "OBJECT_ID" bigint NOT NULL,
    "ATTR_ID" bigint NOT NULL,
    "TEXT_VALUE" text,
    "DATE_VALUE" timestamp(0),
    "REFERENCE_VALUE" bigint,
    "ENUM_VALUE" bigint,
	"POINT_VALUE" point
);


ALTER TABLE "PARAMETERS" OWNER TO postgres;

--
-- Data for Name: attr_object_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ATTR_OBJECT_TYPES" ("OBJECT_TYPE_ID", "ATTR_ID") FROM stdin;
300	400
300	401
301	401
305	401
306	401
300	402
300	403
300	404
300	405
300	406
300	410
300	412
305	403
305	407
305	410
305	411
305	415
306	408
306	409
306	414
305	416
305	417
306	418
305	421
\.


--
-- Data for Name: attr_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ATTR_TYPES" ("ATTR_TYPE_ID", "NAME") FROM stdin;
51	Text
52	Number
53	Reference
54	Date
55	Enum value
56	Point 
\.


--
-- Data for Name: attributes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ATTRIBUTES" ("ATTR_ID", "NAME", "ATTR_TYPE_ID") FROM stdin;
400	Full name	51
401	Name	51
402	Password hash	51
403	Phone number	51
404	Birthday	54
405	User role	55
406	E-mail	51
407	Order cost	51
408	Item category	55
409	Item cost	51
411	Order paid	51
410	Address	56
412	Order	53
414	Item description	51
415	Item	53
416	Order courier	53
417	Order status	55
418	Item image	53
419	Order payment type	55
421	Order creation date	54
\.


--
-- Data for Name: enum_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ENUM_TYPES" ("ENUM_TYPE_ID", "NAME") FROM stdin;
700	User role
701	Order state
702	Item category
703	Order payment
\.


--
-- Data for Name: enums; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ENUMS" ("ENUM_ID", "NAME", "ENUM_TYPE_ID") FROM stdin;
800	ROLE_ADMIN	700
801	ROLE_USER	700
802	ROLE_COURIER	700
803	Created	701
805	Linked with courier	701
806	Delivered	701
808	Expired	701
809	Cancelled	701
811	Pizza	702
812	Sushi	702
813	Burgers	702
814	Salads	702
815	Snacks	702
816	Dessert	702
817	Beverages	702
818	Cash payment	703
819	Payment by card	703
820	Alcohol	702
\.


--
-- Data for Name: object_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "OBJECT_TYPES" ("NAME", "OBJECT_TYPE_ID", "PARENT_TYPE_ID") FROM stdin;
User	300	\N
Image	301	\N
Order	305	\N
Item	306	\N
\.


--
-- Data for Name: objects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "OBJECTS" ("NAME", "OBJECT_ID", "PARENT_ID", "OBJECT_TYPE_ID") FROM stdin;
\.


--
-- Data for Name: parameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "PARAMETERS" ("OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE") FROM stdin;
\.

--
-- Administrator:Administrator1 account creating
--

insert into "OBJECTS" ("NAME", "OBJECT_ID", "OBJECT_TYPE_ID") values ('Administrator', 1598190037014144001, 300);

insert into "PARAMETERS" ("OBJECT_ID", "ATTR_ID", "TEXT_VALUE") values (1598190037014144001, 400, 'Administrator');

insert into "PARAMETERS" ("OBJECT_ID", "ATTR_ID", "TEXT_VALUE") values (1598190037014144001, 401, 'admin');

insert into "PARAMETERS" ("OBJECT_ID", "ATTR_ID", "TEXT_VALUE") values (1598190037014144001, 402, 'df1e9a98b8022278f1a6b7f5f058e2b35696c680');

insert into "PARAMETERS" ("OBJECT_ID", "ATTR_ID", "ENUM_VALUE") values (1598190037014144001, 405, 800);

--
-- Name: attr_types attr_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ATTR_TYPES"
    ADD CONSTRAINT "ATTR_TYPES_PKEY" PRIMARY KEY ("ATTR_TYPE_ID");


--
-- Name: attributes attributes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ATTRIBUTES"
    ADD CONSTRAINT "ATTRIBUTES_PKEY" PRIMARY KEY ("ATTR_ID");


--
-- Name: enum_types enum_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ENUM_TYPES"
    ADD CONSTRAINT "ENUM_TYPES_PKEY" PRIMARY KEY ("ENUM_TYPE_ID");


--
-- Name: enums enums_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ENUMS"
    ADD CONSTRAINT "ENUMS_PKEY" PRIMARY KEY ("ENUM_ID");


--
-- Name: object_types object_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "OBJECT_TYPES"
    ADD CONSTRAINT "OBJECT_TYPES_PKEY" PRIMARY KEY ("OBJECT_TYPE_ID");


--
-- Name: objects objects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "OBJECTS"
    ADD CONSTRAINT "OBJECTS_PKEY" PRIMARY KEY ("OBJECT_ID");

/* 
--
-- Name: attr_object_types attr_object_types_attr_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ATTR_OBJECT_TYPES"
    ADD CONSTRAINT "ATTR_OBJECT_TYPES_ATTR_ID_FKEY" FOREIGN KEY ("ATTR_ID") REFERENCES "ATTRIBUTES"("ATTR_ID");


--
-- Name: attr_object_types attr_object_types_object_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ATTR_OBJECT_TYPES"
    ADD CONSTRAINT "ATTR_OBJECT_TYPES_OBJECT_TYPE_ID_FKEY" FOREIGN KEY ("OBJECT_TYPE_ID") REFERENCES "OBJECT_TYPES"("OBJECT_TYPE_ID");


--
-- Name: attributes attributes_attr_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ATTRIBUTES"
    ADD CONSTRAINT "ATTRIBUTES_ATTR_TYPE_ID_FKEY" FOREIGN KEY ("ATTR_TYPE_ID") REFERENCES "ATTR_TYPES"("ATTR_TYPE_ID");


--
-- Name: enums enums_enum_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ENUMS"
    ADD CONSTRAINT "ENUMS_ENUM_TYPE_ID_FKEY" FOREIGN KEY ("ENUM_TYPE_ID") REFERENCES "ENUM_TYPES"("ENUM_TYPE_ID");


--
-- Name: object_types object_types_parent_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "OBJECT_TYPES"
    ADD CONSTRAINT "OBJECT_TYPES_PARENT_TYPE_ID_FKEY" FOREIGN KEY ("PARENT_TYPE_ID") REFERENCES "OBJECT_TYPES"("OBJECT_TYPE_ID");


--
-- Name: objects objects_object_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "OBJECTS"
    ADD CONSTRAINT "OBJECTS_OBJECT_TYPE_ID_FKEY" FOREIGN KEY ("OBJECT_TYPE_ID") REFERENCES "OBJECT_TYPES"("OBJECT_TYPE_ID");


--
-- Name: objects objects_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "OBJECTS"
    ADD CONSTRAINT "OBJECTS_PARENT_ID_FKEY" FOREIGN KEY ("PARENT_ID") REFERENCES "OBJECTS"("OBJECT_ID");


--
-- Name: parameters parameters_attr_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "PARAMETERS"
    ADD CONSTRAINT "PARAMETERS_ATTR_ID_FKEY" FOREIGN KEY ("ATTR_ID") REFERENCES "ATTRIBUTES"("ATTR_ID");


--
-- Name: parameters parameters_enum_value_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "PARAMETERS"
    ADD CONSTRAINT "PARAMETERS_ENUM_VALUE_FKEY" FOREIGN KEY ("ENUM_VALUE") REFERENCES "ENUMS"("ENUM_ID");


--
-- Name: parameters parameters_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "PARAMETERS"
    ADD CONSTRAINT "PARAMETERS_OBJECT_ID_FKEY" FOREIGN KEY ("OBJECT_ID") REFERENCES "OBJECTS"("OBJECT_ID");


--
-- PostgreSQL database dump complete
--

 */
