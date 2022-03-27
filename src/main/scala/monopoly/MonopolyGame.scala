package com.witcher
package monopoly


object MonopolyGame extends Monopoly {

  private val START_ACCOUNT = 1_500_000_000L
  private val SALARY = 200_000_000L
  private val PRISON_INDEX = 10

  private var cells: Array[Cell] = Array.empty
  private var chips: Array[Chip] = Array.empty
  private var enabled: Boolean = false

  private object ChipsMove {
    private var current: Int = 0
    def reset(): Unit = current = 0
    def nextChip(): Chip = {
      val chip = chips(current)
      current = (current + 1) % chips.length
      chip
    }
    def rollback(): Unit = {
      if (current == 0) {
        current = chips.length - 1
      } else {
        current -= 1
      }
    }
  }

  private var duplicateHolder: Option[(Chip, Int)] = None

  private def checkStartGame(enabled: Boolean = true): Unit = if (this.enabled != enabled)
    throw new IllegalStateException()


  /**
   * Старт игры.
   *
   * @param chips Названия фишек, принимающих участие в данной игре
   */
  override def start(chips: Array[String]): Unit = {
    checkStartGame(false)
    if (chips.length < 2 || chips.length > 6 || chips.toSet.size != chips.length) {
      throw new IllegalArgumentException()
    }
    this.chips = chips.map { name => Chip.chipOf(name, START_ACCOUNT) }
    this.cells = Array.fill(40) { FreeCell() }
    this.cells(0).addChips(this.chips)
    this.enabled = true
  }

  /**
   * Принудительная остановка игры.
   * Победителем в таком случае будет самый богатый игрок на момент остановки.
   */
  override def finish(): Unit = {
    checkStartGame()
    this.chips = Array.empty
    this.cells = Array.empty
    this.ChipsMove.reset()
    this.enabled = false
  }

  /**
   * Бросить игральные кости.
   *
   * @param first  Число, выпавшее на первой игральной кости.
   * @param second Число, выпавшее на второй игральной кости.
   */
  override def rollDice(first: Int, second: Int): Unit = {
    checkStartGame()

    def checkDice(dice: Int): Unit = if (dice < 1 || dice > 6) throw new IllegalArgumentException
    def checkSalary(index: Int, chip: Chip): Unit = if (index > cells.length - 1) chip.addSalary(SALARY)
    def safeIndex(index: Int) = index % cells.length
    def checkDuplicate(): Boolean = first == second
    def checkGoToPrisonInDuplicate(chip: Chip): Boolean = duplicateHolder match {
      case Some(tuple) =>
        if (tuple._1 == chip) {
          if (tuple._2 >= 2) {
            duplicateHolder = None
            return true
          }
          else {
            duplicateHolder = Some(chip, tuple._2 + 1)
            return false
          }
        } else duplicateHolder = Some(chip, 1)
        false
      case None => duplicateHolder = Some(chip, 1); false
      case _ => throw new IllegalStateException
    }

    checkDice(first)
    checkDice(second)

    val chip = ChipsMove.nextChip()
    val oldIndex = whereIs(chip.name)

    if (checkDuplicate()) {
      if (checkGoToPrisonInDuplicate(chip)) {
        chip.arrest()
        cells(PRISON_INDEX).addChip(chip)
        cells(oldIndex).removeChip(chip)
      } else {
        // Если не выпал 3-ий дубль, то следующий опять за текущим игроком.
        ChipsMove.rollback()
      }
      return
    }

    var nextIndex = oldIndex + first + second
    checkSalary(nextIndex, chip)
    nextIndex = safeIndex(nextIndex)
    if (!cells(nextIndex).addChip(chip)) {
      nextIndex += 1
      checkSalary(nextIndex, chip)
      nextIndex = safeIndex(nextIndex)
      if (!cells(nextIndex).addChip(chip)) throw new IllegalStateException
    }
    cells(oldIndex).removeChip(chip)
  }

  /**
   * Узнать расположение фишки на игровом поле.
   *
   * @param chip Название фишки.
   * @return Номер игрового поля, на котором находится в данный момент фишка.
   */
  override def whereIs(chip: String): Int = {
    checkStartGame()

    val index = cells.indexWhere(cell => cell.contains(chip))
    if (index < 0) throw new IllegalStateException
    index
  }

  /**
   * Узнать количество средств на счету фишки.
   *
   * @param chip Название фишки.
   * @return Количество средств.
   */
  override def getAccount(chip: String): Long = {
    checkStartGame()

    chips.find(p => p.name.equals(chip)).get.getAccount
  }

  /**
   * Старт аукциона.
   */
  override def startAuction(): Unit = ???

  /**
   * Сделать ставку на аукционе.
   *
   * @param chip  Название фишки.
   * @param value Величина ставки, кратная 1_000_000 ₽.
   */
  override def makeBid(chip: Chip, value: Long): Unit = ???

  /**
   * Завершение аукциона.
   */
  override def finishAuction(): Unit = ???

  /**
   * Покупка в собственность.
   */
  override def buy(): Unit = ???

  /**
   * Получения списка недвижимости, которая была приобретена игроком.
   *
   * @param chip Название фишки.
   * @return Список номеров игровых полей, находящихся в собственности у игрока.
   */
  override def properties(chip: Chip): Array[Int] = ???

  /**
   * Имя победителя игры.
   */
  override def winner(): Chip = ???

  /**
   * Находится ли данный игрок "В Тюрьме" в качестве арестованного (не на экскурсии)?
   *
   * @param chip Название фишки.
   * @return true — игрок арестован.
   */
  override def isArrested(chip: String): Boolean = {
    checkStartGame()

    chips.find(p => p.name.equals(chip)).get.isArrested
  }

  /**
   * Выкупиться из тюрьмы.
   */
  override def ransomFromPrison(): Unit = ???

  /**
   * Является ли данный игрок банкротом?
   *
   * @param chip Название фишки.
   * @return true — игрок банкрот.
   */
  override def isBankrupt(chip: Chip): Boolean = ???
}
