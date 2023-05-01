package com.canerture.satellitesapp.common

object Constants {

    object Json {
        const val SATELLITE_LIST_JSON = "satellite-list.json"
        const val SATELLITE_DETAIL_JSON = "satellite-detail.json"
        const val SATELLITE_POSITIONS_JSON = "positions.json"
    }

    object Name {
        const val ID = "id"
        const val ACTIVE = "active"
        const val NAME = "name"
        const val COST_PER_LAUNCH = "cost_per_launch"
        const val FIRST_FLIGHT = "first_flight"
        const val HEIGHT = "height"
        const val MASS = "mass"
    }

    object Route {
        const val satellitesNavigationRoute = "satellites_route"
        const val detailNavigationRoute = "detail_route"
    }
}