# Database Description

## Declaration

#### Initial design

```kotlin
@Database
interface Database 
```

##### Problems

1. Different database initialization operations
2. Different database database providers
    1. Need a way to designate the provider
    2. Need a way to pass proper arguments as per provider
    3. Need a way to integrate different providers in circumstances provider doesn't exist in other targets

#### Object based design

```kotlin
@SQLite
object Database {
    @Name
    val dbName: String = ""
}

@Postgress
object Database {
   
    @Name
    val name: String = ""

    @Password
    val password: String = ""

}
```

> NB
> > Database properties can't be passed
> > as properties due to security reasons and
> > annotations requiring values passed
> > to be constants not read/retrieved properties
