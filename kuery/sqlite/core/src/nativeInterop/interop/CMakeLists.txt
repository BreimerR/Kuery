cmake_minimum_required(VERSION 3.22)
project(KSQLite C)

set(CMAKE_C_STANDARD 99)

#[[add_library(
        KuerySQLite
        include/sqlite3.h
        include/shell.h
        include/sqlite3ext.h
)]]

add_executable(KuerySQLite   include/sqlite3.c)
