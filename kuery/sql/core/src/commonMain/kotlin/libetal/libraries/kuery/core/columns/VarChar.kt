package libetal.libraries.kuery.core.columns

/*
class VarChar : SizedColumn<String, Int> {

    private val collate: String
    private val uniqueOn: String
    private val unique: Boolean
    private val autoIncrement: Boolean

    constructor(
        name: String,
        size: Int = 55,
        collate: String = "",
        uniqueOn: String = "",
        unique: Boolean = false,
        primary: Boolean = false,
        autoIncrement: Boolean = false,
        parser: (String?) -> String
    ) : super(name, "`$name` VARCHAR", size, primary, false, parser) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
        this.autoIncrement = autoIncrement
    }

    constructor(
        name: String,
        default: String = "",
        size: Int = 55,
        collate: String = "",
        uniqueOn: String = "",
        unique: Boolean = false,
        autoIncrement: Boolean = false,
        parser: (String?) -> String
    ) : super(name, "`$name` VARCHAR", default, size, parser) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
        this.autoIncrement = autoIncrement
    }

    override fun String.defaultSql(): String = sqlString

}*/
