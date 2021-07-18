package com.CRUD.tutorialApp.datasource

import com.CRUD.tutorialApp.model.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<Client, Long> {
}