package com.example.repositoryLayer;

import com.example.repositoryLayer.Entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseStudentRepository extends JpaRepository<StudentEntity, Integer> {
     //use default method, create(), read(id), read(), update(), delete()
}
