# Tables

Database tables will simply be classes

```kotlin
@Table
data class User(
    @Column
    val name: String = "",
    @Column
    val age: Int = 0
) {
    @PrimaryKey
    var id: Int = 0

    @Retrieved
    val account: Account? = null

}

@Table
data class Account(
    @Column
    val name: String
) {
    @Column
    @Foreign(User::class, "id")
    var id: Int = 0
} 
```

## Generated functions

```kotlin

fun User.insert(project: Project) {

}

fun User.insert() {

}

fun User.fetch(vararg where: Column): User {  // By default, it uses non-null properties but if specified then it will use the specified columns 

}

fun User.fetchAll(vararg name: Column = arrayOf(column1, column2)): List<User> {

}

fun User.select(project: Project): Project?

fun User.selectProjects(columns: MutableList<Column>, where: Statement)

```
