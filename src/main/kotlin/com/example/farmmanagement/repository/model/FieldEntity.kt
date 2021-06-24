package com.example.farmmanagement.repository.model

import com.example.farmmanagement.service.model.Field
import org.locationtech.jts.geom.Geometry
import javax.persistence.*

@Entity
data class FieldEntity(
    @Id @GeneratedValue
    var id: Long? = null,
    @Column(nullable = false)
    var name: String,
    var borders: Geometry?,
    @ManyToOne
    @JoinColumn(name = "farm_id")
    var farm: FarmEntity? = null,
    ) {

    companion object ModelMapper {
        fun from(field: Field): FieldEntity {
            return FieldEntity(
                id = field.id,
                name = field.name,
                borders = field.borders,
            )
        }
    }
}