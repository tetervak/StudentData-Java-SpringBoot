INSERT INTO program
    (program_name)
VALUES
    ('Computer Programmer'),
    ('Systems Technology'),
    ('Engineering Technician');

INSERT INTO student
    (first_name, last_name, program_id, international, program_year, program_coop)
VALUES
    ('Harry', 'Potter',1,false,1,true),
    ('Ronald', 'Weasley',2,true,2,false),
    ('Hermione', 'Granger',2,false,1,false),
    ('Draco', 'Malfoy',3,true,2,true),
    ('George', 'Weasley',2,true,2,false),
    ('Fred', 'Weasley',2,false,1,false),
    ('Ginny', 'Weasley',3,true,2,true);

/* all these passwords are "sesame" */
INSERT INTO app_user
(user_name, password)
VALUES
    ('marge','$2a$10$bxGtVIu12/dXFQ8I1VrCmeFap8AXK.8EFgp.NRgaGt5no27uZd8Ty'),
    ('homer','$2a$10$5y39gonhJWNtUXFHi3gLaumMYLKmK/O4Jshi4/IlhryYNxhEFSNuy'),
    ('bart','$2a$10$WFceIBbBe2ynUC6ckJltOeI9qNgKSqGzE/PqD2BbxBHSVZyscOF8O'),
    ('lisa','$2a$10$/0le0donOsBt.kSva6CNNeNXRjm83m.VQeEsWHyY9ORQwJeGN/DAa');

INSERT INTO app_role
(role_name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

INSERT INTO app_user_role
(user_id, role_id)
VALUES
    (1,1),
    (2,1),
    (3,2),
    (4,2);

