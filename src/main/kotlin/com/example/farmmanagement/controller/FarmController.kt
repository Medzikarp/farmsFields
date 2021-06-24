package com.example.farmmanagement.controller

import com.example.farmmanagement.service.FarmService
import com.example.farmmanagement.service.model.Farm
import com.example.farmmanagement.service.model.Field
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Handle some of CRUD operations.
 * Also maps GeoJSON to field's Geometry.
 */

@RestController
@RequestMapping("/api/farms")
class FarmController (private val farmService: FarmService){

    @PostMapping
    @ResponseStatus(CREATED)
    fun createFarm(@Valid @RequestBody farm: Farm): Farm {
        return farmService.createFarm(farm)
    }

    @PostMapping("/{farmId}/fields")
    @ResponseStatus(CREATED)
    fun addField(@PathVariable farmId: Long, @Valid @RequestBody field: Field) {
        farmService.addFieldToFarm(farmId, field)
    }

    @GetMapping("/{farmId}")
    fun getFarm(@PathVariable farmId: Long): Farm {
        return farmService.getFarmById(farmId)
    }

    @DeleteMapping("/{farmId}/fields")
    fun deleteFarm(@PathVariable farmId: Long) {
         farmService.deleteFarm(farmId);
    }

    @GetMapping("/{farmId}/fields")
    fun getFieldsForFarm(@PathVariable farmId: Long): List<Field> {
        return farmService.getFieldsByFarmId(farmId)
    }

    @GetMapping
    fun listFarms(): ResponseEntity<List<Farm>>{
        val farms = farmService.listFarms()
        if (farms.isNullOrEmpty()) {
            return ResponseEntity(null, NO_CONTENT)
        }
        return ResponseEntity(farms, OK)
    }

}