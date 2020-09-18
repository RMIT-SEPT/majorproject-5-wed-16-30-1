/* Copyright (c) 2001-2020, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb.dbinfo;

import java.lang.reflect.Constructor;

import org.hsqldb.Database;
import org.hsqldb.Session;
import org.hsqldb.Table;
import org.hsqldb.lib.IntValueHashMap;
import org.hsqldb.persist.PersistentStore;

// fredt@users - 1.7.2 - structural modifications to allow inheritance
// campbell-burnet@users 20020305 - completed inheritance work, including final access
// campbell-burnet@users 20020305 - javadoc updates/corrections
// campbell-burnet@users 20020305 - SYSTEM_VIEWS brought in line with SQL 200n
// campbell-burnet@users 20050514 - further SQL 200n metdata support

/**
 * Base class for system tables. Includes a factory method which returns the
 * most complete implementation available in the jar. This base implementation
 * knows the names of all system tables but returns null for any system table.
 * <p>
 * This class has been developed from scratch to replace the previous
 * DatabaseInformation implementations. <p>
 *
 * @author Campbell Burnet (campbell-burnet@users dot sourceforge.net)
 * @version 2.5.1
 * @since 1.7.2
 */
public class DatabaseInformation {

    // ids for system table names strictly in order of sysTableNames[]
    static final int SYSTEM_BESTROWIDENTIFIER = 0;
    static final int SYSTEM_COLUMNS           = 1;
    static final int SYSTEM_CROSSREFERENCE    = 2;
    static final int SYSTEM_INDEXINFO         = 3;
    static final int SYSTEM_PRIMARYKEYS       = 4;
    static final int SYSTEM_PROCEDURECOLUMNS  = 5;
    static final int SYSTEM_PROCEDURES        = 6;
    static final int SYSTEM_SCHEMAS           = 7;
    static final int SYSTEM_TABLES            = 8;
    static final int SYSTEM_TABLETYPES        = 9;
    static final int SYSTEM_TYPEINFO          = 10;
    static final int SYSTEM_UDTS              = 11;
    static final int SYSTEM_USERS             = 12;    //-- ref in SqlFile only
    static final int SYSTEM_VERSIONCOLUMNS    = 13;    //-- returns autogenerated columns
    static final int SYSTEM_SEQUENCES = 14;            //-- same as SEQUENCES

    // HSQLDB-specific
    static final int SYSTEM_CACHEINFO             = 15;
    static final int SYSTEM_COLUMN_SEQUENCE_USAGE = 16;
    static final int SYSTEM_COMMENTS              = 17;
    static final int SYSTEM_CONNECTION_PROPERTIES = 18;
    static final int SYSTEM_INDEXSTATS            = 19;
    static final int SYSTEM_KEY_INDEX_USAGE       = 20;
    static final int SYSTEM_PROPERTIES            = 21;
    static final int SYSTEM_SESSIONINFO           = 22;
    static final int SYSTEM_SESSIONS              = 23;
    static final int SYSTEM_TABLESTATS            = 24;
    static final int SYSTEM_TEXTTABLES            = 25;
    static final int SYSTEM_SYNONYMS              = 26;

