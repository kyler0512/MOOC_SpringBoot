package com.example.repositoryLayer;

import com.example.repositoryLayer.Entities.GradeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IGradeRepository {
    public GradeEntity read(int id);
}
