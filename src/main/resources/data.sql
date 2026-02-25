INSERT INTO vendor_category_rules (vendor_name, category)
VALUES
('Swiggy', 'FOOD'),
('Zomato', 'FOOD'),
('Uber', 'TRANSPORT'),
('Ola', 'TRANSPORT'),
('Amazon', 'SHOPPING'),
('BigBasket', 'GROCERIES'),
('Netflix', 'ENTERTAINMENT')
ON CONFLICT (vendor_name) DO NOTHING;
