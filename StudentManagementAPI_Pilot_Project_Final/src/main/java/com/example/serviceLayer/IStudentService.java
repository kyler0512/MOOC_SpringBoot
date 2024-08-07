package com.example.serviceLayer;

import com.example.serviceLayer.DTOs.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStudentService {
    List<StudentDTO> read();
    StudentDTO read(int id);
    StudentDTO create(StudentDTO student);
    void update(int id, StudentDTO student);
    void delete(int id);

    //additional methods
    List<StudentDTO> read(int pageNumber, int pageSize, String sortField, String sortType);
    List<StudentDTO> read(String searchField, String searchValue);
}
