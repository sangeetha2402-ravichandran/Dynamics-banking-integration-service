INSERT INTO customers
(customer_id, first_name, last_name, email, phone, customer_type, vip)
VALUES
    ('CUST1001', 'John', 'Smith', 'john.smith@email.com', '0211234567', 'PREMIUM', true);

INSERT INTO customers
(customer_id, first_name, last_name, email, phone, customer_type, vip)
VALUES
    ('CUST1002', 'Mary', 'Jones', 'mary.jones@email.com', '0219876543', 'STANDARD', false);


INSERT INTO transactions
(transaction_id, customer_id, account_number, amount, transaction_type, description, transaction_date, status)
VALUES
    ('TXN90001', 'CUST1001', 'ACC1001', 1250.00, 'TRANSFER', 'Online transfer', '2026-09-14 09:20:00', 'COMPLETED');

INSERT INTO transactions
(transaction_id, customer_id, account_number, amount, transaction_type, description, transaction_date, status)
VALUES
    ('TXN90002', 'CUST1001', 'ACC1001', 85.50, 'CARD', 'Supermarket purchase', '2026-09-13 18:10:00', 'COMPLETED');

INSERT INTO loans (
    loan_id,
    customer_id,
    loan_type,
    loan_amount,
    outstanding_amount,
    interest_rate,
    start_date,
    end_date,
    status
)
VALUES (
           'LOAN1001',
           'CUST1001',
           'HOME_LOAN',
           450000.00,
           320000.00,
           6.49,
           DATE '2022-01-15',
           DATE '2042-01-15',
           'ACTIVE'
       );

INSERT INTO loans (
    loan_id,
    customer_id,
    loan_type,
    loan_amount,
    outstanding_amount,
    interest_rate,
    start_date,
    end_date,
    status
)
VALUES (
           'LOAN1002',
           'CUST1001',
           'PERSONAL_LOAN',
           25000.00,
           12000.00,
           8.25,
           DATE '2024-06-10',
           DATE '2029-06-10',
           'ACTIVE'
       );

INSERT INTO loans (
    loan_id,
    customer_id,
    loan_type,
    loan_amount,
    outstanding_amount,
    interest_rate,
    start_date,
    end_date,
    status
)
VALUES (
           'LOAN1003',
           'CUST1002',
           'CAR_LOAN',
           45000.00,
           28500.00,
           7.15,
           DATE '2025-02-01',
           DATE '2030-02-01',
           'ACTIVE'
       );