package app.podrida.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class PlayerScoresConverter : AttributeConverter<Map<String, Int>, String> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, Int>?): String {
        return if (attribute.isNullOrEmpty()) "{}" else objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Int> {
        return if (dbData.isNullOrEmpty()) emptyMap() else
            objectMapper.readValue(dbData, object : TypeReference<Map<String, Int>>() {})
    }
}