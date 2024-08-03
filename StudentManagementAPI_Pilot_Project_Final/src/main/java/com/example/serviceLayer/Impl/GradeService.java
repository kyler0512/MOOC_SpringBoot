package com.example.serviceLayer.Impl;

import com.example.repositoryLayer.Entities.GradeEntity;
import com.example.repositoryLayer.IBaseGradeRepository;
import com.example.repositoryLayer.IGradeRepository;
import com.example.serviceLayer.DTOs.ErrorDTO;
import com.example.serviceLayer.DTOs.GradeDTO;
import com.example.serviceLayer.DTOs.StudentDTO;
import com.example.serviceLayer.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GradeService implements IGradeService {

    @Autowired
    IGradeRepository gradeRepository;

    @Autowired
    IBaseGradeRepository baseGradeRepository;

    @Override
    public List<GradeDTO> read() {
        var gradeEntities = baseGradeRepository.findAll();
        List<GradeDTO> gradeDTOS = new ArrayList<>();
        for(int i = 0; i < gradeEntities.size(); i++)
        {
            var gradeDTO = new GradeDTO(gradeEntities.get(i).getId(), gradeEntities.get(i).getName(), gradeEntities.get(i).getScore(), gradeEntities.get(i).getStudentId());
            gradeDTOS.add(gradeDTO);
        }
        return gradeDTOS;
    }

    @Override
    public GradeDTO read(int id) {
        var gradeEntity = gradeRepository.read(id);
        return new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(), gradeEntity.getStudentId());
    }

    @Override
    public List<GradeDTO> read(int pageNumber, int pageSize, String sortField, String sortType) {
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        //TODO: add paging and sorting logic here

        return gradeDTOS;
    }

    @Override
    public List<GradeDTO> read(String searchField, String searchValue) {
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        //TODO: add filtering logic here

        return gradeDTOS;
    }

    @Override
    public GradeDTO create(GradeDTO gradeDTO) {
        GradeEntity gradeEntity = new GradeEntity(gradeDTO.getName(), gradeDTO.getScore(), gradeDTO.getStudentId());
        baseGradeRepository.save(gradeEntity);
        return gradeDTO;
    }

    @Override
    public void update(int id, GradeDTO gradeDTO) {
        var gradeEntity = baseGradeRepository.findById(id).get();
        if (Objects.nonNull(gradeEntity)) {
            gradeEntity.setName(gradeDTO.getName());
            gradeEntity.setScore(gradeDTO.getScore());
            gradeEntity.setStudentId(gradeDTO.getStudentId());
            baseGradeRepository.save(gradeEntity);
        }
    }

    @Override
    public void delete(int id) {
        baseGradeRepository.deleteById(id);
    }

    @Override
    public ErrorDTO validateGrade(GradeDTO gradeDTO) {
        ErrorDTO errorDTO = new ErrorDTO();
        //TODO: add validation logic here, e.g: validate score must be a number [0 - 10]

        return errorDTO;
    }
}
