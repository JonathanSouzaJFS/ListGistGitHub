package br.com.testbasis.ui.base

interface BasePresenter<V : BaseView> {
    val view: V
    fun subscribe(){}
    fun unSubscribe(){}
}