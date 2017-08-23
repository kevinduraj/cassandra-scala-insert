--- Create a Namespace ---
CREATE KEYSPACE IF NOT EXISTS test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
DESCRIBE KEYSPACES;
USE test;

--- Create a Table ---
CREATE TABLE IF NOT EXISTS test.links (
  domain text,
  url text,
  PRIMARY KEY (domain, url)
);

DESCRIBE test;
