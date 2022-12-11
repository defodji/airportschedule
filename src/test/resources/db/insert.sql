INSERT INTO departure (flight_number,actual_time,aircraft,airline,scheduled_time,city_to) VALUES
	 ('WJ3457',CURRENT_TIME + interval '20 minutes','B720','WestJet',CURRENT_TIME,'Jakarta');
INSERT INTO departure (flight_number,actual_time,aircraft,airline,scheduled_time,city_to) VALUES
	 ('QQ7657',CURRENT_TIME,'A321','Qatar Airways',CURRENT_TIME - interval '14 minutes','Doha, Qatar');
INSERT INTO departure (flight_number,actual_time,aircraft,airline,scheduled_time,city_to) VALUES
	 ('QA7777',CURRENT_TIME - interval '3 minutes','A321','Qantas Airways',CURRENT_TIME + interval '14 minutes','Perth, Australia');


INSERT INTO public.arrival (flight_number,scheduled_time,aircraft,airline,city_from,actual_time) VALUES
	 ('KL9976',CURRENT_TIME,'A320','KLM','Johannesburg, South Africa', CURRENT_TIME + interval '20 minutes');
INSERT INTO public.arrival (flight_number,scheduled_time,aircraft,airline,city_from,actual_time) VALUES
	 ('CA1176',CURRENT_TIME - interval '14 minutes','B626','Canada Airlines','Toronto, Canada', CURRENT_TIME);
INSERT INTO public.arrival (flight_number,scheduled_time, aircraft,airline,city_from, actual_time) VALUES
	 ('DL2176',CURRENT_TIME + interval '14 minutes','B626','Delta Airlines','Chicago, USA',CURRENT_TIME - interval '3 minutes');
