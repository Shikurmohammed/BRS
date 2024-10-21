package com.BRS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Log;
import com.BRS.mapper.LogMapper;

@Service
public class LogService {
    @Autowired
    LogMapper logMapper;

    public void removeLog(Long id) {
        logMapper.removeLog(id);
    }

    public List<Log> searchLog(Log Log) {
        return logMapper.searchLogByKey(Log);
    }

    public List<Log> getAllLog() {
        return logMapper.getLogList();
    }

    public Log saveLog(Log log) {

        try {
            logMapper.saveLog(log);
            System.out.println("logging");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

}
