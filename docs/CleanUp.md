# CleanUp

Some content isn't required during application runtime
and having them removed from the actual build would be an option to
consider. This is basically for applications.

## Plausible candidates

#### Database Creation Commands and Table structure commands

```kotlin 
class Users : Entity<User>
```

As of now this class is mostly used for database creation
but also database queries

#### Database columns and Entities

```kotlin
abstract class Kuery {
    val entities by lazy {
        mutableMapOf<Entitiy<*>, MutableList<Column>>
    }
}
```

`entity` property is only used during table creation and in a situation there
are hundreds of tables having all those in use might not be efficient.

##### Options

1. Clear the array if size is an issue
2. Move execution and code outside release builds
   > Implementation logic not yet considered properly and also the memory concerns haven't been put into proper
   consideration.