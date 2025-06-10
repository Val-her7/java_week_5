package dev.val.Hospital_Registration_API.mapper;

import org.springframework.stereotype.Component;
import dev.val.Hospital_Registration_API.model.Visit;
import dev.val.Hospital_Registration_API.dto.VisitDTO;
import java.util.function.Function;

@Component
public class VisitDTOMapper implements Function<Visit, VisitDTO>{

    @Override
    public VisitDTO apply(Visit visit) {

        return new VisitDTO(
            visit.getId(),
            visit.getFirstName() + " " + visit.getLastName(),
            visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName(),
            visit.getTimestamp().toLocalDate());
    }
}