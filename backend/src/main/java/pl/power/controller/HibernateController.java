package pl.power.controller;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.domain.entity.PowerStation;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping
public class HibernateController {
    //todo add methods to the search tasks and powerStations
    private final EntityManager entityManager;

    public HibernateController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @GetMapping("/testSearchPowerStations")
    public List<PowerStation> test() {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<PowerStation> result = searchSession.search(PowerStation.class)
                .where(f -> f.match().field("name")
                        .matching("Pątnów 1 B6")).fetch(20);
        return result.hits();

    }
}
