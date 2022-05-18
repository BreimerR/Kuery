# Relationships

1. [ ] One to One
2. [ ] One to Many
3. [ ] Many to Many

## One To One

```kotlin
@Table
data class User(
    @Column
    val name: String,
    @Column
    val account: Account
) {
    @Column
    var id: Int = 0
}

@Table
data class Account(
    val password: String
) {
    @PrimaryKey
    var id: Int = 0
}
```

## One to many

| Problems                                                            | Solutions               |
|---------------------------------------------------------------------|-------------------------|
| Declare foreign properties without custom data types                | Have custom data types  |
| GET / SET foreign properties ```val projects = listOf<Project>()``` |                         |

```kotlin
@Table
data class Project(
    @Column
    val name: String
)

@Table
data class User(
    @Column
    val name: String
) {
    /**
     * This could be loaded once the
     * data is received from the database
     * A limitation of get exists
     * on get call you will get the actual value
     * and not invoke the get
     * Other option is to have
     * val projects by readable<List<Project>>()
     **/
    val projects = mutableListOf<Project>()

}

@Table
data class Project
```

```kotlin
@Table
data class User {
    /*Generated property*/
    private val projectsDelegate = wriatableDelegate<Project>()

    @Delegated
    val projects: MutableList<Project> by projectsDelegate
    

}

@Table
data class Project {

}
```

## Many to Many



