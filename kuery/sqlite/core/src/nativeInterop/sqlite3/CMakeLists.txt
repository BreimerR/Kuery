cmake_minimum_required(VERSION 3.22)

project(sqlite3_interop C)

set(CMAKE_C_STANDARD 99)

add_library(sqlite3_interop src/library.c)