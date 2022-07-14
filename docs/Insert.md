# Insert

Features

1. [ ] Sing Insert
2. [ ] Multiple Insert
3. [ ] Relational Insert
4. [ ] View Insert ```NOTE: quite similar to relational insert```

###### Simple Insert

```kotlin
INSERT INTO Users(Users.name, Users.age) VALUES {
    row("Breimer", 12)
    row("Lazie", 1)
}
```

## Limitations And Reasons

The above syntax is best since

1. Columns passed can not be tied directly to values passed unless maps are used
   `Will go against SQL/Near SQL requirements of the library`
2. It's a proper entry point from an sql starting point. 

## Future implementations
1. [ ] Code generation for proper mapping of columns to values per sql
    `Assumes SQL's might be static i.e one will Insert Users.name alone.`
2. 

#### Bound Statements

```kotlin
SELECT * Users WHERE (Users.name equals "12".bound)
```