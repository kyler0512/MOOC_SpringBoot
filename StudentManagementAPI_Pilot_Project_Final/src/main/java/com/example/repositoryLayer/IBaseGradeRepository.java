package com.example.repositoryLayer;

import com.example.repositoryLayer.Entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBaseGradeRepository extends JpaRepository<GradeEntity, Integer> {
}
