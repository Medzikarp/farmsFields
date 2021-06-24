package com.example.farmmanagement.service.model

import com.example.farmmanagement.repository.model.FarmEntity
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class Farm(
    var id: Long? = null,
    @NotNull
    @NotEmpty(message = "Name may not be empty")
    var name: String,
    var note: String?,
    var fields: MutableList<Field>?
    ) {
    companion object ModelMapper {
        fun from(farmEntity: FarmEntity): Farm {
            return Farm(
                id = farmEntity.id,
                name = farmEntity.name,
                note = farmEntity.note,
                fields = farmEntity.fields?.map { Field.from(it) }?.toMutableList()
            )
        }
    }
}