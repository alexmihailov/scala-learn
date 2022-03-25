package com.witcher
package monopoly

class Chip(val name: String, private var account: Long) {

  def addSalary(value: Long): Unit = account += value

  def getAccount: Long = account
}

object Chip {
  def chipOf(name: String, initAccount: Long): Chip = name match {
    case "Kremlin" | "Cosmonaut" | "Pointe" | "Bear" | "Matryoshka" | "Balalaika" => new Chip(name, initAccount)
    case _ => throw new IllegalArgumentException
  }
}
