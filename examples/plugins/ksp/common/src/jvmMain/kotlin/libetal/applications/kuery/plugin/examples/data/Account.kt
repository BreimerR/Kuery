package libetal.applications.kuery.plugin.examples.data

import libetal.libraries.kuery.annotations.common.entities.Table

@Table
data class Account(
    val user: User,
    val password: String
)