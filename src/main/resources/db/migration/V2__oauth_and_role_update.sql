--  Allow OAuth users without password
ALTER TABLE users
ALTER COLUMN password DROP NOT NULL;

--  Update role constraint to match Java enum
ALTER TABLE users
DROP CONSTRAINT users_role_check;

ALTER TABLE users
ADD CONSTRAINT users_role_check
CHECK (role IN ('ROLE_PLAYER', 'ROLE_GAMEMASTER'));

-- Convert existing role values (if any old data)
UPDATE users SET role = 'ROLE_PLAYER' WHERE role = 'PLAYER';
UPDATE users SET role = 'ROLE_GAMEMASTER' WHERE role = 'GAMEMASTER';
