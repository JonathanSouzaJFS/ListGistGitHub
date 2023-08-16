package br.com.testbasis.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import br.com.testbasis.R
import br.com.testbasis.data.model.Address
import br.com.testbasis.ui.base.BaseAlertDialog

class CreateAddressDialog(context: Context) : BaseAlertDialog() {

    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_create_address, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    private val okayButton: Button by lazy {
        dialogView.findViewById(R.id.okBtn)
    }

    private val cancelButton: Button by lazy {
        dialogView.findViewById(R.id.cancelBtn)
    }

    private val address: EditText by lazy {
        dialogView.findViewById(R.id.personAddress)
    }

    private val neighborhood: EditText by lazy {
        dialogView.findViewById(R.id.personNeighborhood)
    }

    private val zipCode: EditText by lazy {
        dialogView.findViewById(R.id.personZipCode)
    }

    private val city: EditText by lazy {
        dialogView.findViewById(R.id.personCity)
    }

    private val number: EditText by lazy {
        dialogView.findViewById(R.id.personNumber)
    }

    private val complement: EditText by lazy {
        dialogView.findViewById(R.id.personComplement)
    }

    private val state: EditText by lazy {
        dialogView.findViewById(R.id.personUf)
    }

    fun cancelClickListener(func: (() -> Unit)? = null) =
        with(cancelButton) {
            setClickListener(func)
        }

    fun okClickListener(func: ((address: Address) -> Unit)? = null) =
        with(okayButton) {
            setAddressClickListener(func)
        }


    private fun View.setAddressClickListener(func: ((address: Address) -> Unit)?) =
        setOnClickListener {

            var validation = true
            if (address.text.toString().isEmpty()) {
                address.error = "Endereço inválido!"
                validation = false
            }
            if (neighborhood.text.toString().isEmpty()) {
                neighborhood.error = "Bairro inválido!"
                validation = false
            }
            if (zipCode.text.toString().isEmpty()) {
                zipCode.error = "CEP inválido!"
                validation = false
            }
            if (city.text.toString().isEmpty()) {
                city.error = "Cidade inválida!"
                validation = false
            }
            if (state.text.toString().isEmpty()) {
                state.error = "UF inválido!"
                validation = false
            }
            if (validation) {
                func?.invoke(
                    Address(
                        address.text.toString(),
                        number.text.toString(),
                        complement.text.toString(),
                        zipCode.text.toString(),
                        city.text.toString(),
                        state.text.toString()
                    )
                )
                dialog?.dismiss()
            }
        }

    private fun View.setClickListener(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
}