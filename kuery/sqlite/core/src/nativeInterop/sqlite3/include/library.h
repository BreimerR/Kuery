#include "sqlite3.h"
#include "sqlite3ext.h"
#include <stdlib.h>
#include <stdio.h>

struct Connection {
    const char *fileName;
    sqlite3 *db;
};

typedef void (*OnError)(int error_code, const char *description);

/**
 * TODO: Need to use UTF-8 Always for windows users
 **/
static inline struct Connection connect(const char *fileName, OnError errorHandler) {
    struct Connection connection;
    connection.fileName = fileName;

    int connected = sqlite3_open(connection.fileName, &connection.db);

    if (!connected) {
        if (errorHandler) {
            int errCode = sqlite3_errcode(connection.db);
            const char *errMsg = sqlite3_errmsg(connection.db);

            errorHandler(errCode, errMsg);
        }
    }

    return connection;

}


static inline struct Connection create(const char *databaseName, OnError errorHandler) {

}

static sqlite3 *Sqlite3(const char *filePath) {

    sqlite3 *database;

    sqlite3_open(filePath, &database);

    return database;

}