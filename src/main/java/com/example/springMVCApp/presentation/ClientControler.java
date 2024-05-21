package com.example.springMVCApp.presentation;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.service.IServices.IServiceClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ClientControler {
    private final IServiceClient serviceClient;
    @GetMapping("/listClients")
    public String listClients(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(defaultValue = "0") int numPage,
                              Model model) {
        Page<Client> clientsPage;
        if (name != null && !name.isEmpty()) {
            clientsPage = serviceClient.searchClientsByName(name, numPage);
        } else {
            clientsPage = serviceClient.listClients(numPage);
        }

        model.addAttribute("currentPage", numPage);
        model.addAttribute("totalPages", clientsPage.getTotalPages());
        model.addAttribute("listClients", clientsPage.getContent());
        model.addAttribute("name", name);
        return "client/ClientsList"; // This should be the view name
    }


    @GetMapping("/formAddClient")
    public String getFormAdd(Model model){
        model.addAttribute("client", new Client());
        return "client/formulaireAddClient";
    }

    @PostMapping("/insertClient")
    public String postInsertClient(@Valid Client c, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "client/formulaireAddClient";
        }
        else{
            serviceClient.addClient(c);
            return "redirect:/listClients";
        }
    }


    @GetMapping("/updateClient/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Client client = serviceClient.searchClient(id);
        model.addAttribute("client", client);
        return "client/updateFormClient";
    }

    @PostMapping("/updateClient")
    public String updateClient(Client client) {
        serviceClient.updateClient(client);
        return "redirect:/listClients";
    }

    @GetMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        serviceClient.removeClient(id);
        return "redirect:/listClients";
    }



}
