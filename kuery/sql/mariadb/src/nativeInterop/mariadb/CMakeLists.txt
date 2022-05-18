cmake_minimum_required(VERSION 3.22)
project(mariadb C)

set(CMAKE_C_STANDARD 99)

add_library(mariadb src/library.c)
