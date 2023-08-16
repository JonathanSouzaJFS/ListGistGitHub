package com.djekgrif.kotlinmvpkoin.ui.pages.base

import androidx.fragment.app.Fragment
import br.com.testbasis.ui.base.BasePresenter

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