    // SQL 200n tables
    static final int ADMINISTRABLE_ROLE_AUTHORIZATIONS = 27;
    static final int APPLICABLE_ROLES                  = 28;
    static final int ASSERTIONS                        = 29;
    static final int AUTHORIZATIONS                    = 30;
    static final int CHARACTER_SETS                    = 31;
    static final int CHECK_CONSTRAINT_ROUTINE_USAGE    = 32;
    static final int CHECK_CONSTRAINTS                 = 33;
    static final int COLLATIONS                        = 34;
    static final int COLUMN_COLUMN_USAGE               = 35;
    static final int COLUMN_DOMAIN_USAGE               = 36;
    static final int COLUMN_PRIVILEGES                 = 37;
    static final int COLUMN_UDT_USAGE                  = 38;
    static final int COLUMNS                           = 39;
    static final int CONSTRAINT_COLUMN_USAGE           = 40;
    static final int CONSTRAINT_PERIOD_USAGE           = 41;
    static final int CONSTRAINT_TABLE_USAGE            = 42;
    static final int DATA_TYPE_PRIVILEGES              = 43;
    static final int DOMAIN_CONSTRAINTS                = 44;
    static final int DOMAINS                           = 45;
    static final int ELEMENT_TYPES                     = 46;
    static final int ENABLED_ROLES                     = 47;
    static final int INFORMATION_SCHEMA_CATALOG_NAME   = 48;
    static final int JAR_JAR_USAGE                     = 49;
    static final int JARS                              = 50;
    static final int KEY_COLUMN_USAGE                  = 51;
    static final int KEY_PERIOD_USAGE                  = 52;
    static final int METHOD_SPECIFICATIONS             = 53;
    static final int MODULE_COLUMN_USAGE               = 54;
    static final int MODULE_PRIVILEGES                 = 55;
    static final int MODULE_TABLE_USAGE                = 56;
    static final int MODULES                           = 57;
    static final int PARAMETERS                        = 58;
    static final int PERIODS                           = 59;
    static final int REFERENTIAL_CONSTRAINTS           = 60;
    static final int ROLE_AUTHORIZATION_DESCRIPTORS    = 61;
    static final int ROLE_COLUMN_GRANTS                = 62;
    static final int ROLE_MODULE_GRANTS                = 63;
    static final int ROLE_ROUTINE_GRANTS               = 64;
    static final int ROLE_TABLE_GRANTS                 = 65;
    static final int ROLE_UDT_GRANTS                   = 66;
    static final int ROLE_USAGE_GRANTS                 = 67;
    static final int ROUTINE_COLUMN_USAGE              = 68;
    static final int ROUTINE_JAR_USAGE                 = 69;
    static final int ROUTINE_PERIOD_USAGE              = 70;
    static final int ROUTINE_PRIVILEGES                = 71;
    static final int ROUTINE_ROUTINE_USAGE             = 72;
    static final int ROUTINE_SEQUENCE_USAGE            = 73;
    static final int ROUTINE_TABLE_USAGE               = 74;
    static final int ROUTINES                          = 75;
    static final int SCHEMATA                          = 76;
    static final int SEQUENCES                         = 77;
    static final int SQL_FEATURES                      = 78;
    static final int SQL_IMPLEMENTATION_INFO           = 79;
    static final int SQL_PACKAGES                      = 80;
    static final int SQL_PARTS                         = 81;
    static final int SQL_SIZING                        = 82;
    static final int SQL_SIZING_PROFILES               = 83;
    static final int TABLE_CONSTRAINTS                 = 84;
    static final int TABLE_PRIVILEGES                  = 85;
    static final int TABLES                            = 86;
    static final int TRANSLATIONS                      = 87;
    static final int TRIGGER_COLUMN_USAGE              = 88;
    static final int TRIGGER_PERIOD_USAGE              = 89;
    static final int TRIGGER_ROUTINE_USAGE             = 90;
    static final int TRIGGER_SEQUENCE_USAGE            = 91;
    static final int TRIGGER_TABLE_USAGE               = 92;
    static final int TRIGGERED_UPDATE_COLUMNS          = 93;
    static final int TRIGGERS                          = 94;
    static final int TYPE_JAR_USAGE                    = 95;
    static final int UDT_PRIVILEGES                    = 96;
    static final int USAGE_PRIVILEGES                  = 97;
    static final int USER_DEFINED_TYPES                = 98;
    static final int VIEW_COLUMN_USAGE                 = 99;
    static final int VIEW_PERIOD_USAGE                 = 100;
    static final int VIEW_ROUTINE_USAGE                = 101;
    static final int VIEW_TABLE_USAGE                  = 102;
    static final int VIEWS                             = 103;

