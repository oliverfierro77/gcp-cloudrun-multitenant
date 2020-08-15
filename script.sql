CREATE TABLE public."order" (
	order_number varchar NOT NULL,
	order_created_date timestamp NULL,
	order_country_description varchar NULL
);

--CHILE
INSERT INTO public."order" (order_number,order_created_date,order_country_description) VALUES 
('0000000001','2020-08-08 00:00:00.000','CHILE')
,('0000000002','2020-08-08 00:00:00.000','CHILE')
,('0000000003','2020-06-01 00:00:00.000','CHILE')
;

--ARGENTINA
INSERT INTO public."order" (order_number,order_created_date,order_country_description) VALUES 
('0000000001','2020-08-08 00:00:00.000','ARGENTINA')
,('0000000002','2020-08-08 00:00:00.000','ARGENTINA')
,('0000000003','2020-06-01 00:00:00.000','ARGENTINA')
;