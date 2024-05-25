package com.example.springMVCApp.service.ServicesImpl;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.repositories.IClientRepository;
import com.example.springMVCApp.service.IServices.IServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiceClientImpl implements IServiceClient {
    private final IClientRepository clientRepository;

    @Override
    public void addClient(Client c) {
        clientRepository.save(c);
    }

    @Override
    public void removeClient(Integer clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setDeleted(true);
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Client c) {
        clientRepository.save(c);

    }

    @Override
    public Client searchClient(Integer id) {
        Optional<Client> c = clientRepository.findById(id);
        if (c.isEmpty()) throw new RuntimeException("Client not found");
        else return c.get();
    }

    @Override
    public List<Client> listClients() {
        return clientRepository.findAllActiveClients();
    }

    @Override
    public Page<Client> listClients(int numPage) {
        return clientRepository.findAllActive(PageRequest.of(numPage, 3));
    }

    @Override
    public Page<Client> searchClientsByName(String name, int numPage) {
        return clientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, PageRequest.of(numPage, 3));
    }
}
