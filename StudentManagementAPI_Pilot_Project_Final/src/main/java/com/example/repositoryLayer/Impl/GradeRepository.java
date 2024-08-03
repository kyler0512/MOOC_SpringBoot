package com.example.repositoryLayer.Impl;

import com.example.repositoryLayer.Entities.GradeEntity;
import com.example.repositoryLayer.IBaseGradeRepository;
import com.example.repositoryLayer.IGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GradeRepository implements IGradeRepository {

    @Autowired
    IBaseGradeRepository baseGradeRepository;

    @Override
    public GradeEntity read(int id) {
        return baseGradeRepository.findById(id).get();
    }
}
