--
-- PostgreSQL database dump
--

-- Dumped from database version 14.8
-- Dumped by pg_dump version 14.8

-- Completed on 2025-06-6-06-28 13:43:48

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
-- TOC entry 210 (class 1259 OID 33638)
-- Name: bodegas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bodegas (
    id_bodega bigint NOT NULL,
    nombre_bodega character varying(255),
    ubicacion character varying(255)
);


ALTER TABLE public.bodegas OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 33637)
-- Name: bodegas_id_bodega_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bodegas_id_bodega_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bodegas_id_bodega_seq OWNER TO postgres;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 209
-- Name: bodegas_id_bodega_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bodegas_id_bodega_seq OWNED BY public.bodegas.id_bodega;


--
-- TOC entry 212 (class 1259 OID 33647)
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    id_cliente bigint NOT NULL,
    direccion character varying(255),
    email character varying(255) NOT NULL,
    nombre character varying(255),
    telefono character varying(255)
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 33646)
-- Name: clientes_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clientes_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 211
-- Name: clientes_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_id_cliente_seq OWNED BY public.clientes.id_cliente;


--
-- TOC entry 214 (class 1259 OID 33656)
-- Name: logistica_maritima; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.logistica_maritima (
    id_logistica_m bigint NOT NULL,
    cantidad integer,
    valor_descuento numeric(19,2),
    fecha_entrega date NOT NULL,
    fecha_registro date NOT NULL,
    numero_flota character varying(255),
    numero_guia character varying(10),
    precio_envio numeric(19,2),
    precio_normal numeric(19,2),
    id_cliente bigint NOT NULL,
    id_producto bigint NOT NULL,
    id_bodega bigint NOT NULL
);


ALTER TABLE public.logistica_maritima OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 33655)
-- Name: logistica_maritima_id_logistica_m_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.logistica_maritima_id_logistica_m_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.logistica_maritima_id_logistica_m_seq OWNER TO postgres;

--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 213
-- Name: logistica_maritima_id_logistica_m_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.logistica_maritima_id_logistica_m_seq OWNED BY public.logistica_maritima.id_logistica_m;


--
-- TOC entry 216 (class 1259 OID 33663)
-- Name: logistica_terrestre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.logistica_terrestre (
    id_logistica_t bigint NOT NULL,
    cantidad integer,
    valor_descuento numeric(19,2),
    fecha_entrega date NOT NULL,
    fecha_registro date NOT NULL,
    numero_guia character varying(10),
    placa_vehiculo character varying(255) NOT NULL,
    precio_envio numeric(19,2) NOT NULL,
    precio_normal numeric(19,2),
    id_bodega bigint NOT NULL,
    id_cliente bigint NOT NULL,
    id_producto bigint NOT NULL
);


ALTER TABLE public.logistica_terrestre OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 33662)
-- Name: logistica_terrestre_id_logistica_t_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.logistica_terrestre_id_logistica_t_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.logistica_terrestre_id_logistica_t_seq OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 215
-- Name: logistica_terrestre_id_logistica_t_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.logistica_terrestre_id_logistica_t_seq OWNED BY public.logistica_terrestre.id_logistica_t;


--
-- TOC entry 218 (class 1259 OID 33670)
-- Name: productos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.productos (
    id_produto bigint NOT NULL,
    descripcion character varying(255),
    nombre_producto character varying(255)
);


ALTER TABLE public.productos OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 33669)
-- Name: productos_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.productos_id_produto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.productos_id_produto_seq OWNER TO postgres;

--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 217
-- Name: productos_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.productos_id_produto_seq OWNED BY public.productos.id_produto;


--
-- TOC entry 220 (class 1259 OID 33679)
-- Name: puertos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.puertos (
    id_puerto bigint NOT NULL,
    nombre_puerto character varying(255),
    ubicacion character varying(255)
);


