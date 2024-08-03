package com.example.repositoryLayer;

import com.example.repositoryLayer.Entities.StudentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository {
//    public List<StudentEntity> read();
    public StudentEntity read(int id);
//    public StudentDTO create(StudentEntity student);
//    public void update(int id, StudentEntity student);
//    public void delete(int id);

    //additional methods
//    public List<StudentEntity> read(int pageNumber, int pageSize, String sortField, String sortType);
//    public List<StudentEntity> read(String searchField, String searchValue);
}
