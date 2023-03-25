package nuvemdesoftware.ceramicpro.controller;

import nuvemdesoftware.ceramicpro.model.Client;
import nuvemdesoftware.ceramicpro.repository.ClientRepository;
import nuvemdesoftware.ceramicpro.utils.CustomPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ResponseBody
@RestController
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    @Value( "${server.port}" )
    private String serverPort;

    private final ClientRepository _clientRepository;

    @Autowired
    public ClientController(ClientRepository _clientRepository) {
        this._clientRepository = _clientRepository;
    }

    @GetMapping(path="/getAllClients")
    public CustomPageImpl getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         @RequestParam(name = "search", defaultValue = "") String search) throws UnknownHostException {


        String hostName = InetAddress.getLocalHost().getHostName();

        if(search.equals("")) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Client> pageResult = _clientRepository.findAll(pageRequest);

            pageResult.forEach(client -> client.setImagePath("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + client.getImageName()));

            List<Client> clients = pageResult.stream().collect(toList());

            return new CustomPageImpl(clients, pageRequest, pageResult.getTotalElements());
        } else {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Client> pageResult = _clientRepository.findByClientId(search, pageRequest);

            pageResult.forEach(client -> client.setImagePath("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + client.getImageName()));

            List<Client> clients = pageResult
                    .stream()
                    .collect(toList());

            return new CustomPageImpl(clients, pageRequest, pageResult.getTotalElements());
        }

    }
}
