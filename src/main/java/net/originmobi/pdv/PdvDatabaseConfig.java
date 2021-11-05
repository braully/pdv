package net.originmobi.pdv;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class PdvDatabaseConfig implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    DataSource dataSource;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent e) {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setOutOfOrder(true);
        configuration.setValidateOnMigrate(false);
        configuration.setLocationsAsStrings("classpath:db/migration-data");
        Flyway flywaytmp = new Flyway(configuration);
        flywaytmp.migrate();
    }

    // Database
    /*
     * Flyway Database configuration Workaround: Force flyway running post hibernate
     * schema update
     * https://stackoverflow.com/questions/37097876/spring-boot-hibernate-and-flyway
     * -boot-order
     */
    @Bean
    @Primary
    /* Execute before hibernate schema update */
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        // ((ClassicConfiguration) flyway.getConfiguration()).setOutOfOrder(true);
        return new FlywayMigrationInitializer(flyway);
    }

}
