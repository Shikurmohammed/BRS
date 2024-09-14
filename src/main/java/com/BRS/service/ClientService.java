package com.BRS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BRS.entity.Client;
import com.BRS.mapper.ClientMapper;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientMapper clientMapper;

    public Client saveClient(Client client) {
        int savedClient = clientMapper.saveClient(client);
        System.out.println("Saving client");
        if (savedClient > 0)
            return client;
        else
            throw new RuntimeException("Unable to save the client!");
    }

    public List<Client> getAllClients() {
        return clientMapper.getAllClients();
    }

    public Client updateClient(Client client) {
        int updatedClient = clientMapper.updateClient(client);
        if (updatedClient > 0)
            return client;
        else
            throw new RuntimeException("Unable to Update client information!");
    }

    public Boolean deleteClient(Long id) {
        Boolean flag = false;
        try {
            Optional<Client> client = clientMapper.getClient(id);
            if (client.isPresent()) {
                clientMapper.deleteClient(id);
                flag = true;
            } else {
                flag = false;

            }

        } catch (Exception e) {
            flag = false;

        }
        return flag;
    }

    public Client getClient(Long id) {

        Optional<Client> client = clientMapper.getClient(id);
        return client.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) clientMapper.findByUsername(username);
    }
}
