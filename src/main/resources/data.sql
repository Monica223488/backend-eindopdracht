insert into orders(paper_type, amount, price, status, size)
values ('white', 2, 19.99, 'ordered', 'A4'),
       ('uncoated', 1, 15.99, 'ordered', 'A5');

insert into customers(name, phone_number)
values ('Jan Jansen', '0612345678'),
       ('Henk de Wit', '0674838291'),
       ('Tamara Stoel', '0698765432');

insert into appointments(Appointment_date, Appointment_time)
values ('2026-01-12', '10:00:00'),
        ('2026-02-04', '11:00:00');
