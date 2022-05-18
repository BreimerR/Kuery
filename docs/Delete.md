# Delete

Features

1. [ ] Single Delete
2. [ ] Mass delete
3. [ ] Column delete
4. [ ] Columns delete


###### Delete

Cons

1. Not sure if it's delete all or one

```kotlin
Users.delete(user, errorHandler) { primaryKey ->
    // called once the item was deleted
}
```

###### Delete all

Cons

1. Tables with no primary key not considered here Pros
2. Does provide a more SQL like structure

```kotlin 
(Users.delete(user, errorHandler) where (Users.id == 12)) all { keys:List<PrimaryKeys> ->

}
```

###### Delete where

```kotlin
Users.delete(user)
```

