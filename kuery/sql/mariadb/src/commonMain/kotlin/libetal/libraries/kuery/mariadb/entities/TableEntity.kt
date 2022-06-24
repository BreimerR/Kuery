package libetal.libraries.kuery.mariadb.entities

/**
 * This violates API design principles
 * 1. If you don't need it live it out
 **/
abstract class TableEntity<Class> : libetal.libraries.kuery.core.entities.TableEntity<Class>()
