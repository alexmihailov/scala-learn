package com.witcher
package monopoly

import scala.collection.mutable.ListBuffer

sealed trait Cell {

  private val chips: ListBuffer[Chip] = ListBuffer.empty

  def addChip(chip: Chip): Boolean =
    if (chips.size == 3) false
    else { chips += chip; true }

  def addChips(chips: Array[Chip]): Unit = this.chips.addAll(chips)

  def removeChip(chip: Chip): Unit = chips -= chip

  def contains(chip: Chip): Boolean = chips.contains(chip)

  def contains(chip: String): Boolean = chips.exists(p => p.name.equals(chip))
}

case class FreeCell() extends Cell



