rootProject.name = "Kuery"

include(":kuery:logging")
project(":kuery:logging").projectDir = File(rootProject.projectDir, "../../Libraries/master/kotlin/log")

include(":kuery:library")
project(":kuery:library").projectDir = File(rootProject.projectDir, "../../Libraries/master/kotlin/library")

include(":kuery:coroutines")
project(":kuery:coroutines").projectDir = File(rootProject.projectDir, "../../Libraries/master/kotlin/coroutines")

include(
    ":annotations:common",
    ":annotations:sqlite",
    ":annotations:mysql",
    ":annotations:mariadb",
    ":annotations:postgresql"
)


include(
    ":kuery:core"
)

include(
    ":kuery:sqlite:core",
    ":kuery:sqlite:android",
)

include(
    ":kuery:sql:core",
    ":kuery:sql:mariadb",
    ":kuery:sql:postgresql"
)

/*
include(
    ":plugins:common"
)

include(
    ":plugins:sqlite:common",
    ":plugins:sqlite:native",
    ":plugins:sqlite:android",
    ":plugins:sqlite:desktopJvm"
)

include(
    ":plugins:mysql:common",
    ":plugins:mysql:mariadb",
    ":plugins:mysql:postgresql",
    ":plugins:mysql:mariadb-jvm",
    ":plugins:mysql:mariadb-native",
    ":plugins:mysql:mariadb-nodejs",
    ":plugins:mysql:postgresql-jvm",
    ":plugins:mysql:postgresql-native",
    ":plugins:mysql:postgresql-nodejs"
)

include(
    ":examples:mariadb:common",
    ":examples:mariadb:jvm",
    ":examples:mariadb:mac",
    ":examples:mariadb:native:",
    ":examples:mariadb:windows",
)



include(
    ":examples:postgresql:common",
    ":examples:postgresql:jvm",
    ":examples:postgresql:mac",
    ":examples:postgresql:native:linux",
    ":examples:postgresql:native:mac",
    ":examples:postgresql:native:windows"
)



*/
