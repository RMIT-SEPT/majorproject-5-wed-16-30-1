#!/bin/sh

java -classpath hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:database/main --dbname.0 main