ALTER TABLE public.puertos OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 33678)
-- Name: puertos_id_puerto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.puertos_id_puerto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.puertos_id_puerto_seq OWNER TO postgres;

--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 219
-- Name: puertos_id_puerto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.puertos_id_puerto_seq OWNED BY public.puertos.id_puerto;


--
-- TOC entry 222 (class 1259 OID 33688)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    nombre character varying(20)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 33687)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 221
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 224 (class 1259 OID 33695)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id bigint NOT NULL,
    apellido character varying(255),
    email character varying(255),
    enabled boolean,
    nombre character varying(255),
    password character varying(64),
    username character varying(20)
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 33694)
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO postgres;

--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 223
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- TOC entry 225 (class 1259 OID 33703)
-- Name: usuarios_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios_roles (
    usuario_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.usuarios_roles OWNER TO postgres;

--
-- TOC entry 3203 (class 2604 OID 33641)
-- Name: bodegas id_bodega; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bodegas ALTER COLUMN id_bodega SET DEFAULT nextval('public.bodegas_id_bodega_seq'::regclass);


--
-- TOC entry 3204 (class 2604 OID 33650)
-- Name: clientes id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN id_cliente SET DEFAULT nextval('public.clientes_id_cliente_seq'::regclass);


--
-- TOC entry 3205 (class 2604 OID 33659)
-- Name: logistica_maritima id_logistica_m; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima ALTER COLUMN id_logistica_m SET DEFAULT nextval('public.logistica_maritima_id_logistica_m_seq'::regclass);


--
-- TOC entry 3206 (class 2604 OID 33666)
-- Name: logistica_terrestre id_logistica_t; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre ALTER COLUMN id_logistica_t SET DEFAULT nextval('public.logistica_terrestre_id_logistica_t_seq'::regclass);


--
-- TOC entry 3207 (class 2604 OID 33673)
-- Name: productos id_produto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos ALTER COLUMN id_produto SET DEFAULT nextval('public.productos_id_produto_seq'::regclass);


--
-- TOC entry 3208 (class 2604 OID 33682)
-- Name: puertos id_puerto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puertos ALTER COLUMN id_puerto SET DEFAULT nextval('public.puertos_id_puerto_seq'::regclass);


--
-- TOC entry 3209 (class 2604 OID 33691)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3210 (class 2604 OID 33698)
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- TOC entry 3212 (class 2606 OID 33645)
-- Name: bodegas bodegas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bodegas
    ADD CONSTRAINT bodegas_pkey PRIMARY KEY (id_bodega);


--
-- TOC entry 3214 (class 2606 OID 33654)
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 3218 (class 2606 OID 33661)
-- Name: logistica_maritima logistica_maritima_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima
    ADD CONSTRAINT logistica_maritima_pkey PRIMARY KEY (id_logistica_m);


--
-- TOC entry 3222 (class 2606 OID 33668)
-- Name: logistica_terrestre logistica_terrestre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre
    ADD CONSTRAINT logistica_terrestre_pkey PRIMARY KEY (id_logistica_t);


--
-- TOC entry 3226 (class 2606 OID 33677)
-- Name: productos productos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos
    ADD CONSTRAINT productos_pkey PRIMARY KEY (id_produto);


--
-- TOC entry 3228 (class 2606 OID 33686)
-- Name: puertos puertos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.puertos
    ADD CONSTRAINT puertos_pkey PRIMARY KEY (id_puerto);


--
-- TOC entry 3230 (class 2606 OID 33693)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3216 (class 2606 OID 33707)
-- Name: clientes uk_1c96wv36rk2hwui7qhjks3mvg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT uk_1c96wv36rk2hwui7qhjks3mvg UNIQUE (email);


--
-- TOC entry 3220 (class 2606 OID 33709)
-- Name: logistica_maritima uk_exwe6v3xfeph23pijerp5rk8k; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima
    ADD CONSTRAINT uk_exwe6v3xfeph23pijerp5rk8k UNIQUE (numero_guia);


--
-- TOC entry 3234 (class 2606 OID 33715)
-- Name: usuarios uk_kfsp0s1tflm1cwlj8idhqsad0; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT uk_kfsp0s1tflm1cwlj8idhqsad0 UNIQUE (email);


--
-- TOC entry 3232 (class 2606 OID 33713)
-- Name: roles uk_ldv0v52e0udsh2h1rs0r0gw1n; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_ldv0v52e0udsh2h1rs0r0gw1n UNIQUE (nombre);


--
-- TOC entry 3236 (class 2606 OID 33717)
-- Name: usuarios uk_m2dvbwfge291euvmk6vkkocao; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT uk_m2dvbwfge291euvmk6vkkocao UNIQUE (username);


--
-- TOC entry 3224 (class 2606 OID 33711)
-- Name: logistica_terrestre uk_oyluwdyadvbk26bjiuicet9k6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre
    ADD CONSTRAINT uk_oyluwdyadvbk26bjiuicet9k6 UNIQUE (numero_guia);


--
-- TOC entry 3240 (class 2606 OID 33719)
-- Name: usuarios_roles ukqjaspm7473pnu9y4jxhrds8r2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT ukqjaspm7473pnu9y4jxhrds8r2 UNIQUE (usuario_id, role_id);


--
-- TOC entry 3238 (class 2606 OID 33702)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 33730)
-- Name: logistica_maritima fk1rfgub1bib6rracey9r37h8wr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima
    ADD CONSTRAINT fk1rfgub1bib6rracey9r37h8wr FOREIGN KEY (id_bodega) REFERENCES public.puertos(id_puerto);