    /** system table names strictly in order of their ids */
    static final String[] sysTableNames = {
        "SYSTEM_BESTROWIDENTIFIER",                              //
        "SYSTEM_COLUMNS",                                        //
        "SYSTEM_CROSSREFERENCE",                                 //
        "SYSTEM_INDEXINFO",                                      //
        "SYSTEM_PRIMARYKEYS",                                    //
        "SYSTEM_PROCEDURECOLUMNS",                               //
        "SYSTEM_PROCEDURES",                                     //
        "SYSTEM_SCHEMAS",                                        //
        "SYSTEM_TABLES",                                         //
        "SYSTEM_TABLETYPES",                                     //
        "SYSTEM_TYPEINFO",                                       //
        "SYSTEM_UDTS",                                           //
        "SYSTEM_USERS",                                          //
        "SYSTEM_VERSIONCOLUMNS",                                 //
        "SYSTEM_SEQUENCES",                                      //

        // HSQLDB-specific
        "SYSTEM_CACHEINFO",                                      //
        "SYSTEM_COLUMN_SEQUENCE_USAGE",                          //
        "SYSTEM_COMMENTS",                                       //
        "SYSTEM_CONNECTION_PROPERTIES",                          //
        "SYSTEM_INDEXSTATS",                                     //
        "SYSTEM_KEY_INDEX_USAGE",                                //
        "SYSTEM_PROPERTIES",                                     //
        "SYSTEM_SESSIONINFO",                                    //
        "SYSTEM_SESSIONS",                                       //
        "SYSTEM_TABLESTATS",                                     //
        "SYSTEM_TEXTTABLES",                                     //
        "SYSTEM_SYNONYMS",                                       //

        // SQL 200n
        "ADMINISTRABLE_ROLE_AUTHORIZATIONS",                     //
        "APPLICABLE_ROLES",                                      //
        "ASSERTIONS",                                            //
        "AUTHORIZATIONS",                                        //
        "CHARACTER_SETS",                                        //
        "CHECK_CONSTRAINT_ROUTINE_USAGE",                        //
        "CHECK_CONSTRAINTS",                                     //
        "COLLATIONS",                                            //
        "COLUMN_COLUMN_USAGE",                                   //
        "COLUMN_DOMAIN_USAGE",                                   //
        "COLUMN_PRIVILEGES",                                     //
        "COLUMN_UDT_USAGE",                                      //
        "COLUMNS",                                               //
        "CONSTRAINT_COLUMN_USAGE",                               //
        "CONSTRAINT_PERIOD_USAGE",                               //
        "CONSTRAINT_TABLE_USAGE",                                //
        "DATA_TYPE_PRIVILEGES",                                  //
        "DOMAIN_CONSTRAINTS",                                    //
        "DOMAINS",                                               //
        "ELEMENT_TYPES",                                         //
        "ENABLED_ROLES",                                         //
        "INFORMATION_SCHEMA_CATALOG_NAME",                       //
        "JAR_JAR_USAGE",                                         //
        "JARS",                                                  //
        "KEY_COLUMN_USAGE",                                      //
        "KEY_PERIOD_USAGE",                                      //
        "METHOD_SPECIFICATIONS",                                 //
        "MODULE_COLUMN_USAGE",                                   //
        "MODULE_PRIVILEGES",                                     //
        "MODULE_TABLE_USAGE",                                    //
        "MODULES",                                               //
        "PARAMETERS",                                            //
        "PERIODS",                                               //
        "REFERENTIAL_CONSTRAINTS",                               //
        "ROLE_AUTHORIZATION_DESCRIPTORS",                        //
        "ROLE_COLUMN_GRANTS",                                    //
        "ROLE_MODULE_GRANTS",                                    //
        "ROLE_ROUTINE_GRANTS",                                   //
        "ROLE_TABLE_GRANTS",                                     //
        "ROLE_UDT_GRANTS",                                       //
        "ROLE_USAGE_GRANTS",                                     //
        "ROUTINE_COLUMN_USAGE",                                  //
        "ROUTINE_JAR_USAGE",                                     //
        "ROUTINE_PERIOD_USAGE",                                  //
        "ROUTINE_PRIVILEGES",                                    //
        "ROUTINE_ROUTINE_USAGE",                                 //
        "ROUTINE_SEQUENCE_USAGE",                                //
        "ROUTINE_TABLE_USAGE",                                   //
        "ROUTINES",                                              //
        "SCHEMATA",                                              //
        "SEQUENCES",                                             //
        "SQL_FEATURES",                                          //
        "SQL_IMPLEMENTATION_INFO",                               //
        "SQL_PACKAGES",                                          //
        "SQL_PARTS",                                             //
        "SQL_SIZING",                                            //
        "SQL_SIZING_PROFILES",                                   //
        "TABLE_CONSTRAINTS",                                     //
        "TABLE_PRIVILEGES",                                      //
        "TABLES",                                                //
        "TRANSLATIONS",                                          //
        "TRIGGER_COLUMN_USAGE",                                  //
        "TRIGGER_PERIOD_USAGE",                                  //
        "TRIGGER_ROUTINE_USAGE",                                 //
        "TRIGGER_SEQUENCE_USAGE",                                //
        "TRIGGER_TABLE_USAGE",                                   //
        "TRIGGERED_UPDATE_COLUMNS",                              //
        "TRIGGERS",                                              //
        "TYPE_JAR_USAGE",                                        //
        "UDT_PRIVILEGES",                                        //
        "USAGE_PRIVILEGES",                                      //
        "USER_DEFINED_TYPES",                                    //
        "VIEW_COLUMN_USAGE",                                     //
        "VIEW_PERIOD_USAGE",                                     //
        "VIEW_ROUTINE_USAGE",                                    //
        "VIEW_TABLE_USAGE",                                      //
        "VIEWS",                                                 //
    };

