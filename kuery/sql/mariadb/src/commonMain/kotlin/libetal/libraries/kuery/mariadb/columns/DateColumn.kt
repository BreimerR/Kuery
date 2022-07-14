package libetal.libraries.kuery.mariadb.columns

import kotlinx.datetime.LocalDate


private fun parseDate(value: String?) = value?.let { LocalDate.parse(it) }

