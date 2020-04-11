package com.elabda3.tripstask.base

class NetworkException constructor(msg:String?): Exception(msg) {

    var type:String = "app"

    constructor():this("")

    constructor(msg : String? , exception: Exception):this(msg)

    constructor(exception: Exception):this("")

}