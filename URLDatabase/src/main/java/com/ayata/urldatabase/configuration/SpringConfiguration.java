package com.ayata.urldatabase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class SpringConfiguration {
    @Autowired
    public void setMapKeyDotReplacement(MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setMapKeyDotReplacement(".");
    }
}
