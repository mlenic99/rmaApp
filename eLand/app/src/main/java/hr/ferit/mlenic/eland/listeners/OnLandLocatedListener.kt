package hr.ferit.mlenic.eland.listeners

import hr.ferit.mlenic.eland.model.Land

interface OnLandLocatedListener {
    fun onLandLocatedListener(land: Land)
}