--
-- TOC entry 3241 (class 2606 OID 33720)
-- Name: logistica_maritima fk6x37521xfa8svopnab0b3u679; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima
    ADD CONSTRAINT fk6x37521xfa8svopnab0b3u679 FOREIGN KEY (id_cliente) REFERENCES public.clientes(id_cliente);


--
-- TOC entry 3245 (class 2606 OID 33740)
-- Name: logistica_terrestre fk9qta9q6o0chelch2wvqshmysn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre
    ADD CONSTRAINT fk9qta9q6o0chelch2wvqshmysn FOREIGN KEY (id_cliente) REFERENCES public.clientes(id_cliente);


--
-- TOC entry 3247 (class 2606 OID 33750)
-- Name: usuarios_roles fkihom0uklpkfpffipxpoyf7b74; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT fkihom0uklpkfpffipxpoyf7b74 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3246 (class 2606 OID 33745)
-- Name: logistica_terrestre fklbdeyq4t5b7kfilmey6fhk1ux; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre
    ADD CONSTRAINT fklbdeyq4t5b7kfilmey6fhk1ux FOREIGN KEY (id_producto) REFERENCES public.productos(id_produto);


--
-- TOC entry 3242 (class 2606 OID 33725)
-- Name: logistica_maritima fkomay0urj7n8k1xxdnfor2ei8l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_maritima
    ADD CONSTRAINT fkomay0urj7n8k1xxdnfor2ei8l FOREIGN KEY (id_producto) REFERENCES public.productos(id_produto);


--
-- TOC entry 3244 (class 2606 OID 33735)
-- Name: logistica_terrestre fkpyj715ngw735l9gpgmuyv8pp0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logistica_terrestre
    ADD CONSTRAINT fkpyj715ngw735l9gpgmuyv8pp0 FOREIGN KEY (id_bodega) REFERENCES public.bodegas(id_bodega);


--
-- TOC entry 3248 (class 2606 OID 33755)
-- Name: usuarios_roles fkqcxu02bqipxpr7cjyj9dmhwec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT fkqcxu02bqipxpr7cjyj9dmhwec FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);


-- Completed on 2025-06-6-06-28 13:43:48

--
-- PostgreSQL database dump complete
--

