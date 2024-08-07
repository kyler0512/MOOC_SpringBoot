package com.example.serviceLayer.Impl;

import com.example.repositoryLayer.Entities.GradeEntity;
import com.example.repositoryLayer.IBaseGradeRepository;
import com.example.repositoryLayer.IGradeRepository;
import com.example.serviceLayer.DTOs.ErrorDTO;
import com.example.serviceLayer.DTOs.GradeDTO;
import com.example.serviceLayer.DTOs.StudentDTO;
import com.example.serviceLayer.IGradeService;
import com.example.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
        Sort.Direction currentSortType = Sort.Direction.ASC;
        if (sortType.equalsIgnoreCase("desc")) {
            currentSortType = Sort.Direction.DESC;
        }
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(currentSortType, sortField.replace("\"", "").trim()));
        Page<GradeEntity> dataEntityList = baseGradeRepository.findAll(paging);
        dataEntityList.stream().forEach(gradeEntity -> {
            GradeDTO gradeDTO = new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(), gradeEntity.getStudentId());
            gradeDTOS.add(gradeDTO);
        });
        return gradeDTOS;
    }

    @Override
    public List<GradeDTO> read(String searchField, String searchValue) {
        List<GradeDTO> gradeDTOS = new ArrayList<>();
        Stream<GradeEntity> gradeEntities;
        switch (searchField.toLowerCase()) {
            case "name" -> gradeEntities = baseGradeRepository.findAll().stream().filter(
                    gradeEntity -> gradeEntity.getName().equals(searchValue)
            );
            case "score" -> gradeEntities = baseGradeRepository.findAll().stream().filter(
                    gradeEntity -> Objects.equals(gradeEntity.getScore(), Double.valueOf(searchValue))
            );
            case "studentid" -> gradeEntities = baseGradeRepository.findAll().stream().filter(
                    gradeEntity -> Objects.equals(gradeEntity.getStudentId(), Integer.valueOf(searchValue))
            );
            default -> gradeEntities = baseGradeRepository.findAll().stream();
        }

        gradeEntities.forEach(gradeEntity -> {
            GradeDTO dto = new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(), gradeEntity.getStudentId());
            gradeDTOS.add(dto);
        });

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
        if (ValueRange.of(0, 10).isValidValue(gradeDTO.getScore().longValue())) {
            errorDTO.setMessage(ErrorMessage.ERROR_SCORE);
            errorDTO.setDestination("validateGrade(gradeDTO)");
        }
        return errorDTO;
    }
}
