package org.sid.springwebservice.ws;


import com.amani.soap.GetCompteRequest;
import com.amani.soap.GetCompteResponse;
import com.amani.soap.ListComptesRequest;
import com.amani.soap.ListComptesResponse;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.sid.springwebservice.entities.Compte;
import org.sid.springwebservice.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.List;

@Endpoint
public class BanqueSoapEndpoint {

    @Autowired
    private CompteRepository  compteRepository;
    public static  final String NAME_SPACE="http://www.amani.com/soap";


    @ResponsePayload
    @PayloadRoot(localPart = "GetCompteRequest",namespace = NAME_SPACE)
    public GetCompteResponse consulterCompte(@RequestPayload GetCompteRequest compteRequest) throws DatatypeConfigurationException {
        GetCompteResponse response=new GetCompteResponse();
        Compte compteMetier=compteRepository.findById(compteRequest.getCode()).get();
        com.amani.soap.Compte soapCompte=mapComptesoapfrom(compteMetier);

        response.setCompte(soapCompte);
         return response;
    }


@ResponsePayload
@PayloadRoot(localPart = "listComptesRequest",namespace = NAME_SPACE)
public ListComptesResponse listComptes( @RequestPayload ListComptesRequest request) throws DatatypeConfigurationException {
       ListComptesResponse response=new ListComptesResponse();
        List<Compte> comptesMetier=compteRepository.findAll();
       for (Compte c:comptesMetier){
           response.getComptes().add(mapComptesoapfrom(c));
       }

  return response;
    }






    private static com.amani.soap.Compte mapComptesoapfrom(Compte compteMetier) throws DatatypeConfigurationException {
        com.amani.soap.Compte compte=new com.amani.soap.Compte();
        compte.setCode(compteMetier.getId());
        compte.setSolde(compteMetier.getSolde());
        compte.setState(compteMetier.getEtat());
        GregorianCalendar gregorianCalendar=new GregorianCalendar();
        gregorianCalendar.setTime(compteMetier.getDateCreation());

        compte.setDateCreation(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
     return compte;
    }
}
