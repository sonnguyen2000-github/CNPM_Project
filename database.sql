--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2021-01-05 21:59:00

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
-- TOC entry 204 (class 1259 OID 16603)
-- Name: Drink; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Drink" (
    madouong character varying NOT NULL,
    ten character varying NOT NULL,
    gia integer NOT NULL
);


ALTER TABLE public."Drink" OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16591)
-- Name: History; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."History" (
    username character varying NOT NULL,
    id_order character varying NOT NULL,
    date date NOT NULL
);


ALTER TABLE public."History" OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16585)
-- Name: Order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Order" (
    magoi character varying NOT NULL,
    maban character varying NOT NULL,
    madouong character varying NOT NULL,
    soluong integer NOT NULL
);


ALTER TABLE public."Order" OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16579)
-- Name: Table; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Table" (
    maban character varying NOT NULL,
    tenban character varying NOT NULL,
    ghichu character varying
);


ALTER TABLE public."Table" OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16597)
-- Name: User; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."User" (
    username character varying NOT NULL,
    password character varying NOT NULL,
    priority integer NOT NULL,
    fullname character varying NOT NULL,
    dob date NOT NULL,
    address character varying NOT NULL,
    phone character varying NOT NULL
);


ALTER TABLE public."User" OWNER TO postgres;

--
-- TOC entry 3016 (class 0 OID 16603)
-- Dependencies: 204
-- Data for Name: Drink; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Drink" (madouong, ten, gia) FROM stdin;
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
-- TOC entry 3014 (class 0 OID 16591)
-- Dependencies: 202
-- Data for Name: History; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."History" (username, id_order, date) FROM stdin;
nguyenson_01	nguyenson_016031910	2020-11-19
\.


--
-- TOC entry 3013 (class 0 OID 16585)
-- Dependencies: 201
-- Data for Name: Order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Order" (magoi, maban, madouong, soluong) FROM stdin;
\.


--
-- TOC entry 3012 (class 0 OID 16579)
-- Dependencies: 200
-- Data for Name: Table; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Table" (maban, tenban, ghichu) FROM stdin;
B1	Ban 1	\N
B2	Ban 2	\N
B4	Ban 4	\N
B5	Ban 5	\N
B6	Ban 6	\N
B3	Ban 3	\N
B7	Ban 7	
\.


--
-- TOC entry 3015 (class 0 OID 16597)
-- Dependencies: 203
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."User" (username, password, priority, fullname, dob, address, phone) FROM stdin;
admin	admin	1	admin	2000-01-20		
nguyenson_03	123	3		1970-01-01		
nguyenson_01	1	3	Nguyễn Hồng Sơn	1970-01-01		
nguyenson_02	123	2	Nguyễn Hồng Sơn	1998-12-14	Hà Nội	
s	123456	3		1999-12-14		
\.


--
-- TOC entry 2870 (class 2606 OID 16610)
-- Name: Table Ban_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Table"
    ADD CONSTRAINT "Ban_pkey" PRIMARY KEY (maban);


--
-- TOC entry 2876 (class 2606 OID 16612)
-- Name: User Nguoidung_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "Nguoidung_pkey" PRIMARY KEY (username);


--
-- TOC entry 2878 (class 2606 OID 16614)
-- Name: Drink Thuc_don_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Drink"
    ADD CONSTRAINT "Thuc_don_pkey" PRIMARY KEY (madouong);


--
-- TOC entry 2872 (class 2606 OID 16616)
-- Name: Order goimon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order"
    ADD CONSTRAINT goimon_pkey PRIMARY KEY (magoi, maban);


--
-- TOC entry 2874 (class 2606 OID 16618)
-- Name: History ls_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."History"
    ADD CONSTRAINT ls_pkey PRIMARY KEY (id_order);


--
-- TOC entry 2879 (class 2606 OID 16619)
-- Name: Order goimon_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order"
    ADD CONSTRAINT goimon_fkey FOREIGN KEY (maban) REFERENCES public."Table"(maban);


--
-- TOC entry 2880 (class 2606 OID 16624)
-- Name: Order goimon_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order"
    ADD CONSTRAINT goimon_fkey1 FOREIGN KEY (madouong) REFERENCES public."Drink"(madouong) NOT VALID;


--
-- TOC entry 2881 (class 2606 OID 16629)
-- Name: History ls_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."History"
    ADD CONSTRAINT ls_fkey FOREIGN KEY (username) REFERENCES public."User"(username) ON DELETE CASCADE;


-- Completed on 2021-01-05 21:59:00

--
-- PostgreSQL database dump complete
--

