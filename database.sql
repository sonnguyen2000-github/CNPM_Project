--
-- PostgreSQL database dump
--

-- Dumped from database version 13.0
-- Dumped by pg_dump version 13.0

-- Started on 2020-11-20 08:53:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 200 (class 1259 OID 17024)
-- Name: Ban; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ban" (
    maban character varying NOT NULL,
    tenban character varying NOT NULL,
    ghichu character varying
);


ALTER TABLE public."Ban" OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 17032)
-- Name: Goimon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Goimon" (
    magoi character varying NOT NULL,
    maban character varying NOT NULL,
    madouong character varying NOT NULL,
    soluong integer NOT NULL
);


ALTER TABLE public."Goimon" OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17890)
-- Name: Lichsu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Lichsu" (
    username character varying NOT NULL,
    id_order character varying NOT NULL,
    date date NOT NULL
);


ALTER TABLE public."Lichsu" OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 17882)
-- Name: Nguoidung; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Nguoidung" (
    username character varying NOT NULL,
    password character varying NOT NULL,
    priority integer NOT NULL,
    fullname character varying NOT NULL,
    dob date NOT NULL,
    address character varying NOT NULL,
    phone integer NOT NULL
);


ALTER TABLE public."Nguoidung" OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17045)
-- Name: Thucdon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Thucdon" (
    madouong character varying NOT NULL,
    ten character varying NOT NULL,
    gia integer NOT NULL
);


ALTER TABLE public."Thucdon" OWNER TO postgres;

--
-- TOC entry 3012 (class 0 OID 17024)
-- Dependencies: 200
-- Data for Name: Ban; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Ban" (maban, tenban, ghichu) FROM stdin;
B1	Ban 1	\N
B2	Ban 2	\N
B4	Ban 4	\N
B5	Ban 5	\N
B6	Ban 6	\N
B3	Ban 3	\N
B7	Ban 7	
\.


--
-- TOC entry 3013 (class 0 OID 17032)
-- Dependencies: 201
-- Data for Name: Goimon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Goimon" (magoi, maban, madouong, soluong) FROM stdin;
\.


--
-- TOC entry 3016 (class 0 OID 17890)
-- Dependencies: 204
-- Data for Name: Lichsu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Lichsu" (username, id_order, date) FROM stdin;
nguyenson_01	nguyenson_016031910	2020-11-19
\.


--
-- TOC entry 3015 (class 0 OID 17882)
-- Dependencies: 203
-- Data for Name: Nguoidung; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Nguoidung" (username, password, priority, fullname, dob, address, phone) FROM stdin;
admin	admin	1	admin	2000-01-20		0
nguyenson_03	123	3		1970-01-01		0
nguyenson_01	1	3	Nguyễn Hồng Sơn	1970-01-01		916264096
nguyenson_02	123	2	Nguyễn Hồng Sơn	1998-12-14	Hà Nội	0
\.


--
-- TOC entry 3014 (class 0 OID 17045)
-- Dependencies: 202
-- Data for Name: Thucdon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Thucdon" (madouong, ten, gia) FROM stdin;
CFS	Coffee sua	30
SN	Sua nong	15
SRG	Tra gung	15
CXR	Chanh xa	20
CFR	Lipton	10
CFB	Tra sua tran chau	35
CAF	Cam ep	13
CFD	Coffee den	35
\.


--
-- TOC entry 2870 (class 2606 OID 17031)
-- Name: Ban Ban_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ban"
    ADD CONSTRAINT "Ban_pkey" PRIMARY KEY (maban);


--
-- TOC entry 2876 (class 2606 OID 17889)
-- Name: Nguoidung Nguoidung_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Nguoidung"
    ADD CONSTRAINT "Nguoidung_pkey" PRIMARY KEY (username);


--
-- TOC entry 2874 (class 2606 OID 17052)
-- Name: Thucdon Thuc_don_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Thucdon"
    ADD CONSTRAINT "Thuc_don_pkey" PRIMARY KEY (madouong);


--
-- TOC entry 2872 (class 2606 OID 17059)
-- Name: Goimon goimon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Goimon"
    ADD CONSTRAINT goimon_pkey PRIMARY KEY (magoi, maban);


--
-- TOC entry 2878 (class 2606 OID 17933)
-- Name: Lichsu ls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Lichsu"
    ADD CONSTRAINT ls_pkey PRIMARY KEY (id_order);


--
-- TOC entry 2879 (class 2606 OID 17040)
-- Name: Goimon goimon_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Goimon"
    ADD CONSTRAINT goimon_fkey FOREIGN KEY (maban) REFERENCES public."Ban"(maban);


--
-- TOC entry 2880 (class 2606 OID 17053)
-- Name: Goimon goimon_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Goimon"
    ADD CONSTRAINT goimon_fkey1 FOREIGN KEY (madouong) REFERENCES public."Thucdon"(madouong) NOT VALID;


--
-- TOC entry 2881 (class 2606 OID 17927)
-- Name: Lichsu ls_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Lichsu"
    ADD CONSTRAINT ls_fkey FOREIGN KEY (username) REFERENCES public."Nguoidung"(username) ON DELETE CASCADE;


-- Completed on 2020-11-20 08:53:10

--
-- PostgreSQL database dump complete
--

