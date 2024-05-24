package lab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class ModelMapperProducer {
    @Produces
    @ApplicationScoped
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }
}
