begin;

--drop schema if exists client_workspace cascade;
create schema client_workspace;

CREATE TABLE client_workspace.t_ads
( 
	ad_pk		serial, -- id
	ad_name		character varying (200) not null, -- Название
	ad_price	numeric not null, --Цена
	ad_dsc		character varying (1000) not null, -- Описание
	ad_date		timestamp, --Дата создания объявления
	PRIMARY KEY (ad_pk)
);

CREATE TABLE client_workspace.t_links
( 
	lnk_pk			serial, -- id
	lnk_ad_fk		integer, -- Ссылка на объявление
	lnk_value		character varying (255) not null, -- Значение ссылки
	lnk_gen			boolean, -- Главное фото или нет
	PRIMARY KEY (lnk_pk),
	FOREIGN KEY (lnk_ad_fk) REFERENCES client_workspace.t_ads (ad_pk)
);

end;