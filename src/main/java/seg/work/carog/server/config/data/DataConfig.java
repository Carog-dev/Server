package seg.work.carog.server.config.data;

import jakarta.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "seg.work.carog.server.*.repository")
@MapperScan(basePackages = "seg.work.carog.server.*.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataConfig {

    private final HibernateProperties hibernateProperties;
    private final JpaProperties jpaProperties;

    @Bean("writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("routingDataSource")
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource, @Qualifier("readDataSource") DataSource readDataSource) {
        List<DataSource> slaves = Collections.singletonList(readDataSource);
        return new RoutingDataSource(writeDataSource, slaves);
    }

    @Primary
    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan(
                "seg.work.carog.server.*.entity"
        );
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()));
        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return localContainerEntityManagerFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("seg.work.carog.server.*.dto");
        Objects.requireNonNull(sqlSessionFactoryBean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
