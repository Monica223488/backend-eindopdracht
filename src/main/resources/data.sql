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


INSERT INTO orders (
    id,
    paper_type,
    amount,
    price,
    status,
    size,
    customer_id,
    appointment_id
)
VALUES
    (
        '44444444-4444-4444-4444-444444444444',
        'white',
        2,
        19.99,
        'ordered',
        'A4',
        '11111111-1111-1111-1111-111111111111',
        '66666666-6666-6666-6666-666666666666'
    ),
    (
        '55555555-5555-5555-5555-555555555555',
        'uncoated',
        1,
        15.99,
        'ordered',
        'A5',
        '22222222-2222-2222-2222-222222222222',
        '77777777-7777-7777-7777-777777777777'
    ),
    (
        'aaaaaaaa-1111-1111-1111-111111111111',
        'glossy',
        1,
        24.99,
        'ordered',
        'A4',
        '33333333-3333-3333-3333-333333333333',
        NULL
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


INSERT INTO photobook (
    id,
    title,
    created_at,
    approved_at,
    sent_to_printer_at,
    last_feedback,
    pages,
    status,
    order_id
)
VALUES
    (
        '11111111-1111-1111-1111-111111111111',
        'Fotoboek - Uploading',
        '2026-08-01T10:00:00Z',
        NULL,
        NULL,
        NULL,
        24,
        'UPLOADING',
        NULL
    ),
    (
        '22222222-2222-2222-2222-222222222222',
        'Fotoboek - Designing',
        '2026-08-01T10:00:00Z',
        NULL,
        NULL,
        NULL,
        24,
        'DESIGNING',
        NULL
    ),
    (
        '33333333-3333-3333-3333-333333333333',
        'Fotoboek - Ready for review',
        '2026-08-01T10:00:00Z',
        NULL,
        NULL,
        NULL,
        24,
        'READY_FOR_REVIEW',
        NULL
    ),
    (
        '44444444-4444-4444-4444-444444444444',
        'Fotoboek - Approved',
        '2026-08-01T10:00:00Z',
        '2026-08-05T10:00:00Z',
        NULL,
        NULL,
        24,
        'APPROVED',
        NULL
    ),
    (
        '55555555-5555-5555-5555-555555555555',
        'Fotoboek - Rejected',
        '2026-08-01T10:00:00Z',
        NULL,
        NULL,
        NULL,
        24,
        'REJECTED',
        NULL
    ),
    (
        '66666666-6666-6666-6666-666666666666',
        'Fotoboek - Sent to printer',
        '2026-08-01T10:00:00Z',
        '2026-08-04T10:00:00Z',
        '2026-08-06T10:00:00Z',
        NULL,
        24,
        'SENT_TO_PRINTER',
        NULL
    ),
    (
        '77777777-7777-7777-7777-777777777777',
        'Fotoboek - Printed',
        '2026-08-01T10:00:00Z',
        '2026-08-04T10:00:00Z',
        '2026-08-06T10:00:00Z',
        NULL,
        24,
        'PRINTED',
        NULL
    ),
    (
        '88888888-8888-8888-8888-888888888888',
        'Fotoboek - Ready for pickup',
        '2026-08-01T10:00:00Z',
        '2026-08-04T10:00:00Z',
        '2026-08-06T10:00:00Z',
        NULL,
        24,
        'READY_FOR_PICKUP',
        NULL
    );


INSERT INTO receipts (
    id,
    summary,
    customer_information,
    appointment_information,
    order_id
)
VALUES
    (
        'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
        'Bon voor bestelling van 2 fotoboeken, A4, wit papier',
        'Jan Jansen - 0612345678',
        '12-01-2026, 10:00',
        '44444444-4444-4444-4444-444444444444'
    ),
    (
        'ffffffff-ffff-ffff-ffff-ffffffffffff',
        'Bon voor bestelling van 1 fotoboek, A5, uncoated papier',
        'Henk de Wit - 0674838291',
        '04-02-2026, 11:00',
        '55555555-5555-5555-5555-555555555555'
    );