    /** Map: table name => table id */
    static final IntValueHashMap sysTableNamesMap;

    static {
        synchronized (DatabaseInformation.class) {
            sysTableNamesMap = new IntValueHashMap(107);

            for (int i = 0; i < sysTableNames.length; i++) {
                sysTableNamesMap.put(sysTableNames[i], i);
            }
        }
    }

    static int getSysTableID(String token) {
        return sysTableNamesMap.get(token, -1);
    }

    /** Database for which to produce tables */
    final Database database;

    /**
     * state flag -- if true, tables are to be produced with content, else
     * empty (surrogate) tables are to be produced.  This allows faster
     * database startup where user views reference system tables and faster
     * system table structural reflection for table metadata.
     */
    boolean withContent = false;

    /**
     * Factory method returns the fullest system table producer
     * implementation available.  This instantiates implementations beginning
     * with the most complete, finally choosing an empty table producer
     * implementation (this class) if no better instance can be constructed.
     * @param db The Database object for which to produce system tables
     * @return the fullest system table producer
     *      implementation available
     */
    public static DatabaseInformation newDatabaseInformation(Database db) {

        Class<?> c = DatabaseInformation.class;

        try {
            c = Class.forName("org.hsqldb.dbinfo.DatabaseInformationFull");
        } catch (Exception e) {
            try {
                c = Class.forName("org.hsqldb.dbinfo.DatabaseInformationMain");
            } catch (Exception e2) {}
        }

        try {
            Class<?>[]     ctorParmTypes = new Class[]{ Database.class };
            Object[]       ctorParms     = new Object[]{ db };
            Constructor<?> ctor = c.getDeclaredConstructor(ctorParmTypes);

            return (DatabaseInformation) ctor.newInstance(ctorParms);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DatabaseInformation(db);
    }

    /**
     * Constructs a new DatabaseInformation instance which knows the names of
     * all system tables (isSystemTable()) but simply returns null for all
     * getSystemTable() requests. <p>
     *
     * @param db The Database object for which to produce system tables
     */
    DatabaseInformation(Database db) {
        database = db;
    }

    /**
     * Tests if the specified name is that of a system table. <p>
     *
     * @param name the name to test
     * @return true if the specified name is that of a system table
     */
    final boolean isSystemTable(String name) {
        return sysTableNamesMap.containsKey(name);
    }

    /**
     * Retrieves a table with the specified name whose content may depend on
     * the execution context indicated by the session argument as well as the
     * current value of <code>withContent</code>. <p>
     *
     * @param session the context in which to produce the table
     * @param name the name of the table to produce
     * @return a table corresponding to the name and session arguments, or
     *      <code>null</code> if there is no such table to be produced
     */
    public Table getSystemTable(Session session, String name) {
        return null;
    }

    /**
     * Sets the store for the given session, populates the store if necessary.
     */
    public void setStore(Session session, Table table,
                         PersistentStore store) {}

    /**
     * Switches this table producer between producing empty (surrogate)
     * or tables with (row) content. <p>
     *
     * @param withContent if true, then produce tables with (row) content, else
     *        produce empty (surrogate) tables
     */
    public final void setWithContent(boolean withContent) {
        this.withContent = withContent;
    }
}
