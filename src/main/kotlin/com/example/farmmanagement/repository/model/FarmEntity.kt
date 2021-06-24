package com.example.farmmanagement.repository.model

import com.example.farmmanagement.service.model.Farm
import javax.persistence.*

@Entity
data class FarmEntity(
    @Id @GeneratedValue
    var id: Long? = null,
    @Column(nullable = false)
    var name: String,
    var note: String?,
    @OneToMany(mappedBy = "farm", fetch = FetchType.EAGER, orphanRemoval = true)
    var fields: MutableList<FieldEntity>?
    ) {

    companion object ModelMapper {
        fun from(farm: Farm): FarmEntity {
            return FarmEntity(
                id = farm.id,
                name = farm.name,
                note = farm.note,
                fields = farm.fields?.map { FieldEntity.from(it) }?.toMutableList()
            )
        }
    }
}