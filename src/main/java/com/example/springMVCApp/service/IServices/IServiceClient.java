package com.example.springMVCApp.service.IServices;

import com.example.springMVCApp.dao.entities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceClient {
    void addClient(Client c);
    void removeClient(Integer id);
    void updateClient(Client c);
    Client searchClient(Integer id);
    List<Client> listClients();
    Page<Client> listClients(int numPage);
    Page<Client> searchClientsByName(String name, int numPage);
}
