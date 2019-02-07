package dat153.no.hvl.namequiz2.model

import android.net.Uri

class Person() {
    var name: String? = null
    var img: String? = null
    var id: Int? = null

    constructor(name: String, img: String, id: Int ):this() {
        this.name = name
        this.img = img
        this.id = id
    }
}