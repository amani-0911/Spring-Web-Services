package org.sid.springwebservice.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class springWebServiceConfig {
    //deployer le message servlet

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> servletRegistrationBean(ApplicationContext context){
        MessageDispatcherServlet servlet=new MessageDispatcherServlet();
        //specifier le context de l'application
        servlet.setApplicationContext(context);
          //pur faire la generation de wsdl
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet,"/ws/*");
    }


    //methode qui return le schema
    @Bean
    public XsdSchema xsdSchema(){
        SimpleXsdSchema simpleXsdSchema=new SimpleXsdSchema();
        simpleXsdSchema.setXsd(new ClassPathResource("contract.xsd"));
        return simpleXsdSchema;
    }

    //method qui gener wsdl nom de bean c'est url de wsdl

    @Bean("banque")
    public DefaultWsdl11Definition wsdl11Definition(){
        DefaultWsdl11Definition definition =new DefaultWsdl11Definition();
        definition.setPortTypeName("BanqueService");
        definition.setSchema(xsdSchema());
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(BanqueSoapEndpoint.NAME_SPACE);
     return definition;
    }
}
