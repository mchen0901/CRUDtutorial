package com.CRUD.tutorialApp.controller

import com.CRUD.tutorialApp.datasource.ClientRepository
import com.CRUD.tutorialApp.model.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException


@RestController
@RequestMapping("/clients")
class ClientController(clientRepository: ClientRepository) {

    @Autowired
    val clientRepository = clientRepository;

    @GetMapping
    fun getClients(): List<Client>{
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    fun getClient(@PathVariable id: Long): Client {
        return clientRepository.findById(id).orElseThrow { RuntimeException() }
    }

    @PostMapping
    @Throws(URISyntaxException::class)
    fun createClient(@RequestBody client: Client): ResponseEntity<*> {
        val savedClient = clientRepository.save(client)
        return ResponseEntity.created(URI("/clients/" + savedClient.id)).body<Any>(savedClient)
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Long, @RequestBody client: Client): ResponseEntity<*> {
        var currentClient = clientRepository.findById(id).orElseThrow { RuntimeException() }
        currentClient.name = client.name;
        currentClient.email = client.email;
        currentClient = clientRepository.save(client)
        return ResponseEntity.ok<Any>(currentClient)
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long): ResponseEntity<*> {
        clientRepository.deleteById(id)
        return ResponseEntity.ok().build<Any>()
    }


}