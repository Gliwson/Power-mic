package pl.power.elastic.es;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.aspect.LogController;
import pl.power.elastic.es.model.Article;
import pl.power.elastic.es.model.Author;
import pl.power.elastic.es.repository.ArticleRepository;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/search/")
public class ElasticController {

    private final ArticleRepository articleRepository;

    public ElasticController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    @LogController
    public Iterable<Article> elsticSearch() {
        return articleRepository.findAll();
    }

    @GetMapping("/name/{name}")
    @LogController
    public List<Article> findAllName(@PathVariable String name) {
        return articleRepository.findByAuthorsName(name);
    }

    @GetMapping("/add")
    @LogController
    public void add() {
        for (int i = 0; i < 1000; i++) {
            Article article = new Article("Spring Data Elasticsearch");
            article.setAuthors(asList(new Author("John Smith" + i), new Author("John Doe" + i)));
            articleRepository.save(article);
        }
    }
}
