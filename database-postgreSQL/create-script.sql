--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: alerts; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE alerts (
    patient_id integer NOT NULL,
    acknowledged boolean,
    alert_id integer NOT NULL
);


ALTER TABLE public.alerts OWNER TO capstone;

--
-- Name: alerts_alert_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE alerts_alert_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alerts_alert_id_seq OWNER TO capstone;

--
-- Name: alerts_alert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE alerts_alert_id_seq OWNED BY alerts.alert_id;


--
-- Name: checkin; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE checkin (
    checkin_id integer NOT NULL,
    throat_pain_level integer NOT NULL,
    eat_dificulty_level integer NOT NULL,
    checkin_timestamp timestamp without time zone,
    patient_id integer
);


ALTER TABLE public.checkin OWNER TO capstone;

--
-- Name: checkin_checkin_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE checkin_checkin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checkin_checkin_id_seq OWNER TO capstone;

--
-- Name: checkin_checkin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE checkin_checkin_id_seq OWNED BY checkin.checkin_id;


--
-- Name: checkin_medication; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE checkin_medication (
    checkin_id integer NOT NULL,
    medication_id integer NOT NULL,
    medication_token boolean NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.checkin_medication OWNER TO capstone;

--
-- Name: checkin_medication_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE checkin_medication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checkin_medication_id_seq OWNER TO capstone;

--
-- Name: checkin_medication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE checkin_medication_id_seq OWNED BY checkin_medication.id;


--
-- Name: doctor; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE doctor (
    doctor_id integer NOT NULL,
    first_name text,
    last_name text,
    password text
);


ALTER TABLE public.doctor OWNER TO capstone;

--
-- Name: doctor_doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE doctor_doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.doctor_doctor_id_seq OWNER TO capstone;

--
-- Name: doctor_doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE doctor_doctor_id_seq OWNED BY doctor.doctor_id;


--
-- Name: medication; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE medication (
    medication_name text NOT NULL,
    description text,
    medication_id integer NOT NULL
);


ALTER TABLE public.medication OWNER TO capstone;

--
-- Name: medication_medication_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE medication_medication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medication_medication_id_seq OWNER TO capstone;

--
-- Name: medication_medication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE medication_medication_id_seq OWNED BY medication.medication_id;


--
-- Name: patient; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE patient (
    medical_record_number text NOT NULL,
    first_name text,
    last_name text,
    birthdate date,
    password text,
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL
);


ALTER TABLE public.patient OWNER TO capstone;

--
-- Name: patient_medication; Type: TABLE; Schema: public; Owner: capstone; Tablespace: 
--

CREATE TABLE patient_medication (
    patient_id integer NOT NULL,
    medication_id integer NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.patient_medication OWNER TO capstone;

--
-- Name: patient_medication_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE patient_medication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.patient_medication_id_seq OWNER TO capstone;

--
-- Name: patient_medication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE patient_medication_id_seq OWNED BY patient_medication.id;


--
-- Name: patient_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: capstone
--

CREATE SEQUENCE patient_patient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.patient_patient_id_seq OWNER TO capstone;

--
-- Name: patient_patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capstone
--

ALTER SEQUENCE patient_patient_id_seq OWNED BY patient.patient_id;


--
-- Name: alert_id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY alerts ALTER COLUMN alert_id SET DEFAULT nextval('alerts_alert_id_seq'::regclass);


--
-- Name: checkin_id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY checkin ALTER COLUMN checkin_id SET DEFAULT nextval('checkin_checkin_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY checkin_medication ALTER COLUMN id SET DEFAULT nextval('checkin_medication_id_seq'::regclass);


--
-- Name: doctor_id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY doctor ALTER COLUMN doctor_id SET DEFAULT nextval('doctor_doctor_id_seq'::regclass);


--
-- Name: medication_id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY medication ALTER COLUMN medication_id SET DEFAULT nextval('medication_medication_id_seq'::regclass);


--
-- Name: patient_id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY patient ALTER COLUMN patient_id SET DEFAULT nextval('patient_patient_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY patient_medication ALTER COLUMN id SET DEFAULT nextval('patient_medication_id_seq'::regclass);


--
-- Data for Name: alerts; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO alerts VALUES (3, false, 6);
INSERT INTO alerts VALUES (3, false, 7);
INSERT INTO alerts VALUES (3, false, 8);
INSERT INTO alerts VALUES (3, false, 9);
INSERT INTO alerts VALUES (3, false, 10);
INSERT INTO alerts VALUES (3, false, 11);
INSERT INTO alerts VALUES (3, false, 12);
INSERT INTO alerts VALUES (3, false, 13);
INSERT INTO alerts VALUES (3, false, 14);
INSERT INTO alerts VALUES (3, true, 17);
INSERT INTO alerts VALUES (3, true, 16);
INSERT INTO alerts VALUES (3, true, 15);


--
-- Name: alerts_alert_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('alerts_alert_id_seq', 17, true);


--
-- Data for Name: checkin; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO checkin VALUES (30, 3, 2, '2014-11-26 08:04:53.805', 3);
INSERT INTO checkin VALUES (31, 3, 2, '2014-11-26 08:05:11.628', 1);
INSERT INTO checkin VALUES (32, 1, 1, '2014-11-26 08:05:30.864', 2);
INSERT INTO checkin VALUES (33, 1, 1, '2014-11-26 08:06:02.306', 4);
INSERT INTO checkin VALUES (34, 2, 2, '2014-11-26 08:06:16.129', 5);
INSERT INTO checkin VALUES (35, 1, 1, '2014-11-25 19:21:34.881', 1);
INSERT INTO checkin VALUES (36, 2, 2, '2014-11-25 19:21:49.256', 2);
INSERT INTO checkin VALUES (37, 3, 3, '2014-11-25 19:22:03.225', 3);
INSERT INTO checkin VALUES (38, 2, 3, '2014-11-25 19:22:16.054', 4);
INSERT INTO checkin VALUES (39, 1, 2, '2014-11-25 19:22:33.466', 5);
INSERT INTO checkin VALUES (40, 2, 2, '2014-11-26 16:39:16.532', 3);
INSERT INTO checkin VALUES (44, 3, 3, '2014-11-28 21:25:08.228', 3);
INSERT INTO checkin VALUES (43, 3, 3, '2014-11-28 21:17:57.884', 3);
INSERT INTO checkin VALUES (42, 3, 3, '2014-11-26 16:59:45.894', 3);
INSERT INTO checkin VALUES (41, 3, 3, '2014-11-26 16:39:36.23', 3);
INSERT INTO checkin VALUES (45, 3, 3, '2014-11-30 22:42:25.925', 3);
INSERT INTO checkin VALUES (46, 3, 3, '2014-11-30 22:42:58.709', 3);
INSERT INTO checkin VALUES (49, 3, 3, '2014-11-30 22:52:52.446', 3);
INSERT INTO checkin VALUES (47, 3, 3, '2014-11-30 22:43:12.184', 3);
INSERT INTO checkin VALUES (48, 3, 3, '2014-11-30 22:45:08.787', 3);
INSERT INTO checkin VALUES (50, 3, 3, '2014-11-30 22:53:14.297', 3);
INSERT INTO checkin VALUES (51, 3, 3, '2014-11-30 23:11:59.17', 3);
INSERT INTO checkin VALUES (52, 3, 3, '2014-11-30 23:18:35.313', 3);
INSERT INTO checkin VALUES (53, 3, 3, '2014-11-30 23:29:40.527', 3);
INSERT INTO checkin VALUES (54, 3, 3, '2014-11-30 23:30:34.978', 3);
INSERT INTO checkin VALUES (55, 3, 3, '2014-11-30 23:33:19.572', 3);
INSERT INTO checkin VALUES (56, 3, 3, '2014-11-30 23:38:26.281', 3);
INSERT INTO checkin VALUES (57, 3, 3, '2014-11-30 23:39:07.641', 3);
INSERT INTO checkin VALUES (58, 3, 3, '2014-11-30 23:49:49.152', 3);
INSERT INTO checkin VALUES (59, 3, 3, '2014-11-30 23:51:24.014', 3);
INSERT INTO checkin VALUES (60, 3, 3, '2014-11-30 23:53:06.078', 3);
INSERT INTO checkin VALUES (61, 3, 3, '2014-11-30 23:56:00.399', 3);


--
-- Name: checkin_checkin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('checkin_checkin_id_seq', 61, true);


--
-- Data for Name: checkin_medication; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO checkin_medication VALUES (30, 1, false, 9);
INSERT INTO checkin_medication VALUES (30, 3, true, 10);
INSERT INTO checkin_medication VALUES (30, 5, false, 11);
INSERT INTO checkin_medication VALUES (31, 1, true, 12);
INSERT INTO checkin_medication VALUES (32, 2, true, 13);
INSERT INTO checkin_medication VALUES (32, 3, true, 14);
INSERT INTO checkin_medication VALUES (33, 2, true, 15);
INSERT INTO checkin_medication VALUES (33, 4, true, 16);
INSERT INTO checkin_medication VALUES (34, 5, false, 17);
INSERT INTO checkin_medication VALUES (35, 1, false, 18);
INSERT INTO checkin_medication VALUES (36, 2, true, 19);
INSERT INTO checkin_medication VALUES (36, 3, false, 20);
INSERT INTO checkin_medication VALUES (37, 1, false, 21);
INSERT INTO checkin_medication VALUES (37, 3, true, 22);
INSERT INTO checkin_medication VALUES (37, 5, true, 23);
INSERT INTO checkin_medication VALUES (38, 2, true, 24);
INSERT INTO checkin_medication VALUES (38, 4, false, 25);
INSERT INTO checkin_medication VALUES (39, 5, true, 26);
INSERT INTO checkin_medication VALUES (41, 1, false, 27);
INSERT INTO checkin_medication VALUES (41, 3, true, 28);
INSERT INTO checkin_medication VALUES (41, 5, true, 29);
INSERT INTO checkin_medication VALUES (42, 1, true, 30);
INSERT INTO checkin_medication VALUES (42, 3, true, 31);
INSERT INTO checkin_medication VALUES (42, 5, false, 32);
INSERT INTO checkin_medication VALUES (43, 2, false, 33);
INSERT INTO checkin_medication VALUES (43, 3, true, 34);
INSERT INTO checkin_medication VALUES (43, 4, true, 35);
INSERT INTO checkin_medication VALUES (43, 5, false, 36);
INSERT INTO checkin_medication VALUES (44, 2, false, 37);
INSERT INTO checkin_medication VALUES (44, 3, true, 38);
INSERT INTO checkin_medication VALUES (44, 4, true, 39);
INSERT INTO checkin_medication VALUES (44, 5, true, 40);
INSERT INTO checkin_medication VALUES (45, 1, false, 41);
INSERT INTO checkin_medication VALUES (45, 2, false, 42);
INSERT INTO checkin_medication VALUES (45, 5, false, 43);
INSERT INTO checkin_medication VALUES (46, 1, false, 44);
INSERT INTO checkin_medication VALUES (46, 2, false, 45);
INSERT INTO checkin_medication VALUES (46, 3, false, 46);
INSERT INTO checkin_medication VALUES (46, 4, false, 47);
INSERT INTO checkin_medication VALUES (47, 1, false, 48);
INSERT INTO checkin_medication VALUES (47, 2, false, 49);
INSERT INTO checkin_medication VALUES (47, 3, false, 50);
INSERT INTO checkin_medication VALUES (47, 4, false, 51);
INSERT INTO checkin_medication VALUES (48, 1, false, 52);
INSERT INTO checkin_medication VALUES (48, 2, false, 53);
INSERT INTO checkin_medication VALUES (48, 3, false, 54);
INSERT INTO checkin_medication VALUES (48, 4, false, 55);
INSERT INTO checkin_medication VALUES (49, 1, false, 56);
INSERT INTO checkin_medication VALUES (49, 2, false, 57);
INSERT INTO checkin_medication VALUES (49, 3, false, 58);
INSERT INTO checkin_medication VALUES (49, 4, false, 59);
INSERT INTO checkin_medication VALUES (50, 1, false, 60);
INSERT INTO checkin_medication VALUES (50, 2, false, 61);
INSERT INTO checkin_medication VALUES (50, 3, false, 62);
INSERT INTO checkin_medication VALUES (50, 4, false, 63);
INSERT INTO checkin_medication VALUES (51, 1, false, 64);
INSERT INTO checkin_medication VALUES (51, 2, false, 65);
INSERT INTO checkin_medication VALUES (51, 3, false, 66);
INSERT INTO checkin_medication VALUES (51, 4, false, 67);
INSERT INTO checkin_medication VALUES (52, 1, false, 68);
INSERT INTO checkin_medication VALUES (52, 2, false, 69);
INSERT INTO checkin_medication VALUES (52, 3, false, 70);
INSERT INTO checkin_medication VALUES (52, 4, false, 71);
INSERT INTO checkin_medication VALUES (53, 1, false, 72);
INSERT INTO checkin_medication VALUES (53, 2, false, 73);
INSERT INTO checkin_medication VALUES (53, 3, false, 74);
INSERT INTO checkin_medication VALUES (53, 4, false, 75);
INSERT INTO checkin_medication VALUES (54, 1, false, 76);
INSERT INTO checkin_medication VALUES (54, 2, false, 77);
INSERT INTO checkin_medication VALUES (54, 3, false, 78);
INSERT INTO checkin_medication VALUES (54, 4, false, 79);
INSERT INTO checkin_medication VALUES (55, 1, false, 80);
INSERT INTO checkin_medication VALUES (55, 2, false, 81);
INSERT INTO checkin_medication VALUES (55, 3, false, 82);
INSERT INTO checkin_medication VALUES (55, 4, false, 83);
INSERT INTO checkin_medication VALUES (56, 1, false, 84);
INSERT INTO checkin_medication VALUES (56, 2, false, 85);
INSERT INTO checkin_medication VALUES (56, 3, false, 86);
INSERT INTO checkin_medication VALUES (56, 4, false, 87);
INSERT INTO checkin_medication VALUES (57, 1, false, 88);
INSERT INTO checkin_medication VALUES (57, 2, false, 89);
INSERT INTO checkin_medication VALUES (57, 3, false, 90);
INSERT INTO checkin_medication VALUES (57, 4, false, 91);
INSERT INTO checkin_medication VALUES (58, 1, false, 92);
INSERT INTO checkin_medication VALUES (58, 2, true, 93);
INSERT INTO checkin_medication VALUES (58, 3, true, 94);
INSERT INTO checkin_medication VALUES (58, 4, false, 95);
INSERT INTO checkin_medication VALUES (59, 1, false, 96);
INSERT INTO checkin_medication VALUES (59, 2, false, 97);
INSERT INTO checkin_medication VALUES (59, 3, false, 98);
INSERT INTO checkin_medication VALUES (59, 4, false, 99);
INSERT INTO checkin_medication VALUES (60, 1, false, 100);
INSERT INTO checkin_medication VALUES (60, 2, false, 101);
INSERT INTO checkin_medication VALUES (60, 3, false, 102);
INSERT INTO checkin_medication VALUES (60, 4, false, 103);
INSERT INTO checkin_medication VALUES (61, 1, false, 104);
INSERT INTO checkin_medication VALUES (61, 2, false, 105);
INSERT INTO checkin_medication VALUES (61, 3, false, 106);
INSERT INTO checkin_medication VALUES (61, 4, false, 107);


--
-- Name: checkin_medication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('checkin_medication_id_seq', 107, true);


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO doctor VALUES (1, 'Marcos', 'Moreno', '123445');
INSERT INTO doctor VALUES (2, 'John', 'Raymond', '987654');


--
-- Name: doctor_doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('doctor_doctor_id_seq', 2, true);


--
-- Data for Name: medication; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO medication VALUES ('amifostine', 'A drug used as a chemoprotective drug to control some of the side effects of chemotherapy and radiation therapy.', 1);
INSERT INTO medication VALUES ('ibritumomab tiuxetan', 'A monoclonal antibody that is used to treat certain types of B-cell non-Hodgkin lymphoma and is being studied in the treatment and detection of other types of B-cell tumors. Monoclonal antibodies are made in the laboratory and can locate and bind to substances in the body, including cancer cells. Ibritumomab binds to the protein called CD20, which is found on B cells. It is linked to the compound tiuxetan. This allows certain radioisotopes to be attached before it is given to a patient. It is a type of monoclonal antibody-chelator conjugate. Also called Zevalin.', 2);
INSERT INTO medication VALUES ('5-fluorouracil', 'A drug used to treat cancers of the breast, stomach, and pancreas, and certain types of colorectal and head and neck cancers. It is also used in a cream to treat basal cell skin cancer and actinic keratosis (a skin condition that may become cancer). It is being studied in the treatment of other conditions and types of cancer. 5-fluorouracil stops cells from making DNA and it may kill cancer cells. It is a type of antimetabolite. Also called 5-FU, Adrucil, Efudex, Fluoroplex, and fluorouracil.', 3);
INSERT INTO medication VALUES ('cisplatin', 'A drug used to treat malignant mesothelioma, non-small cell lung cancer, squamous cell carcinoma of the head and neck, and cancers of the bladder, cervix, ovaries, and testes. It is used in patients whose cancer cannot be treated with or has not gotten better with other anticancer treatment. It is also being studied in the treatment of other types of cancer. Cisplatin contains the metal platinum. It kills cancer cells by damaging their DNA and stopping them from dividing. It is a type of DNA crosslinking agent. Also called Platinol and Platinol-AQ.', 4);
INSERT INTO medication VALUES ('samarium 153', 'A radioactive substance used in the treatment of bone cancer and bone metastases (cancers that have spread from the original tumor to the bone). Samarium 153 is a radioactive form of the element samarium. It collects in bone, where it releases radiation that may kill cancer cells. It is a type of radioisotope.', 5);
INSERT INTO medication VALUES ('strontium-89', 'A radioactive form of the metal strontium that is taken up by a part of growing bone. It is being studied in the treatment of bone pain caused by some types of cancer.', 6);


--
-- Name: medication_medication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('medication_medication_id_seq', 1, false);


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO patient VALUES ('efgh5678', 'Thelma', 'Lyons', '1965-11-12', 'qwerty', 2, 2);
INSERT INTO patient VALUES ('ijkl9012', 'Malcolm', 'Barnett', '1978-07-02', 'zxcvb', 1, 3);
INSERT INTO patient VALUES ('mnop3456', 'Kelli', 'Foster', '1992-02-22', 'poiuy', 1, 4);
INSERT INTO patient VALUES ('abcd1234', 'Conraad', 'Gibson', '1950-05-15', 'asdfgh', 1, 1);
INSERT INTO patient VALUES ('qrst7890', 'Mario', 'Alexander', '1986-08-17', 'lkjhg', 2, 5);


--
-- Data for Name: patient_medication; Type: TABLE DATA; Schema: public; Owner: capstone
--

INSERT INTO patient_medication VALUES (1, 1, 1);
INSERT INTO patient_medication VALUES (2, 2, 2);
INSERT INTO patient_medication VALUES (3, 3, 3);
INSERT INTO patient_medication VALUES (4, 4, 4);
INSERT INTO patient_medication VALUES (5, 5, 5);
INSERT INTO patient_medication VALUES (2, 3, 6);
INSERT INTO patient_medication VALUES (3, 1, 7);
INSERT INTO patient_medication VALUES (4, 2, 9);
INSERT INTO patient_medication VALUES (4, 1, 11);
INSERT INTO patient_medication VALUES (4, 5, 12);
INSERT INTO patient_medication VALUES (3, 2, 13);
INSERT INTO patient_medication VALUES (3, 5, 15);
INSERT INTO patient_medication VALUES (3, 4, 16);


--
-- Name: patient_medication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('patient_medication_id_seq', 16, true);


--
-- Name: patient_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: capstone
--

SELECT pg_catalog.setval('patient_patient_id_seq', 1, false);


--
-- Name: alerts_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY alerts
    ADD CONSTRAINT alerts_pkey PRIMARY KEY (alert_id);


--
-- Name: checkin_medication_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY checkin_medication
    ADD CONSTRAINT checkin_medication_pkey PRIMARY KEY (id);


--
-- Name: checkin_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY checkin
    ADD CONSTRAINT checkin_pkey PRIMARY KEY (checkin_id);


--
-- Name: medication_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY medication
    ADD CONSTRAINT medication_pkey PRIMARY KEY (medication_id);


--
-- Name: patient_medical_record_number_key; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_medical_record_number_key UNIQUE (medical_record_number);


--
-- Name: patient_medication_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY patient_medication
    ADD CONSTRAINT patient_medication_pkey PRIMARY KEY (id);


--
-- Name: patient_pkey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);


--
-- Name: primaryKey; Type: CONSTRAINT; Schema: public; Owner: capstone; Tablespace: 
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT "primaryKey" PRIMARY KEY (doctor_id);


--
-- Name: alerts_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY alerts
    ADD CONSTRAINT alerts_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patient(patient_id);


--
-- Name: checkin_medication_checkin_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY checkin_medication
    ADD CONSTRAINT checkin_medication_checkin_id_fkey FOREIGN KEY (checkin_id) REFERENCES checkin(checkin_id);


--
-- Name: checkin_medication_medication_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY checkin_medication
    ADD CONSTRAINT checkin_medication_medication_id_fkey FOREIGN KEY (medication_id) REFERENCES medication(medication_id);


--
-- Name: checkin_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY checkin
    ADD CONSTRAINT checkin_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patient(patient_id);


--
-- Name: patient_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id);


--
-- Name: patient_medication_medication_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY patient_medication
    ADD CONSTRAINT patient_medication_medication_id_fkey FOREIGN KEY (medication_id) REFERENCES medication(medication_id);


--
-- Name: patient_medication_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capstone
--

ALTER TABLE ONLY patient_medication
    ADD CONSTRAINT patient_medication_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patient(patient_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

