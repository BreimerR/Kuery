package libetal.libraries.kuery.core


import libetal.libraries.kuery.core.entities.Entity

/**
 * Every extend this class to get
 * an instance of the required
 * database
 **/
abstract class KSQL : Kuery<Entity<*>>()