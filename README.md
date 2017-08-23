Scala to Cassandra Connection
=============================

## Create Keyspace and Table
```
--- create a namespace ---
CREATE KEYSPACE test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
DESCRIBE KEYSPACES;
USE test;

--- create a table ---
CREATE TABLE test.student (
  id int PRIMARY KEY,
  first_name text,
  last_name text
);
```

## Packaging
```
$ sbt package
$ sbt "run 69.13.39.46 "/home/spider/raw/links.csv""

```
