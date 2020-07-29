package pl.power.config;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import pl.power.domain.entity.PowerStation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Profile("hibernateSearch")
@EnableAsync
@Configuration
public class HibernateSearchConfig {

    private final EntityManager entityManager;

    public HibernateSearchConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Async
    @Transactional
    @Scheduled(fixedRate = 5000)
    public void loadToDatabaseEvery5second() {
        loadClass(PowerStation.class);
    }

    @SafeVarargs
    private <T> void loadClass(Class<T>... nameClass) {
        if (nameClass != null) {
            SearchSession searchSession = Search.session(entityManager);
            MassIndexer indexer = searchSession.massIndexer(nameClass);
            try {
                indexer.startAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }
}
