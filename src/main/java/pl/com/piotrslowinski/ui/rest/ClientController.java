package pl.com.piotrslowinski.ui.rest;

import pl.com.piotrslowinski.application.ClientDto;
import pl.com.piotrslowinski.application.ClientFinder;
import pl.com.piotrslowinski.application.CommandGateway;
import pl.com.piotrslowinski.infrastructure.Secured;
import pl.com.piotrslowinski.model.commands.RegisterClientCommand;
import org.springframework.web.bind.annotation.*;

@Secured
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
