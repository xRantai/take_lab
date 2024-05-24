package lab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lab.data.ComplaintRepository;
import lab.dto.ComplaintDTO;
import lab.entities.Complaint;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@ApplicationScoped
public class ComplaintService {
    @Inject
    private ComplaintRepository repository;
    @Inject
    private ModelMapperProducer modelMapperProducer;

    @Transactional
    public void create(ComplaintDTO dto) {
        ModelMapper mapper = modelMapperProducer.createModelMapper();
        repository.create(mapper.map(dto, Complaint.class));
    }

    @Transactional
    public void edit(ComplaintDTO dto) {
        ModelMapper mapper = modelMapperProducer.createModelMapper();
        Complaint complaint = mapper.map(dto, Complaint.class);
        repository.edit(complaint);
    }

    @Transactional
    public ComplaintDTO find(Long id) {
        ModelMapper mapper = modelMapperProducer.createModelMapper();
        Complaint complaint = repository.find(id);
        return mapper.map(complaint, ComplaintDTO.class);
    }

    @Transactional
    public void remove(ComplaintDTO dto) {
        ModelMapper mapper = modelMapperProducer.createModelMapper();
        Complaint complaint = mapper.map(dto, Complaint.class);
        repository.remove(complaint);
    }

    @Transactional
    public List<ComplaintDTO> findAll(String status) {
        ModelMapper mapper = modelMapperProducer.createModelMapper();
        List<Complaint> entityList = repository.findAll(status);
        Type listType =
                new TypeToken<List<ComplaintDTO>>() {
                }.getType();
        List<ComplaintDTO> dtoList =
                mapper.map(entityList, listType);
        return dtoList;
    }
}
