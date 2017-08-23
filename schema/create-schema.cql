--- Create a Namespace ---
CREATE KEYSPACE test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
DESCRIBE KEYSPACES;
USE test;

--- Create a Table ---
CREATE TABLE test.student (
  id int PRIMARY KEY,
  first_name text,
  last_name text
);

DESCRIBE test;
