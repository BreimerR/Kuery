package libetal.libraries.kuery.mariadb.interop

import kotlinx.cinterop.*
import kuery.interop.mariadb.*
import platform.posix.size_tVar

object Mariadb {
    /**
     * TODO
     * [Update To New version](https://github.com/mariadb-corporation/mariadb-connector-c/wiki/mariadb_connect)
     **/
    fun connect(existingConnection: CPointer<MYSQL>?, connection: String): CPointer<MYSQL> =
        TODO("mariadb_connect(this, connection)")
}

object Mysql {

    fun realConnect(
        host: String?,
        user: String?,
        password: String?,
        database: String?,
        port: UInt = 3306u,
        unixSocket: String? = null,
        clientFlag: ULong = 0u
    ): CPointer<MYSQL> {
        val mysql = init() ?: throw RuntimeException("Failed to initialize MYSQL")

        val connection = mysql_real_connect(
            mysql,
            host,
            user,
            password,
            database,
            port,
            unixSocket,
            clientFlag
        )

        val errorCode = mysql.errorCode

        if (connection != null && errorCode == 0u) return connection

        val errorMessage = mysql.error?.toKString() ?: "Failed to Parse error Message"

        throw RuntimeException(errorMessage)

    }

    fun libraryEnd() = mysqlLibraryEnd()

    fun init(mysql: CValuesRef<MYSQL>? = null) =
        mysql_init(mysql)

    fun threadSafe() = mysql_thread_safe()

    fun debug(message: String) = mysql_debug(message)

    fun threadInit() = mysql_thread_init()

    fun libraryInit(arg: Int, arguments: CPointer<CPointerVar<ByteVar>>?, groups: CPointer<CPointerVar<ByteVar>>?) =
        mysql_library_init_macro(arg, arguments, groups)

}

val CPointer<MYSQL>.moreResults
    get() = mysql_more_results(this)

val CPointer<MYSQL>.nextResult
    get() = mysql_next_result(this)

val CValuesRef<MYSQL_RES>.numFields
    get() = mysql_num_fields(this)

/**
 * Can't use dataSeek | numRows
 * When using mysql_use_result()
 **/
val CValuesRef<MYSQL_RES>.numRows
    get() = mysql_num_rows(this)

val CPointer<MYSQL>.protoInfo
    get() = mysql_get_proto_info(this)

val CPointer<MYSQL>.serverInfo
    get() = mysql_get_server_info(this)

val CPointer<MYSQL>.serverVersion
    get() = mysql_get_server_version(this)

val CPointer<MYSQL>.sslCipher
    get() = mysql_get_ssl_cipher(this)

val CPointer<MYSQL>.dumpDebugInfo
    get() = mysql_dump_debug_info(this)

/**
 * Returns the last error code for the most recent function call
 * @return UInt: 0 | (x > 0) for errors
 **/
val CPointer<MYSQL>.errno
    get() = mysql_errno(this)

val CPointer<MYSQL>.errorCode
    get() = errno

val CPointer<MYSQL>.error
    get() = mysql_error(this)

val CPointer<MYSQL>.errorMessage
    get() = error?.toKString() ?: "Undefined error message"

val CPointer<MYSQL>.clientInfo
    get() = mysql_get_client_info()?.toKString()

val CPointer<MYSQL>.clientVersion
    get() = mysql_get_client_version()

val CPointer<MYSQL>.hostInfo
    get() = mysql_get_host_info(this)?.toKString()

val CValuesRef<MYSQL_RES>.field
    get() = mysql_fetch_field(this)

val CValuesRef<MYSQL_RES>.fields
    get() = mysql_fetch_field(this)

val CValuesRef<MYSQL_RES>.lengths
    get() = mysql_fetch_lengths(this)

val CValuesRef<MYSQL_RES>.row
    get() = mysql_fetch_row(this)

val MYSQL_ROW?.hasResult
    get() = hasResult(this) > 0u

val CValuesRef<MYSQL>.fieldCount
    get() = mysql_field_count(this)

val CValuesRef<MYSQL_RES>.fieldTell
    get() = mysql_field_tell(this)

val CPointer<MYSQL>.info
    get() = mysql_info(this)

val CPointer<MYSQL>.insertId
    get() = mysql_insert_id(this)

val CValuesRef<MYSQL>.warningCount
    get() = mysql_warning_count(this)

infix fun CPointer<MYSQL>.kill(pid: ULong) =
    mysql_kill(this, pid)

fun CValuesRef<ByteVar>.escapeString(from: String?, length: ULong) =
    mysql_escape_string(this, from, length)

fun CPointer<MYSQL>.options(option: mysql_option, argument: CValuesRef<*>?) =
    mysql_options(this, option, argument)

fun CPointer<MYSQL>.optionsv(
    option: mysql_option,
    variadicArguments: Array<Any?>
): Int { //TODO Currently can't pass vardic arguments
    return mysql_optionsv(this, option, *arrayOf())
}


fun CPointer<MYSQL>.ping() =
    mysql_ping(this)

// https://github.com/mariadb-corporation/mariadb-connector-c/wiki/example_non_blocking
fun CPointer<MYSQL>.query(sql: String) = mysql_query(this, sql)

infix fun CValuesRef<MYSQL_RES>.fetchFieldDirect(fieldNr: UInt) = mysql_fetch_field_direct(this, fieldNr)

