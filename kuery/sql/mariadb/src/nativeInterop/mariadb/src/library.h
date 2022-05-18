#include "mariadb/mysql.h"
#include <stdio.h>

/**TODO
 * Since database actions
 * are more of an IO thing then being
 * able to just use read it like an IO would
 * be a preferred inorder to avoid having too
 * many pointers or memScoped functions
 * and execution can all be inside a while loop
 **/
static MYSQL *Mysql() {
    MYSQL mysql;
    return &mysql;
}

typedef void (*OnFail)(unsigned int code, const char *message);

static unsigned int read(MYSQL *connection, const char *sql, OnFail onFail) {

    int response = mysql_query(connection, sql);


    if (response > 0) {
        // return 1 for each row read
        mysql_read_query_result(connection);
        return 0;
    } else {
        unsigned int errorCode = mysql_errno(connection);
        const char *errorMsg = mysql_error(connection);
        onFail(errorCode, errorMsg);
    }

}

static int mysql_library_init_macro(int arg, char **arguments, char **groups) {
    return mysql_library_init(arg, arguments, groups);
}

static void mysqlLibraryEnd() {

    mysql_library_end();
}

static unsigned int hasResult(MYSQL_ROW row) {
    return row != NULL;
}