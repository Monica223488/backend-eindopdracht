INSERT INTO roles (id, name)
VALUES
    ('88888888-8888-8888-8888-888888888888', 'CUSTOMER'),
    ('99999999-9999-9999-9999-999999999999', 'DESIGNER'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'EMPLOYEE');

INSERT INTO customers (id, name, phone_number)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Jan Jansen', '0612345678'),
    ('22222222-2222-2222-2222-222222222222', 'Henk de Wit', '0674838291'),
    ('33333333-3333-3333-3333-333333333333', 'Tamara Stoel', '0698765432');

INSERT INTO orders (
    id,
    paper_type,
    amount,
    price,
    status,
    size,
    customer_id
)
VALUES
    (
        '44444444-4444-4444-4444-444444444444',
        'white',
        2,
        19.99,
        'ordered',
        'A4',
        '11111111-1111-1111-1111-111111111111'
    ),
    (
        '55555555-5555-5555-5555-555555555555',
        'uncoated',
        1,
        15.99,
        'ordered',
        'A5',
        '22222222-2222-2222-2222-222222222222'
    );

INSERT INTO appointments (
    id,
    appointment_date,
    appointment_time
)
VALUES
    (
        '66666666-6666-6666-6666-666666666666',
        '2026-01-12',
        '10:00:00'
    ),
    (
        '77777777-7777-7777-7777-777777777777',
        '2026-02-04',
        '11:00:00'
    );

INSERT INTO users (
    id,
    email,
    password,
    name
)
VALUES
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'employee@test.nl',
        '$2a$10$KejtNKEexb0aT6jv1F4EP.6xHoh0z97uLMCOrZxTqESG5Wpjq6/S',
        'Test Employee'
    ),
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'designer@test.nl',
        '$2a$10$KejtNKEexb0aT6jv1F4EP.6xHoh0z97uLMCOrZxTqESG5Wpjq6/S',
        'Test Designer'
    ),
    (
        'dddddddd-dddd-dddd-dddd-dddddddddddd',
        'customer@test.nl',
        '$2a$10$KejtNKEexb0aT6jv1F4EP.6xHoh0z97uLMCOrZxTqESG5Wpjq6/S',
        'Test Customer'
    );

INSERT INTO user_roles (
    user_id,
    role_id
)
VALUES
    (
        'dddddddd-dddd-dddd-dddd-dddddddddddd',
        '88888888-8888-8888-8888-888888888888'
    ),
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        '99999999-9999-9999-9999-999999999999'
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
    );


