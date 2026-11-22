INSERT INTO location (name, city, address, opening_time, closing_time) VALUES ('Sede Norte', 'Bucaramanga', 'Carrera 27 # 45-10', '07:00:00', '21:00:00');
INSERT INTO location (name, city, address, opening_time, closing_time) VALUES ('Sede Cabecera', 'Bucaramanga', 'Calle 48 # 33-15', '08:00:00', '20:00:00');

INSERT INTO equipment(name) VALUES ('Proyector 4K');
INSERT INTO equipment(name) VALUES ('Pizarra Inteligente');
INSERT INTO equipment(name) VALUES ('Sistema de Videoconferencia');
INSERT INTO equipment(name) VALUES ('Monitor Ultrawide 34');

INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Sala Ejecutiva A', 'MEETING_ROOM', 12, 45.00, true, 1);
INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Oficina Privada 101', 'PRIVATE_OFFICE', 4, 30.00, true, 1);
INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Escritorio Compartido N1', 'DESK', 1, 10.00, true, 1);
INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Sala Creativa B', 'MEETING_ROOM', 8, 35.00, true, 2);
INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Oficina Privada 202', 'PRIVATE_OFFICE', 3, 25.00, true, 2);
INSERT INTO room (name, type, capacity, hourly_rate, active, location_id) VALUES ('Sala Mantenimiento C', 'MEETING_ROOM', 10, 40.00, false, 2);

INSERT INTO additional_service(name, unit_price) VALUES ('Estacion de Cafe Ilimitado', 5.00);
INSERT INTO additional_service(name, unit_price) VALUES ('Almuerzo Ejecutivo', 15.00);
INSERT INTO additional_service(name, unit_price) VALUES ('Soporte Tecnico Dedicado', 25.00);

INSERT INTO member_ship_plan(name, included_hours_per_month, overage_discount, max_active_bookings) VALUES ('Plan Basico', 10, 0.05, 2);
INSERT INTO member_ship_plan(name, included_hours_per_month, overage_discount, max_active_bookings) VALUES ('Plan Profesional', 30, 0.15, 5);
INSERT INTO member_ship_plan(name, included_hours_per_month, overage_discount, max_active_bookings) VALUES ('Plan Corporativo', 80, 0.25, 10);

INSERT INTO member(document_number, full_name, email, status, membershipplan_id) VALUES ('1098765432', 'Carlos Mendoza', 'carlos.mendoza@email.com', 'ACTIVE', 2);
INSERT INTO member(document_number, full_name, email, status, membershipplan_id) VALUES ('1098765433', 'Laura Gomez', 'laura.gomez@email.com', 'ACTIVE', 3);
INSERT INTO member(document_number, full_name, email, status, membershipplan_id) VALUES ('1098765434', 'Andres Silva', 'andres.silva@email.com', 'ACTIVE', 1);
INSERT INTO member(document_number, full_name, email, status, membershipplan_id) VALUES ('1098765435', 'Valentina Rios', 'valentina.rios@email.com', 'ACTIVE', 2);
INSERT INTO member(document_number, full_name, email, status, membershipplan_id) VALUES ('1098765436', 'Mateo Vargas', 'mateo.vargas@email.com', 'SUSPENDED', 1);

INSERT INTO usser_acount(username, password, role, member_id) VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NULL);
INSERT INTO usser_acount(username, password, role, member_id) VALUES ('recepcion', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEPTIONIST', NULL);
INSERT INTO usser_acount(username, password, role, member_id) VALUES ('cmendoza', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'MEMBER', 1);
INSERT INTO usser_acount(username, password, role, member_id) VALUES ('lgomez', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'MEMBER', 2);

INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (8, 0.00, 90.00, 40.00, 130.00, '2026-11-10 11:00:00', 1, 1, '2026-11-10 09:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (3, 0.00, 60.00, 0.00, 60.00, '2026-11-11 16:00:00', 2, 2, '2026-11-11 14:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (1, 0.00, 30.00, 5.00, 35.00, '2026-11-12 11:00:00', 3, 3, '2026-11-12 08:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (6, 0.00, 70.00, 90.00, 160.00, '2026-11-13 12:00:00', 4, 4, '2026-11-13 10:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (2, 12.50, 0.00, 0.00, 12.50, '2026-11-14 17:00:00', 5, 5, '2026-11-14 15:00:00', 'CANCELLED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (10, 22.50, 0.00, 0.00, 22.50, '2026-11-15 11:00:00', 1, 1, '2026-11-15 09:00:00', 'CANCELLED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (4, 0.00, 60.00, 25.00, 85.00, '2026-11-16 12:00:00', 2, 2, '2026-11-16 10:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (1, 0.00, 20.00, 0.00, 20.00, '2026-11-17 15:00:00', 4, 3, '2026-11-17 13:00:00', 'COMPLETED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (7, 0.00, 105.00, 35.00, 140.00, '2026-11-23 12:00:00', 2, 4, '2026-11-23 09:00:00', 'CONFIRMED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (2, 0.00, 50.00, 0.00, 50.00, '2026-11-24 16:00:00', 1, 5, '2026-11-24 14:00:00', 'CONFIRMED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (12, 0.00, 135.00, 60.00, 195.00, '2026-11-25 11:00:00', 2, 1, '2026-11-25 08:00:00', 'CONFIRMED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (3, 0.00, 60.00, 15.00, 75.00, '2026-11-26 17:00:00', 4, 2, '2026-11-26 15:00:00', 'CONFIRMED');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (1, 0.00, 40.00, 0.00, 40.00, '2026-11-27 13:00:00', 3, 3, '2026-11-27 09:00:00', 'PENDING');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (5, 0.00, 70.00, 25.00, 95.00, '2026-11-28 12:00:00', 1, 4, '2026-11-28 10:00:00', 'PENDING');
INSERT INTO boocking(attendees, cancellation_fee, room_cost, services_cost, total_cost, end_time, member_id, room_id, start_time, status) VALUES (2, 0.00, 75.00, 0.00, 75.00, '2026-11-29 12:00:00', 4, 5, '2026-11-29 09:00:00', 'PENDING');

INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (5.00, 8, 1, 1);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (5.00, 1, 1, 3);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (15.00, 6, 2, 4);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (25.00, 1, 3, 7);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (5.00, 7, 1, 9);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (5.00, 12, 1, 11);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (5.00, 3, 1, 12);
INSERT INTO booking_item(applied_unit_price, quantity, additional_service_id, booking_id) VALUES (25.00, 1, 3, 14);
