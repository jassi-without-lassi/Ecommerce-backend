package com.example.ecomerce.config;

import com.example.ecomerce.entity.Product;
import com.example.ecomerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] theUnsupportedActions = {HttpMethod.DELETE};

        // disable HTTP methods for Product : put, post and DELETE
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata,httpMethods ) -> httpMethods.disable(theUnsupportedActions));


        // disable HTTP methods for ProductCategory : put, post and DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata,httpMethods ) -> httpMethods.disable(theUnsupportedActions));






//        call an internal helper method.
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config){

        //        expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entites = entityManager.getMetamodel().getEntities();

       //- create an array of the entity types
       List<Class> entityClasses= new ArrayList< >();

       // - get the entity types for the entities
       for (EntityType tempEntityType: entites){
           entityClasses.add(tempEntityType.getJavaType());

       }
//        expose the entity ids for the array of entity/domain types
       Class[] domainTypes = entityClasses.toArray(new Class[0]);
       config.exposeIdsFor(domainTypes);
    }
}
