package br.com.testbasis.ui.base

import androidx.fragment.app.Fragment

abstract class BasePresenterFragment<P : BasePresenter<*>> : Fragment() {

    abstract val presenter: P

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unSubscribe()
    }
}