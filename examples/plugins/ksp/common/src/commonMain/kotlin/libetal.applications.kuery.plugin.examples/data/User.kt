package libetal.applications.kuery.plugin.examples.data

import libetal.applications.kuery.entities.UsersEntity
import libetal.libraries.kuery.annotations.common.columns.Column
import libetal.libraries.kuery.annotations.common.columns.PrimaryKey
import libetal.libraries.kuery.annotations.common.entities.Table
import libetal.libraries.kuery.core.columns.extensions.greaterThan
import libetal.libraries.kuery.core.statements.SELECT

@Table
class User(
    val fName: String,
    @Column
    val age: Int,
    @Column("last_name")
    val lName: String,
    @PrimaryKey
    var id: Int = 0
) {
    val name: String
        get() = "$fName $lName"

    companion object{
        fun getAll() = SELECT ALL UsersEntity WHERE (UsersEntity.id greaterThan 1)
    }
}