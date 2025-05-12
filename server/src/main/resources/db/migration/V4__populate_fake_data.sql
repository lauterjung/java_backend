-- Users
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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

INSERT INTO users (id, name, email, age, date_of_birth)
SELECT
    uuid_generate_v4() AS id,
    CONCAT(f.first_name, ' ', l.last_name) AS name,
    LOWER(CONCAT(f.first_name, '.', l.last_name, '@example.com')) AS email,
    (FLOOR(18 + random() * (65 - 18 + 1)))::int AS age,
    (
        CURRENT_DATE - 
        ((FLOOR(18 + random() * (65 - 18 + 1)))::int * INTERVAL '1 year') - 
        (random() * INTERVAL '365 days')
    )::date AS date_of_birth
FROM generate_series(1, 100),
     LATERAL (SELECT first_name FROM first_names ORDER BY random() LIMIT 1) f,
     LATERAL (SELECT last_name FROM last_names ORDER BY random() LIMIT 1) l;

-- Job Postings
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create and insert 30 job postings
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
