package com.example.introduction_to_spring_web_mvc;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;

@Configuration
public class Config {

    @Bean
    @SessionScope
    Name createNameStore(ArrayList<Name> names) {
        return new Name(names);
    }

    @Bean
    ArrayList<Name> createNamesDB() {
        return new ArrayList<>();
    }
}


class Name implements InitializingBean, DisposableBean {

    String name;
    ArrayList<Name> names;

    Name(ArrayList<Name> names) {
        name = null;
        this.names = names;
        names.add(this);
    }
    public void setName(String name) {
        LoggerFactory.getLogger("RinzeLogger").info("De naam is gewijzigd van %s naar %s".formatted(this.name, name));
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        var l = LoggerFactory.getLogger("RinzeLogger");
        l.info("We hebben een nieuwe naam!");
    }

    @Override
    public void destroy() throws Exception {
        var l = LoggerFactory.getLogger("RinzeLogger");
        l.info("We eindigen de sessie!");
        names.remove(this);
    }
}