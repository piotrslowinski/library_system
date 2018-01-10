package com.pl.piotrslowinski.ui.rest;

import com.pl.piotrslowinski.application.ClientDto;
import com.pl.piotrslowinski.application.ClientFinder;
import com.pl.piotrslowinski.application.CommandGateway;
import com.pl.piotrslowinski.model.commands.RegisterClientCommand;
import org.springframework.web.bind.annotation.*;

/**
 * Created by user on 08.01.2018.
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private CommandGateway gateway;
    private ClientFinder clientFinder;


    public ClientController(CommandGateway gateway, ClientFinder clientFinder) {
        this.gateway = gateway;
        this.clientFinder = clientFinder;
    }

    @GetMapping("/{clientId}")
    public ClientDto get(@PathVariable Integer clientId){
        return clientFinder.get(clientId);
    }

    @PutMapping
    public void register(@RequestBody RegisterClientCommand cmd){
        gateway.execute(cmd);
    }
}
