# Insert

Features

1. [ ] Sing Insert
2. [ ] Multiple Insert
3. [ ] Relational Insert
4. [ ] View Insert ```NOTE: quite similar to relational insert```

###### Simple Insert

```kotlin
Users.insert(user) { user: User ->
    // Called on insert
    // No fail handlers provided here
}
```

```kotlin
Users.insert(user, handler) { user: User ->
    // error handler provided here 
}
```

###### Insert Where ```I don't think insert where is a thing should be update```

```kotlin
Users.insert(user) where expression { user: User ->

}
```

#### Bound Statements

```kotlin
SELECT * Users WHERE (Users.name equals "12".bound)
```