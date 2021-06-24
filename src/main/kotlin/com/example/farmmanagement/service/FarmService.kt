package com.example.farmmanagement.service

import com.example.farmmanagement.error.InvalidRequestException
import com.example.farmmanagement.repository.model.FarmEntity
import com.example.farmmanagement.repository.FarmRepository
import com.example.farmmanagement.repository.FieldRepository
import com.example.farmmanagement.repository.model.FieldEntity
import com.example.farmmanagement.service.model.Farm
import com.example.farmmanagement.service.model.Field
import org.locationtech.jts.geom.Polygon
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 *  Some of CRUD operations for Farm and Fields with Entity | DTO conversion.
 *  Accepts only Polygons for Field's Geometry.
 */

@Service
@Transactional
class FarmService(private val farmRepository: FarmRepository, private val fieldRepository: FieldRepository) {

    fun createFarm(farm: Farm): Farm {
        return Farm.from(farmRepository.save(FarmEntity.from(farm)))
    }

    fun getFarmById(farmId: Long): Farm {
        return Farm.from(farmRepository.findById(farmId).orElseThrow())
    }

    fun addFieldToFarm(farmId: Long, field: Field): Field{
        if(field.borders !is Polygon) {
            throw InvalidRequestException("Only polygon geometry supported.")
        }
        val farmEntity =  farmRepository.findById(farmId).orElseThrow()
        val fieldEntityToStore = FieldEntity.from(field)
        fieldEntityToStore.farm = farmEntity
        return Field.from(fieldRepository.save(fieldEntityToStore))
    }

    fun getFieldsByFarmId(farmId: Long): List<Field> {
        val farm = farmRepository.findById(farmId).orElseThrow()
        return fieldRepository.findByFarm(farm).map { Field.from(it) }
    }

    fun deleteFarm(farmId: Long) {
        val farm =  farmRepository.findById(farmId).orElseThrow()
        farmRepository.delete(farm)
    }

    fun listFarms(): List<Farm>? {
        return farmRepository.findAll().map { Farm.from(it) }
    }
}