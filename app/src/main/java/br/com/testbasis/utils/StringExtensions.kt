package br.com.testbasis.utils

fun String.isCPF(): Boolean {
    if (this.isEmpty()) return false

    val numbers = this.filter { it.isDigit() }.map {
        it.toString().toInt()
    }

    if (numbers.size != 11) return false

    if (numbers.all { it == numbers[0] }) return false

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

fun String.isCNPJ(): Boolean {
    val cnpj = this
    return validateCNPJLength(cnpj) && validateCNPJRepeatedNumbers(cnpj)
            && validateCNPJVerificationDigit(true, cnpj)
            && validateCNPJVerificationDigit(false, cnpj)
}

fun String.isPhoneValid(): Boolean {
    return if (this.length in 7..12) {
        android.util.Patterns.PHONE.matcher(this).matches()
    } else {
        false
    }
}

fun String.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return this.isNotEmpty() && this.matches(emailPattern)
}

private fun validateCNPJRepeatedNumbers(cnpj: String): Boolean {
    return (0..9)
        .map { it.toString().repeat(14) }
        .map { cnpj == it }
        .all { !it }
}

private fun validateCNPJLength(cnpj: String) = cnpj.length == 14
private fun validateCNPJVerificationDigit(firstDigit: Boolean, cnpj: String): Boolean {
    val startPos = when (firstDigit) {
        true -> 11
        else -> 12
    }
    val weightOffset = when (firstDigit) {
        true -> 0
        false -> 1
    }
    val sum = (startPos downTo 0).fold(0) { acc, pos ->
        val weight = 2 + ((11 + weightOffset - pos) % 8)
        val num = cnpj[pos].toString().toInt()
        val sum = acc + (num * weight)
        sum
    }
    val result = sum % 11
    val expectedDigit = when (result) {
        0, 1 -> 0
        else -> 11 - result
    }

    val actualDigit = cnpj[startPos + 1].toString().toInt()

    return expectedDigit == actualDigit
}