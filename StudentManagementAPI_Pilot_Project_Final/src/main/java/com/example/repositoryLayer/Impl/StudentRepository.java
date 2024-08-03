package com.example.repositoryLayer.Impl;

import com.example.repositoryLayer.Entities.StudentEntity;
import com.example.repositoryLayer.IBaseStudentRepository;
import com.example.repositoryLayer.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository implements IStudentRepository {

    @Autowired
    IBaseStudentRepository baseStudentRepository;

//    @Override
//    public List<StudentDTO> read() {
//        var studentEntities= baseStudentRepository.findAll();
//        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
//        for(int i=0; i<studentEntities.size(); i++)
//        {
//            var studentDTO = new StudentDTO(studentEntities.get(i).getId(), studentEntities.get(i).getFirstName(), studentEntities.get(i).getLastName(), studentEntities.get(i).getPhone());
//            studentDTOS.add(studentDTO);
//        }
//        return studentDTOS;
//    }

    @Override
    public StudentEntity read(int id) {
        return baseStudentRepository.findById(id).get();
    }

//    @Override
//    public StudentDTO create(StudentDTO studentDTO) {
//        var studentEntity = new StudentEntity(studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getPhone());
//        baseStudentRepository.save(studentEntity);
//        return studentDTO;
//    }
//
//    @Override
//    public void update(int id, StudentDTO studentDTO) {
//        var studentEntity = baseStudentRepository.findById(id).get();
//        studentEntity.setFirstName(studentDTO.getFirstName());
//        studentEntity.setLastName(studentDTO.getLastName());
//        studentEntity.setPhone(studentDTO.getPhone());
//
//        baseStudentRepository.save(studentEntity);
//    }
//
//    @Override
//    public void delete(int id) {
//        baseStudentRepository.deleteById(id);
//    }
//
//    @Override
//    public List<StudentDTO> read(int pageNumber, int pageSize, String sortField, String sortType) {
//        Pageable pageable = null;
//
//        var newSortType = Sort.Direction.ASC; //A-Za-z 0-9
//        if(sortType.toLowerCase().equals("desc"))
//            newSortType = Sort.Direction.DESC;
//
//        pageable = PageRequest.of(pageNumber, pageSize, newSortType, sortField.replace("\"", "").trim());
//
//        //Manual Mapper
//        var studentsEntity = baseStudentRepository.findAll(pageable);
//        List<StudentDTO> studentDTOs = new ArrayList<>();
//
//        studentsEntity.forEach(student -> {
//            StudentDTO dto = new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getPhone());
//            studentDTOs.add(dto);
//        });
//
//        //Auto Mapper[OPTIONAL]
//        return studentDTOs;
//    }
//
//    @Override
//    public List<StudentDTO> read(String searchField, String searchValue) {
//        Stream<StudentEntity> studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getLastName().contains(searchValue));
//        if(searchField.toLowerCase().equals("phone"))
//            studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getPhone().contains(searchValue));
//
//        else if(searchField.toLowerCase().equals("firstname"))
//            studentsEntity = baseStudentRepository.findAll().stream().filter(s -> s.getFirstName().contains(searchValue));
//
//        //Manual Mapper
//        List<StudentDTO> studentDTOs = new ArrayList<>();
//        studentsEntity.forEach(student -> {
//            StudentDTO dto = new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getPhone());
//            studentDTOs.add(dto);
//        });
//
//        //Auto Mapper[OPTIONAL]
//        return studentDTOs;
//    }
}
