package com.example.farmmanagement.repository

import com.example.farmmanagement.repository.model.FarmEntity
import com.example.farmmanagement.repository.model.FieldEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldRepository : CrudRepository<FieldEntity, Long> {

    fun findByFarm(farm: FarmEntity): List<FieldEntity>

}