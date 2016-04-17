package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by craigleclair on 2016-04-16.
 */
public interface FileRepository extends CrudRepository<File, Long> {

    List<File> findByUserHash(int userHash);
    List<File> findAll();
}
