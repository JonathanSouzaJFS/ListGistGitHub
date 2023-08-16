package br.com.testbasis.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class Person : RealmObject {
    var name: String = " "
    var isCompany: Boolean = false
    var cpf: String = " "
    var corporateName: String = " "
    var cnpj: String = " "
    var phone: String = " "
    var email: String = " "
    var address: RealmList<Address> = RealmList()

    @PrimaryKey
    var id = UUID.randomUUID().toString()

    constructor()

    constructor(
        name: String,
        isCompany: Boolean,
        cpf: String,
        corporateName: String,
        cnpj: String,
        phone: String,
        email: String,
        address: RealmList<Address>
    ) {
        this.name = name
        this.isCompany = isCompany
        this.cpf = cpf
        this.corporateName = corporateName
        this.cnpj = cnpj
        this.phone = phone
        this.email = email
        this.address = address
    }

}