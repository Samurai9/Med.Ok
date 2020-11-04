--
-- PostgreSQL database dump
--

-- Dumped from database version 13.0
-- Dumped by pg_dump version 13.0

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

--
-- Name: product_categories; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.product_categories AS ENUM (
    'LIQUID',
    'HONEYCOMB'
);


ALTER TYPE public.product_categories OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: basket_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.basket_product (
    basket_id integer NOT NULL,
    product_id integer NOT NULL,
    count integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.basket_product OWNER TO postgres;

--
-- Name: baskets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.baskets (
    basket_id integer NOT NULL,
    user_id integer
);


ALTER TABLE public.baskets OWNER TO postgres;

--
-- Name: baskets_basket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.baskets_basket_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.baskets_basket_id_seq OWNER TO postgres;

--
-- Name: baskets_basket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.baskets_basket_id_seq OWNED BY public.baskets.basket_id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    product_id integer NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL,
    remaining integer DEFAULT 0 NOT NULL,
    size double precision,
    price double precision NOT NULL,
    rating double precision DEFAULT 0 NOT NULL,
    picture character varying,
    product_category public.product_categories NOT NULL,
    reviews_count integer DEFAULT 0 NOT NULL,
    CONSTRAINT products_price_check CHECK ((price >= (0)::double precision)),
    CONSTRAINT products_rating_check CHECK (((rating >= (0)::double precision) AND (rating <= (5)::double precision))),
    CONSTRAINT products_remaining_check CHECK ((remaining >= 0)),
    CONSTRAINT products_size_check CHECK ((size >= (0)::double precision))
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Name: products_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_product_id_seq OWNER TO postgres;

--
-- Name: products_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_product_id_seq OWNED BY public.products.product_id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
    review_id integer NOT NULL,
    author_id integer NOT NULL,
    text character varying NOT NULL,
    rating integer NOT NULL,
    "time" timestamp without time zone NOT NULL,
    product_id integer NOT NULL,
    author_name character varying NOT NULL,
    CONSTRAINT reviews_rating_check CHECK ((((rating)::double precision >= (0)::double precision) AND ((rating)::double precision <= (5)::double precision)))
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- Name: reviews_review_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_review_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reviews_review_id_seq OWNER TO postgres;

--
-- Name: reviews_review_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_review_id_seq OWNED BY public.reviews.review_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    discount integer DEFAULT 0,
    CONSTRAINT users_discount_check CHECK ((discount >= 0)),
    CONSTRAINT users_name_check CHECK (((length((name)::text) >= 2) AND (length((name)::text) <= 30)))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- Name: baskets basket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baskets ALTER COLUMN basket_id SET DEFAULT nextval('public.baskets_basket_id_seq'::regclass);


--
-- Name: products product_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN product_id SET DEFAULT nextval('public.products_product_id_seq'::regclass);


--
-- Name: reviews review_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN review_id SET DEFAULT nextval('public.reviews_review_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- Data for Name: basket_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.basket_product (basket_id, product_id, count) FROM stdin;
1	2	1
12	2	11
16	2	1
1	3	1
1	1	1
2	4	1
2	2	1
\.


--
-- Data for Name: baskets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.baskets (basket_id, user_id) FROM stdin;
1	1
2	14
6	23
9	26
10	27
12	29
13	30
14	31
15	32
16	33
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (product_id, name, description, remaining, size, price, rating, picture, product_category, reviews_count) FROM stdin;
5	Сотовый мед	Сотовый мед высшего качества	10	0.3	1299	4.8	\\resources\\img\\ProductImages\\5.png	HONEYCOMB	5
3	Липовый мед	Литр липового мёда, собранного на нашей личной пасеке 	0	1	1299	0	\N	LIQUID	0
4	Гречишный мед	Три литра гречшного мёда, собранного на нашей личной пасеке 	6	3	2999	3.25	\\resources\\img\\ProductImages\\4.png	LIQUID	4
2	Цветочный мед	Литр цветочного мёда, собранного на нашей личной пасеке 	1	1	1299	5	\\resources\\img\\ProductImages\\2.png	LIQUID	1
1	Липовый мед	Литр липового мёда, собранного на нашей личной пасеке 	3	0.5	599	4	\\resources\\img\\ProductImages\\1.png	LIQUID	2
\.


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reviews (review_id, author_id, text, rating, "time", product_id, author_name) FROM stdin;
1	16	Хороший мед, всем советую	5	2020-10-25 12:03:11.191	2	Erik
18	16	Тестовый обзор для оценки	4	2020-10-25 14:42:11.533	4	Erik
19	16	Тестовый обзор для оценки	3	2020-10-25 14:42:16.44	4	Erik
20	16	Тестовый обзор для оценки	2	2020-10-25 14:42:21.607	4	Erik
21	16	Тестовый обзор для оценки	4	2020-10-25 14:42:37.377	4	Erik
22	16	Отличный мед, но цена чуть-чуть кусается	4	2020-10-25 15:49:28.773	1	Erik
24	16	Отличный мед, но цена чуть-чуть кусается	4	2020-10-25 17:19:53.014	1	Erik
26	16	Тестовый обзор для оценки	5	2020-11-01 10:26:16.662	5	Erik
27	16	Тестовый обзор для оценки	4	2020-11-01 10:26:44.76	5	Erik
28	16	Тестовый обзор для оценки	5	2020-11-01 10:26:49.164	5	Erik
29	16	Тестовый обзор для оценки	5	2020-11-01 10:26:51.986	5	Erik
30	16	Тестовый обзор для оценки	5	2020-11-01 10:26:54.769	5	Erik
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, name, email, password, discount) FROM stdin;
33	Тестов123	test6@mail.ru	E46641B992781E4FB2786A8CB1FA697D	0
16	Erik	testemail@mail.ru	B75DEE53D2ABB6AE7869E73DC102F528	0
1	Test2	example2@mail.ru	F90EDC3CE197A032676B5537730F7C01	0
26	Абв	test3@mail.ru	1FF5B293F5E4463347B9AFB3394C9CBC	0
27	ФСи	test4@mail.ru	30BAA3B4A1A50662BE3D374034CE0054	0
14	Erik	naserik99@mail.ru	F2DF3E9934B683F33CFD8F974C3A8599	50
23	Tester	tester228@toster.kek	757BE3FE74717BBDFE68E2926E745FE0	0
29	Ffs	onewaydream@mail.ru	3EA4ACC4FA9F8F899AED9544DB2A565A	0
30	Ваы	onewaydreams@mail.ru	C768041E4ED58D91336BCE780726798F	0
31	FSlkj	sonewaydream@mail.ru	B5EC6361306E332289ACF33448FBC081	0
32	Тест	osnewaydream@mail.ru	154E1E3D4D612003F59C189222AC43A3	0
\.


--
-- Name: baskets_basket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.baskets_basket_id_seq', 17, true);


--
-- Name: products_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_product_id_seq', 5, true);


--
-- Name: reviews_review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reviews_review_id_seq', 30, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 34, true);


--
-- Name: baskets baskets_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baskets
    ADD CONSTRAINT baskets_pk PRIMARY KEY (basket_id);


--
-- Name: products products_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pk PRIMARY KEY (product_id);


--
-- Name: reviews reviews_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pk PRIMARY KEY (review_id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- Name: baskets_basket_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX baskets_basket_id_uindex ON public.baskets USING btree (basket_id);


--
-- Name: basket_product basket_product_baskets_basket_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket_product
    ADD CONSTRAINT basket_product_baskets_basket_id_fk FOREIGN KEY (basket_id) REFERENCES public.baskets(basket_id);


--
-- Name: basket_product basket_product_products_product_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket_product
    ADD CONSTRAINT basket_product_products_product_id_fk FOREIGN KEY (product_id) REFERENCES public.products(product_id);


--
-- Name: baskets baskets_users_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baskets
    ADD CONSTRAINT baskets_users_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- Name: reviews reviews_products_product_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_products_product_id_fk FOREIGN KEY (product_id) REFERENCES public.products(product_id);


--
-- Name: reviews reviews_users_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_users_user_id_fk FOREIGN KEY (author_id) REFERENCES public.users(user_id);


--
-- PostgreSQL database dump complete
--

