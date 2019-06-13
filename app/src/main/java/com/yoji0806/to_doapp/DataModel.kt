package com.yoji0806.to_doapp

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaskTitleModel: RealmObject(){
    @PrimaryKey
    var id: Int = 0
    var title = ""
    var itemsLeft = 0
    var tasks : RealmList<TaskModel> = RealmList()
}

open class TaskModel: RealmObject(){
    var task =""
    var done = false
}