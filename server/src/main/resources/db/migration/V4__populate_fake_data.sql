CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- Users

CREATE TEMP TABLE temp_names (
    id UUID,
    name VARCHAR(255),
    email VARCHAR(255)
);

WITH first_names AS (
    SELECT unnest(ARRAY[
        'Alice', 'Bob', 'Carlos', 'Diana', 'Eva', 'Frank', 'Grace', 'Hank',
        'Isla', 'Jake', 'Karen', 'Liam', 'Mona', 'Nate', 'Olga', 'Paul',
        'Quinn', 'Rita', 'Sam', 'Tina', 'Uma', 'Victor', 'Wendy', 'Xander',
        'Yara', 'Zack'
    ]) AS first_name
),
last_names AS (
    SELECT unnest(ARRAY[
        'Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Miller', 'Davis',
        'Garcia', 'Rodriguez', 'Wilson', 'Martinez', 'Anderson', 'Taylor',
        'Thomas', 'Hernandez', 'Moore', 'Martin', 'Lee', 'Perez', 'Thompson'
    ]) AS last_name
)

INSERT INTO temp_names (id, name, email)
SELECT
    uuid_generate_v4() AS id,
    CONCAT(fn.first_name, ' ', ln.last_name) AS name,
    LOWER(CONCAT(fn.first_name, '.', ln.last_name, '@example.com')) AS email
FROM generate_series(1, 100) AS gs
CROSS JOIN (SELECT first_name FROM first_names LIMIT 100) fn
CROSS JOIN (SELECT last_name FROM last_names LIMIT 100) ln
ORDER BY random();

INSERT INTO users (id, name, email, age, date_of_birth, phone_number, gender, theme)
SELECT
    tn.id,
    tn.name,
    tn.email,
    (FLOOR(18 + random() * (65 - 18 + 1)))::int AS age,
    TO_DATE(
        CONCAT(
            (CURRENT_DATE - INTERVAL '1 year' * (FLOOR(18 + random() * (65 - 18 + 1)))::int)::date, ' ',
            (FLOOR(random() * 12) + 1)::int, '-',
            (FLOOR(random() * 31) + 1)::int
        ), 'YYYY-MM-DD') AS date_of_birth,
    LPAD(FLOOR(random() * 10000000000)::text, 10, '0') AS phone_number,
    CASE
        WHEN tn.name LIKE '%Alice%' OR tn.name LIKE '%Diana%' OR tn.name LIKE '%Eva%' THEN 'FEMALE'
        WHEN tn.name LIKE '%Bob%' OR tn.name LIKE '%Carlos%' OR tn.name LIKE '%Frank%' THEN 'MALE'
        ELSE 'OTHER'
    END AS gender,
    CASE WHEN random() < 0.5 THEN 'LIGHT_MODE' ELSE 'DARK_MODE' END AS theme
FROM temp_names tn;

-- Job Postings
INSERT INTO job_postings (id, name)
SELECT uuid_generate_v4(), service_name
FROM unnest(ARRAY[
    'IT Support Services',
    'Foreign Language Teachers',
    'Cleaning Services',
    'Plumbing Services',
    'Electrician Services',
    'Transport and Logistics',
    'Home Tutoring',
    'Catering Services',
    'Graphic Design',
    'Web Development',
    'Digital Marketing',
    'Legal Consulting',
    'Accounting and Bookkeeping',
    'Carpentry Services',
    'Home Renovation',
    'Personal Training',
    'Pet Grooming',
    'Elderly Care Services',
    'Babysitting Services',
    'Photography',
    'Video Editing',
    'Content Writing',
    'SEO Optimization',
    'Translation Services',
    'Event Planning',
    'Interior Design',
    'Mobile App Development',
    'Data Analysis',
    'Social Media Management',
    'Cybersecurity Services'
]) AS service_name;
