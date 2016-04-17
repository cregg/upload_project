package hello;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by craigleclair on 2016-04-16.
 */
public interface SettingRepository extends CrudRepository<Setting, Long> {

    Setting getByKey(String key);
}
