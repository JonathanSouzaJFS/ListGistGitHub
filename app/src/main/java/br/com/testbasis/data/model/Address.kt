package br.com.testbasis.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class Address(
    var address: String = " ",
    var number: String = " ",
    var complement: String = " ",
    var zipCode: String = " ",
    var city: String = " ",
    var state: String = " "
) : RealmObject() {

    @PrimaryKey
    var id = UUID.randomUUID().toString()
}