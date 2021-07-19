package com.CRUD.tutorialApp.controller

import com.CRUD.tutorialApp.datasource.ClientRepository
import com.CRUD.tutorialApp.model.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
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
    fun getClient(@PathVariable id: Long): Client? {
        try {
            return clientRepository.findByIdOrNull(id);
        } catch (ex: Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User by $id cannot be Not Found");
        }
    }

    @PostMapping
    @Throws(URISyntaxException::class)
    fun createClient(@RequestBody client: Client): ResponseEntity<*> {
        val savedClient = clientRepository.save(client)
        return ResponseEntity.created(URI("/clients/" + savedClient.id)).body<Any>(savedClient)
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Long, @RequestBody client: Client): ResponseEntity<*> {
        try{
            var currentClient = clientRepository.findByIdOrNull(id)
            if (currentClient != null) currentClient.name = client.name
            if (currentClient != null) currentClient.email= client.email
            currentClient = clientRepository.save(client)
            return ResponseEntity.ok(currentClient);
        }
        catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "id $id is invalid");
        }
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long): ResponseEntity<*> {
        try{
            clientRepository.deleteById(id)
            return ResponseEntity.ok().build<Any>()
        }
        catch (ex: Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "$id id is invalid")
        }
    }

}