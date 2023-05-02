package bstrees.model.dataBases.converters.utils

interface StringConverter<T> {
    fun toString(value: T): String
    fun fromString(value: String): T
}

interface ComparableStringConverter<K: Comparable<K>>: StringConverter<K>

object StringStringConverter : ComparableStringConverter<String> {
    override fun toString(value: String): String = value

    override fun fromString(value: String): String = value
}

object IntStringConverter : ComparableStringConverter<Int> {
    override fun toString(value: Int): String = value.toString()

    override fun fromString(value: String): Int = value.toInt()
}

fun createStringConverter(type: String): ComparableStringConverter<*> = when (type) {
    "String" -> StringStringConverter
    "Int" -> IntStringConverter
    else -> throw IllegalArgumentException("Unsupported type $type")
}

