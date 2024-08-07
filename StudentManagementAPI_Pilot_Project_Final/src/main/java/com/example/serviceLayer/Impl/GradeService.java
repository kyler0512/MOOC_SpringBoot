package com.example.serviceLayer.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.repositoryLayer.IBaseGradeRepository;
import com.example.repositoryLayer.IGradeRepository;
import com.example.repositoryLayer.Entities.GradeEntity;
import com.example.serviceLayer.IGradeService;
import com.example.serviceLayer.DTOs.GradeDTO;

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
        for (int i = 0; i < gradeEntities.size(); i++) {
            var gradeDTO = new GradeDTO(gradeEntities.get(i).getId(), gradeEntities.get(i).getName(),
                    gradeEntities.get(i).getScore(), gradeEntities.get(i).getStudentId());
            gradeDTOS.add(gradeDTO);
        }
        return gradeDTOS;
    }

    @Override
    public GradeDTO read(int id) {
        var gradeEntity = gradeRepository.read(id);
        return new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(),
                gradeEntity.getStudentId());
    }

    @Override
    public List<GradeDTO> read(int pageNumber, int pageSize, String sortField, String sortType) {
        List<GradeDTO> gradeDTOS = new ArrayList<>();
        Sort.Direction currentSortType = Sort.Direction.ASC;
        if (sortType.equalsIgnoreCase("desc")) {
            currentSortType = Sort.Direction.DESC;
        }
        Pageable paging = PageRequest.of(pageNumber, pageSize,
                Sort.by(currentSortType, sortField.replace("\"", "").trim()));
        Page<GradeEntity> dataEntityList = baseGradeRepository.findAll(paging);
        dataEntityList.stream().forEach(gradeEntity -> {
            GradeDTO gradeDTO = new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(),
                    gradeEntity.getStudentId());
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
                    gradeEntity -> gradeEntity.getName().equals(searchValue));
            case "score" -> gradeEntities = baseGradeRepository.findAll().stream().filter(
                    gradeEntity -> Objects.equals(gradeEntity.getScore(), Double.valueOf(searchValue)));
            case "studentid" -> gradeEntities = baseGradeRepository.findAll().stream().filter(
                    gradeEntity -> Objects.equals(gradeEntity.getStudentId(), Integer.valueOf(searchValue)));
            default -> gradeEntities = baseGradeRepository.findAll().stream();
        }

        gradeEntities.forEach(gradeEntity -> {
            GradeDTO dto = new GradeDTO(gradeEntity.getId(), gradeEntity.getName(), gradeEntity.getScore(),
                    gradeEntity.getStudentId());
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

}
