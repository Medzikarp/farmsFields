package com.example.farmmanagement.service.model


import com.example.farmmanagement.repository.model.FieldEntity
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.geotools.geometry.jts.JTS
import org.geotools.measure.Measure
import org.geotools.referencing.CRS
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.Polygon
import org.n52.jackson.datatype.jts.GeometryDeserializer
import org.n52.jackson.datatype.jts.GeometrySerializer
import org.opengis.referencing.crs.CoordinateReferenceSystem
import tech.units.indriya.unit.Units.SQUARE_METRE
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class Field (
    var id: Long? = null,
    @NotNull
    @NotEmpty(message = "Name may not be empty")
    var name: String,
    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
    var borders: Geometry?
    ) {

    fun getArea(): Measure {
        if (borders is Polygon) {
            var p = borders as Polygon
            val centroid: Point = p.centroid
            val code = "AUTO:42001," + centroid.x.toString() + "," + centroid.y
            val auto: CoordinateReferenceSystem = CRS.decode(code)
            val transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, auto)
            val projected = JTS.transform(borders, transform) as Polygon
            return Measure(projected.area, SQUARE_METRE)
        }
        return Measure(1.0, SQUARE_METRE)
    }

    companion object ModelMapper {
        fun from(fieldEntity: FieldEntity): Field {
            return Field(
                id = fieldEntity.id,
                name = fieldEntity.name,
                borders = fieldEntity.borders
            )
        }
    }
}