# SQLITE

## Entity

#### Properties

##### id

In sqlite every entity has an ID.
The ID can be user defined or system defined
This option is provided by an open property id

```kotlin
abstract class Entity<`MappingClass`>(type: Entity.Type, val id: Column = Number("id"))
```