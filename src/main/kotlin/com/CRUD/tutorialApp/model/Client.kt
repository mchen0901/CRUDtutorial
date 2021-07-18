package com.CRUD.tutorialApp.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "client")
class Client (
    @Id
    @GeneratedValue
    val id: Long,
    var name: String,
    var email: String
    ){
}