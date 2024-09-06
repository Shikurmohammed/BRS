package com.BRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Client;
import com.BRS.mapper.ClientMapper;

@Service
public class ClientService {

    @Autowired
    private ClientMapper clientMapper;

    public Client saveClient(Client client) {
        return clientMapper.saveClient(client);
    }
}
