package com.f_a.project_moca.model

class MTVShow {
    companion object Factory {
        fun create(): MTVShow = MTVShow()
    }

    var id: String? = null
    var title: String? = null
    var genre: String? = null
    var release: String? = null
    var rating: String? = null
    var deskripsi: String? = null
    var poster: String? = null
}