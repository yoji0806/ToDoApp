package com.yoji0806.to_doapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaskTitleModel(): RealmObject(){
    @PrimaryKey
    var id: Int = 0
    var title = ""
    var itemsLeft = 0
}

open class TaskModel(): RealmObject(){
    @PrimaryKey
    var id = 0
    var task =""
    var done = false
}