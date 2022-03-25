package com.witcher
package monopoly

trait Monopoly {

  /**
   * Старт игры.
   *
   * @param chips Названия фишек, принимающих участие в данной игре
   */
  def start(chips: Array[String]): Unit

  /**
   * Принудительная остановка игры.
   * Победителем в таком случае будет самый богатый игрок на момент остановки.
   */
  def finish(): Unit

  /**
   * Бросить игральные кости.
   *
   * @param first  Число, выпавшее на первой игральной кости.
   * @param second Число, выпавшее на второй игральной кости.
   */
  def rollDice(first: Int, second: Int): Unit

  /**
   * Узнать расположение фишки на игровом поле.
   *
   * @param chip Название фишки.
   * @return Номер игрового поля, на котором находится в данный момент фишка.
   */
  def whereIs(chip: String): Int

  /**
   * Узнать количество средств на счету фишки.
   *
   * @param chip Название фишки.
   * @return Количество средств.
   */
  def getAccount(chip: String): Long

  /**
   * Старт аукциона.
   */
  def startAuction(): Unit

  /**
   * Сделать ставку на аукционе.
   *
   * @param chip  Название фишки.
   * @param value Величина ставки, кратная 1_000_000 ₽.
   */
  def makeBid(chip: Chip, value: Long): Unit

  /**
   * Завершение аукциона.
   */
  def finishAuction(): Unit

  /**
   * Покупка в собственность.
   */
  def buy(): Unit

  /**
   * Получения списка недвижимости, которая была приобретена игроком.
   *
   * @param chip Название фишки.
   * @return Список номеров игровых полей, находящихся в собственности у игрока.
   */
  def properties(chip: Chip): Array[Int]

  /**
   * Имя победителя игры.
   */
  def winner(): Chip

  /**
   * Находится ли данный игрок "В Тюрьме" в качестве арестованного (не на экскурсии)?
   *
   * @param chip Название фишки.
   * @return true — игрок арестован.
   */
  def isArrested(chip: Chip): Boolean

  /**
   * Выкупиться из тюрьмы.
   */
  def ransomFromPrison(): Unit

  /**
   * Является ли данный игрок банкротом?
   *
   * @param chip Название фишки.
   * @return true — игрок банкрот.
   */
  def isBankrupt(chip: Chip): Boolean
}
