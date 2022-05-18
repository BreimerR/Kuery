# Migrations

Database migrations are quite difficult to handle
at times and having a fluid transition from one version
of an existing database to a different one might help the
situation

## Abstract Migration

In this model on database creation
an abstract class is created requiring the user
to implement mapping for each mapped class i.e.

```kotlin
@Table
data class User(

)

@SQLite(1)
object Database

//// BEGIN GENERATED CODE////
abstract class MigrationHandlerVersion1 {
    fun users(columns: Map<Column, String>) {

    }
}
///// END GENERATED CODE/////
```

The declaration structure is
> fun `table-name`(columns:Map<Column,String>){  
> }
>
> This happens for all the tables that
>