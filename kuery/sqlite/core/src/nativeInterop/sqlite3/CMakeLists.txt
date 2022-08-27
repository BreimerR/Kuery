cmake_minimum_required(VERSION 3.22)
project(sqlite3 C)

set(CMAKE_C_STANDARD 99)

add_library(
        KSQLite3
        include/sqlite3.h
        include/shell.h
        include/sqlite3ext.h
)

#[[
find_package(SQLite3 REQUIRED)
target_link_libraries(${PROJECT_NAME} SQLite::SQLite3)]]
