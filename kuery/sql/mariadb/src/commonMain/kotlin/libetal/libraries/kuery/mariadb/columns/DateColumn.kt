package libetal.libraries.kuery.mariadb.columns

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.mariadb.entities.TableEntity


private fun parseDate(value: String?) = value?.let { LocalDate.parse(it) }

