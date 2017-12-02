SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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

SET default_tablespace = '';

SET default_with_oids = false;

CREATE SEQUENCE id_sequence;

CREATE OR REPLACE FUNCTION id_generator(OUT result bigint) AS $$
DECLARE
  seq_id bigint;
  now_millis bigint;
BEGIN
  SELECT nextval('id_sequence') % 1024 INTO seq_id;
  SELECT FLOOR(EXTRACT(EPOCH FROM clock_timestamp()) * 1000) INTO now_millis;

  result:= now_millis << 20;
  result:= result | 1010111100011010010;
  result:= result | seq_id;
END;
$$ LANGUAGE PLPGSQL;

--
-- Name: attr_object_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE attr_object_types (
    object_type_id bigint NOT NULL,
    attr_id bigint NOT NULL
);


ALTER TABLE attr_object_types OWNER TO postgres;

--
-- Name: attr_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE attr_types (
    attr_type_id bigint,
    name character varying(200) NOT NULL
);


ALTER TABLE attr_types OWNER TO postgres;

--
-- Name: attributes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE attributes (
    attr_id bigint,
    name character varying(200) NOT NULL,
    attr_type_id bigint NOT NULL
);


ALTER TABLE attributes OWNER TO postgres;

--
-- Name: enum_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE enum_types (
    enum_type_id bigint,
    name character varying(200) NOT NULL
);


ALTER TABLE enum_types OWNER TO postgres;

--
-- Name: enums; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE enums (
    enum_id bigint,
    name character varying(200) NOT NULL,
    enum_type_id bigint NOT NULL
);


ALTER TABLE enums OWNER TO postgres;

--
-- Name: object_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE object_types (
    name character varying(200) NOT NULL,
    object_type_id bigint,
    parent_type_id bigint
);


ALTER TABLE object_types OWNER TO postgres;

--
-- Name: objects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE objects (
    name character varying(200) NOT NULL,
    object_id bigint,
    parent_id bigint,
    object_type_id bigint NOT NULL
);


ALTER TABLE objects OWNER TO postgres;

--
-- Name: parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE parameters (
    object_id bigint NOT NULL,
    attr_id bigint NOT NULL,
    text_value character varying(200),
    date_value date,
    reference_value bigint,
    enum_value bigint
);


ALTER TABLE parameters OWNER TO postgres;

--
-- Data for Name: attr_object_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attr_object_types (object_type_id, attr_id) FROM stdin;
\.


--
-- Data for Name: attr_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attr_types (attr_type_id, name) FROM stdin;
\.


--
-- Data for Name: attributes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attributes (name, attr_id, attr_type_id) FROM stdin;
\.


--
-- Data for Name: enum_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY enum_types (name, enum_type_id) FROM stdin;
\.


--
-- Data for Name: enums; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY enums (name, enum_id, enum_type_id) FROM stdin;
\.


--
-- Data for Name: object_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY object_types (name, object_type_id, parent_type_id) FROM stdin;
\.


--
-- Data for Name: objects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY objects (name, object_id, parent_id, object_type_id) FROM stdin;
\.


--
-- Data for Name: parameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY parameters (object_id, attr_id, text_value, date_value, reference_value, enum_value) FROM stdin;
\.

--
-- Name: attr_types attr_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attr_types
    ADD CONSTRAINT attr_types_pkey PRIMARY KEY (attr_type_id);


--
-- Name: attributes attributes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attributes
    ADD CONSTRAINT attributes_pkey PRIMARY KEY (attr_id);


--
-- Name: enum_types enum_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enum_types
    ADD CONSTRAINT enum_types_pkey PRIMARY KEY (enum_type_id);


--
-- Name: enums enums_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enums
    ADD CONSTRAINT enums_pkey PRIMARY KEY (enum_id);


--
-- Name: object_types object_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY object_types
    ADD CONSTRAINT object_types_pkey PRIMARY KEY (object_type_id);


--
-- Name: objects objects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY objects
    ADD CONSTRAINT objects_pkey PRIMARY KEY (object_id);


--
-- Name: attr_object_types attr_object_types_attr_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attr_object_types
    ADD CONSTRAINT attr_object_types_attr_id_fkey FOREIGN KEY (attr_id) REFERENCES attributes(attr_id);


--
-- Name: attr_object_types attr_object_types_object_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attr_object_types
    ADD CONSTRAINT attr_object_types_object_type_id_fkey FOREIGN KEY (object_type_id) REFERENCES object_types(object_type_id);


--
-- Name: attributes attributes_attr_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attributes
    ADD CONSTRAINT attributes_attr_type_id_fkey FOREIGN KEY (attr_type_id) REFERENCES attr_types(attr_type_id);


--
-- Name: enums enums_enum_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enums
    ADD CONSTRAINT enums_enum_type_id_fkey FOREIGN KEY (enum_type_id) REFERENCES enum_types(enum_type_id);


--
-- Name: object_types object_types_parent_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY object_types
    ADD CONSTRAINT object_types_parent_type_id_fkey FOREIGN KEY (parent_type_id) REFERENCES object_types(object_type_id);


--
-- Name: objects objects_object_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY objects
    ADD CONSTRAINT objects_object_type_id_fkey FOREIGN KEY (object_type_id) REFERENCES object_types(object_type_id);


--
-- Name: objects objects_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY objects
    ADD CONSTRAINT objects_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES objects(object_id);


--
-- Name: parameters parameters_attr_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_attr_id_fkey FOREIGN KEY (attr_id) REFERENCES attributes(attr_id);


--
-- Name: parameters parameters_enum_value_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_enum_value_fkey FOREIGN KEY (enum_value) REFERENCES enums(enum_id);


--
-- Name: parameters parameters_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_object_id_fkey FOREIGN KEY (object_id) REFERENCES objects(object_id);

--
-- Adding basic rows
--

INSERT INTO object_types (name, object_type_id) VALUES ('User', 1);
INSERT INTO object_types (name, object_type_id) VALUES ('Order', 2);
INSERT INTO object_types (name, object_type_id) VALUES ('Food', 3);

INSERT INTO attr_types (attr_type_id, name) VALUES (101, 'Text');
INSERT INTO attr_types (attr_type_id, name) VALUES (102, 'Date');
INSERT INTO attr_types (attr_type_id, name) VALUES (103, 'Reference');
INSERT INTO attr_types (attr_type_id, name) VALUES (104, 'Number');
INSERT INTO attr_types (attr_type_id, name) VALUES (105, 'Enum value');

INSERT INTO attributes (attr_id, name, attr_type_id) VALUES (11, 'Username', 101);
INSERT INTO attributes (attr_id, name, attr_type_id) VALUES (12, 'Password hash', 101);
INSERT INTO attributes (attr_id, name, attr_type_id) VALUES (13, 'Role', 101);

--
-- PostgreSQL database dump complete
--

