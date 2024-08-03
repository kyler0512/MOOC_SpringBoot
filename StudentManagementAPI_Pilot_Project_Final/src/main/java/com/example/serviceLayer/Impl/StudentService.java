package com.example.serviceLayer.Impl;

import com.example.repositoryLayer.Entities.StudentEntity;
import com.example.repositoryLayer.IBaseStudentRepository;
import com.example.repositoryLayer.IStudentRepository;
import com.example.serviceLayer.DTOs.ErrorDTO;
import com.example.serviceLayer.DTOs.StudentDTO;
import com.example.serviceLayer.IStudentService;
import com.example.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService implements IStudentService {

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    IBaseStudentRepository baseStudentRepository;

    @Override
    public List<StudentDTO> read() {
        var studentEntities= baseStudentRepository.findAll();
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        for(int i=0; i<studentEntities.size(); i++)
        {
            var studentDTO = new StudentDTO(studentEntities.get(i).getId(), studentEntities.get(i).getFirstName(), studentEntities.get(i).getLastName(), studentEntities.get(i).getPhone());
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO read(int id) {
        var studentEntity = studentRepository.read(id);
        return new StudentDTO(studentEntity.getId(), studentEntity.getFirstName(), studentEntity.getLastName(), studentEntity.getPhone());
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        var studentEntity = new StudentEntity(studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getPhone());
        baseStudentRepository.save(studentEntity);
        return studentDTO;
    }

    @Override
    public void update(int id, StudentDTO studentDTO) {
        var studentEntity = baseStudentRepository.findById(id).get();
        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setPhone(studentDTO.getPhone());

        baseStudentRepository.save(studentEntity);
    }

    @Override
    public void delete(int id) {
        baseStudentRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> read(int pageNumber, int pageSize, String sortField, String sortType) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        //TODO: add paging and sorting logic here
        //Auto Mapper[OPTIONAL]
        return studentDTOList;
    }

    @Override
    public List<StudentDTO> read(String searchField, String searchValue) {
        Stream<StudentEntity> studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getLastName().contains(searchValue));
        if(searchField.equalsIgnoreCase("phone"))
            studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getPhone().contains(searchValue));

        else if(searchField.equalsIgnoreCase("firstname"))
            studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getFirstName().contains(searchValue));

        //Manual Mapper
        List<StudentDTO> studentDTOs = new ArrayList<>();
        studentsEntity.forEach(student -> {
            StudentDTO dto = new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getPhone());
            studentDTOs.add(dto);
        });

        //Auto Mapper[OPTIONAL]
        return studentDTOs;
    }

    @Override
    public ErrorDTO validateStudent(StudentDTO student) {
        //validate "Name"
        ErrorDTO errorDTO = new ErrorDTO();
        if (student.getFirstName().length() > 50 || student.getLastName().length() > 50) {
            errorDTO.setMessage(ErrorMessage.ERROR_STUDENT_NAME);
            errorDTO.setDestination("validateStudent(student)");
        }

        //TODO: add validation logic here, e.g: validate phone number must has 10 digits

        return errorDTO;
    }
}
