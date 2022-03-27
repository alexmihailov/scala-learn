package com.witcher
package monopoly

case class Chip(name: String, private var account: Long) {

  private var arrested: Boolean = false

  def addSalary(value: Long): Unit = account += value

  def getAccount: Long = account

  def arrest(): Unit = if (arrested) {
    throw new IllegalStateException()
  } else {
    arrested = true
  }
  def isArrested: Boolean = arrested
}

object Chip {
  def chipOf(name: String, initAccount: Long): Chip = name match {
    case "Kremlin" | "Cosmonaut" | "Pointe" | "Bear" | "Matryoshka" | "Balalaika" => new Chip(name, initAccount)
    case _ => throw new IllegalArgumentException
  }
}
