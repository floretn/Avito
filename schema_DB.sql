begin;

--drop schema if exists investor_workspace cascade;
create schema client_workspace;

CREATE TABLE client_workspace.t_applications
( 
	app_pk		serial, -- id
	app_name	character varying (200) not null, -- Название
	app_dsc		character varying (1000) not null, -- Описание
	PRIMARY KEY (app_pk)
);

CREATE TABLE client_workspace.t_links
( 
	lnk_pk			serial, -- id
	lnk_app_fk		integer, -- Ссылка на заявку
	lnk_value		character varying (255) not null, -- Значение ссылки
	PRIMARY KEY (lnk_pk),
	FOREIGN KEY (lnk_app_fk) REFERENCES client_workspace.t_applications (app_pk)
);

end;