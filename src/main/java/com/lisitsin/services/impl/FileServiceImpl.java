package com.lisitsin.services.impl;

import com.lisitsin.model.MyFile;
import com.lisitsin.repository.FileRepository;
import com.lisitsin.services.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {

    FileRepository repository;


    public FileServiceImpl(FileRepository repository) {
        this.repository = repository;
    }

    @Override
    public MyFile getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<MyFile> getAll() {
        return repository.getAll();
    }

    @Override
    public MyFile save(MyFile myFile) {
        if (myFile.getName() != null && !myFile.getName().trim().isEmpty() && myFile.getEvent() != null) {
            return repository.save(myFile);
        }
            return null;
    }

    @Override
    public MyFile update(MyFile myFile) {
        if (myFile.getName() != null && !myFile.getName().trim().isEmpty() && myFile.getEvent() != null) {
            return repository.update(myFile);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        repository.remove(id);
    }
}
