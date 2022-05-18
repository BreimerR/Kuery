# Kuery

Kuery is a cross-platform orm system aiming to support multiple database systems
with as little code invasion as possible.

## Code Invasion

This defines how much the library affects the final outlook of your code
and how it can interact with other libraries.

1. ### Code Invasion

```kotlin
class User : Entity<*>()
```

This definition affects future code base use i.e. If we'd like to now make everything serializable,
we'd have to write intermediate implementations for Entity with kotlin-serialization.
Since Entity class isn't serialized and no need for it currently since it's just used
for interaction with the database period. And moving it across the wire isn't really preferred.

Also, this definition will affect class look / design i.e.

```kotlin
import libetal.kuery.DatabaseInstance.string

class User : Entity<*>() {
    val name: String by string("name")
}
```

In future if we need this as a data class well we can't since
the dataclass below isn't sound code plus would really look boring

```kotlin
data class User(val name: String by string("name"))
```

2. ### Non Invaded

While this does add code to your code base it doesn't really change much

```kotlin
@Entity
class User
```

Future Serialization

```kotlin
import kotlinx.serialization.Serializable;
import libetal.libraries.kuery.sql.annotations.Entity;

@Entity
@Serializable
data class User(val name: String, val age: Int)
```

Removing the above code from your source will only require you to delete the "database class
implementation"

## \<Code/> Of Conduct

### Implementation

1. Minimal code invasion
   > We can easily remove most Kuery code and the code should work `roughly` the same
   > > 1. This will provide for a proper plug and play into already existing projects with minimum effort
   > > 2. Ease of transition from DBMS to another
2. Plug and Play
   > 1. Ease of integration
3. CODE SQL
   > 1. All SQL should be expressed as properly syntax analyzable kotlin code.
   > > INFIX FUNCTIONS BEING A MAJOR SOLUTION HERE

### Contributions

1. IF it's a DBMS And It's clean contribution it will be merged.

# DBMS

## SQLITE

## SQL

1. [x] Mariadb
    1. [X] CREATE
    2. [x] Update
    3. [x] Delete
    4. [x] Read
2. [ ] Postgresql
    1. [ ] CREATE
    2. [ ] Update
    3. [ ] Delete
    4. [ ] Read
3. [ ] GraphQl
   > Implementation of this will affect the kuery: core
   > > ### Solutions
   > > 1. Update code base structure into
   > >   ```
   > >     kuery:core
   > >     kuery:graph
   > >     kuery:graph:ql // This are core modules
   > >     kuery:graph:ql-js
   > >     kuery:graph:ql-jvm
   > >     kuery:graph:ql-native // not sure of yet
   > >     kuery:relational  // core module this is what's currently kuery:core
   > >     kuery:relational:sqlite:jvm
   > >     kuery:relational:sqlite:native
   > >     kuery:relational:sqlite:android
   > >     kuery:relational:sql:mariadb // core module
   > >     kuery:relational:sql:mariadb-jvm
   > >     kuery:relational:sql:mariadb-native
   > >     kuery:relational:sql:postgres // core module
   > >     kuery:relational:sql:postgres-jvm
   > >     kuery:relational:sql:postgres-native
   > >   ``` 

    1. [ ] CREATE
    2. [ ] Update
    3. [ ] Delete
    4. [ ] Read
5. [ ] Others
   > Suggest for new database implementations here

# Features 
1. [ ] Coroutines
   > This takes precedence after implementation of the mariadb 