infix fun CValuesRef<MYSQL_RES>.fieldSeek(offset: MYSQL_FIELD_OFFSET) = mysql_field_seek(this, offset)

fun CPointer<MYSQL>.getInfov(
    value: mariadb_value,
    arg: CValuesRef<*>?,
    vararg vardigArguent: Any?
) { // TODO: Can't pass vardigArguent
    mariadb_get_infov(this, value, arg, *arrayOf())
}

fun CValuesRef<MYSQL_RES>.freeResult() = mysql_free_result(this)

infix fun CValuesRef<MYSQL>.getCharacterSetInfo(charSetInfo: CValuesRef<MY_CHARSET_INFO>?) =
    mysql_get_character_set_info(this, charSetInfo)

fun CPointer<MYSQL>.optionv(options: mysql_option, arg: CValuesRef<*>?, vararg variadicArguments: Any?) =
    // TODO can't pass variadicArguments
    mysql_get_optionv(this, options, arg, *arrayOf())

fun CPointer<ByteVar>.hexString(from: String?, length: ULong) =
    mysql_hex_string(this, from, length)

fun CValuesRef<MYSQL>.realEscapeString(to: CValues<ByteVar>, from: String?, length: ULong) =
    mysql_real_escape_string(this, to, from, length)

fun CValuesRef<MYSQL>.realQuery(query: String, length: ULong) =
    mysql_real_query(this, query, length)

infix fun CValuesRef<MYSQL>.refresh(options: UInt) =
    mysql_refresh(this, options)

/**
 * Immediately aborts a MYSQL read/write operations...
 * > Doesn't invalidate or free memory use close().
 * > Use this mainly to MURDER LONG RUNNING OPERATIONS
 *
 * @param context: CPointer<MYSQL>
 * @return int:  0 on success | (x > 0) on ERROR
 **/
fun CPointer<MYSQL>.cancel() = mariadb_cancel(this)

fun CPointer<MYSQL>.affectedRows() = mysql_affected_rows(this)

infix fun CPointer<MYSQL>.autocommit(mode: Boolean) = mysql_autocommit(this, mode.toByte())

fun CPointer<MYSQL>.changeUser(user: String, password: String, database: String) =
    mysql_change_user(this, user, password, database)

fun CPointer<MYSQL>.characterSetName() = mysql_character_set_name(this)

fun CPointer<MYSQL>?.close() =
    mysql_close(this)

fun CPointer<MYSQL>.commit() =
    mysql_commit(this)

infix fun CValuesRef<MYSQL_RES>.dataSeek(offset: ULong) =
    mysql_data_seek(this, offset)

fun CValuesRef<MYSQL>.resetConnection() =
    mysql_reset_connection(this)

fun CValuesRef<MYSQL>.rollback() =
    mysql_rollback(this)

fun CValuesRef<MYSQL_RES>.rowSeek(offset: MYSQL_ROW_OFFSET) =
    mysql_row_seek(this, offset)

fun CValuesRef<MYSQL_RES>.rowTell() =
    mysql_row_tell(this)

infix fun CValuesRef<MYSQL>.selectDb(dbName: String) =
    mysql_select_db(this, dbName)

fun CValuesRef<MYSQL>.sendQuery(query: String, length: ULong) =
    mysql_send_query(this, query, length)

fun serverEnd() = mysql_server_end()

fun serverInit(agrc: Int, arguments: CValuesRef<CPointerVar<ByteVar>>?, groups: CValuesRef<CPointerVar<ByteVar>>?) =
    mysql_server_init(agrc, arguments, groups)

fun CValuesRef<MYSQL>.sessionTrackGetFirst(
    type: enum_session_state_type,
    data: CValuesRef<CPointerVar<ByteVar>>?,
    length: CValuesRef<size_tVar>?
) = mysql_session_track_get_first(this, type, data, length)

fun CPointer<MYSQL>.sessionTrackGetNext(
    type: enum_session_state_type,
    data: CValuesRef<CPointerVar<ByteVar>>?,
    length: CValuesRef<size_tVar>?
) = mysql_session_track_get_next(this, type, data, length)

infix fun CValuesRef<MYSQL>.setCharacterSet(characterSet: String) =
    mysql_set_character_set(this, characterSet)

infix fun CValuesRef<MYSQL>.setServerOption(option: enum_mysql_set_option) =
    mysql_set_server_option(this, option)

infix fun CValuesRef<MYSQL>.shutdown(level: mysql_enum_shutdown_level) =
    mysql_shutdown(this, level)


fun CValuesRef<MYSQL>.sqlState() =
    mysql_sqlstate(this)

/**
 * Returns SQLSTATE error code
 *
 * @return String
 **/
val CValuesRef<MYSQL>.sqlStateError
    get() = sqlState()?.toKString()

fun CValuesRef<MYSQL>.sslSet() =
    mysql_stat(this)

fun CValuesRef<MYSQL>.stat() =
    mysql_stat(this)

fun CValuesRef<MYSQL>.storeResult() =
    mysql_store_result(this)

fun threadEnd() =
    mysql_thread_end()

val CValuesRef<MYSQL>.threadId
    get() = mysql_thread_id(this)

fun CValuesRef<MYSQL>.useResult() =
    mysql_use_result(this)

fun CValuesRef<MYSQL>.mariadbReconnect() =
    mariadb_reconnect(this)

fun CValuesRef<MYSQL>.readQueryResult() =
    mysql_read_query_result(this)
