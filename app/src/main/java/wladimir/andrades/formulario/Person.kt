package wladimir.andrades.formulario

import java.io.Serializable
import java.util.Date

data class Person(private var name: String = "Armamento",
                  private var attribute: String = "Ninguno",
                  private var rarity: String = "Normal",
                  private var damage: Long = 0,
                  private var uses: Long = 0,
                  private var magic: Boolean = false,
                  private var dateObtained: Date = Date(0,0,0)
    )
    : Serializable {

    fun getName(): String = this.name
    fun getAttribute(): String = this.attribute
    fun getRarity(): String = this.rarity
    fun getDamage(): Long = this.damage
    fun getUses(): Long = this.uses
    fun getMagic(): Boolean = this.magic
    fun getDateObtained(): Date = this.dateObtained

    fun setName(name: String) { this.name = name }
    fun setAttribute(attribute: String) { this.attribute =  attribute}
    fun setRarity(rarity: String) { this.rarity = rarity }
    fun setDamage(damage: Long) { this.damage = damage }
    fun setUses(uses: Long) { this.uses = uses }
    fun setMagic(magic: Boolean) { this.magic = magic }
    fun setDateObtained(dateObtained: Date) { this.dateObtained = dateObtained}
}