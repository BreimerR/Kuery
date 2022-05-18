package libetal.libraries.kuery.sqlite.example.data

import libetal.libraries.kuery.annotations.common.columns.Column
import libetal.libraries.kuery.annotations.common.columns.PrimaryKey
import libetal.libraries.kuery.annotations.common.entities.Table

@Table
data class User(
    @Column
    val name: String,
    @Column
    @PrimaryKey
    var id: Int = 0
) {

    @Column // one to many
    val projects = mutableListOf<Project>()

    @Column // one to one
    val account: Account? = null

}

@Table
data class Account(
    @Column
    val user: User,
    @Column
    val contacts: List<Contact>
)

@Table
data class Contact(
    @Column
    val phone: String
)

@Table
class Project
