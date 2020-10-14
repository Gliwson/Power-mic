package pl.power.elasticRepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pl.power.domain.entity.TaskDocument;

import java.util.List;

public interface TaskDocumentRepository extends ElasticsearchRepository<TaskDocument, Long> {

//    Page<TaskDocument> findByAuthorsName(String name, Pageable pageable);

    List<TaskDocument> findByNamePowerStation(String name);

    List<TaskDocument> findByNamePowerStationLike(String name);



//    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
//    Page<TaskDocument> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
//
//    @Query("{\"bool\": {\"must\": {\"match_all\": {}}, \"filter\": {\"term\": {\"tags\": \"?0\" }}}}")
//    Page<TaskDocument> findByFilteredTagQuery(String tag, Pageable pageable);
//
//    @Query("{\"bool\": {\"must\": {\"match\": {\"authors.name\": \"?0\"}}, \"filter\": {\"term\": {\"tags\": \"?1\" }}}}")
//    Page<TaskDocument> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);
}
