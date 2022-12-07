package com.lisitsin.services;

import com.lisitsin.model.MyFile;

import java.util.List;

public interface FileService extends ServiceForDbTemplate<MyFile, Long>{
    @Override
    MyFile getById(Long id);

    @Override
    List<MyFile> getAll();

    @Override
    MyFile save(MyFile myFile);

    @Override
    MyFile update(MyFile myFile);

    @Override
    void remove(Long id);
}
