CREATE TABLE regions
(
    region_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    region_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT regions_pkey PRIMARY KEY (region_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE regions
    OWNER to postgres;

	
CREATE TABLE houses
(
    house_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    added_date timestamp(6) without time zone,
    price double precision,
    rooms integer,
    size character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    region_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT houses_pkey PRIMARY KEY (house_id),
    CONSTRAINT fkhycgy7nyyiaxatl5paqar6efk FOREIGN KEY (region_id)
        REFERENCES regions (region_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE houses
    OWNER to postgres;	
	
INSERT INTO
    regions(region_id, region_name)
    VALUES
	('DLN_WROC_C', 'Dolnośląskie - Wrocław centrum'),
	('DLN_WROC_PC','Dolnośląskie - Wrocław poza centrum'),
	('DLN_POZA_WROC', 'Dolnośląskie - poza Wrocławiem'),
	('SL_POL', 'Śląskie - południe'),
	('SL_KATO', 'Śląskie - Katowice'),
	('SL_PN', 'Śląskie - północ'),
	('M_WAW_CE', 'Mazowieckie - Warszawa Centrum'),
	('M_WAW_W', 'Mazowieckie - Warszawa wschód'),
	('M_WAW_Z', 'Mazowieckie - Warszaawa - zachód'),
	('LUBL', 'Lubelskie - Lublin'),
	('LUBL_INNE', 'Lubelskie - poza Lublinem'),
	('ZPOM', 'Zachodniopomorskie'),
	('LUBSK', 'Lubuskie')
	;