package com.example.farmmanagement.repository

import com.example.farmmanagement.repository.model.FarmEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FarmRepository : CrudRepository<FarmEntity, Long> {

}