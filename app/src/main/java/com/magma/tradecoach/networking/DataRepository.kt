package com.magma.tradecoach.networking

import com.magma.tradecoach.model.HomeItemModel
import com.magma.tradecoach.model.MarketCoinModel


object DataRepository {
        var data: List<MarketCoinModel>? = emptyList()
        //TODO Remove force operators
        fun homeData(): ArrayList<HomeItemModel> {
                return arrayListOf(
                        HomeItemModel(
                                data?.get(0)?.symbol!!.uppercase(),
                                data?.get(0)?.name!!,
                                "$ " + data?.get(0)?.current_price.toString(),
                                data?.get(0)?.price_change_24h.toString() + "%"
                        ), HomeItemModel(
                                data?.get(1)?.symbol!!.uppercase(),
                                data?.get(1)?.name!!,
                                "$ " + data?.get(1)?.current_price.toString(),
                                data?.get(1)?.price_change_24h.toString() + "%"
                        ), HomeItemModel(
                                data?.get(2)?.symbol!!.uppercase(),
                                data?.get(2)?.name!!,
                                "$ " + data?.get(2)?.current_price.toString(),
                                data?.get(2)?.price_change_24h.toString() + "%"
                        )
                )
        }
}
