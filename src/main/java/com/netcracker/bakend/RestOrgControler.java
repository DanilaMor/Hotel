package com.netcracker.bakend;

import com.netcracker.DAO.entity.Organization;
import com.netcracker.exception.EntityNotFound;
import com.netcracker.exception.FatalError;
import com.netcracker.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 12345 on 30.01.2018.
 */
@RestController
@RequestMapping("/org")
public class RestOrgControler {

    @Autowired
    OrganizationService service;

    @GetMapping("/getAll")
    ResponseEntity getAll(){
        List<Organization> list = null;
        try {
            list = service.findAllOrg();
            return new ResponseEntity<List<Organization>>(list,HttpStatus.OK);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
            return new ResponseEntity<String>(entityNotFound.getMessage(),HttpStatus.NOT_FOUND);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add")
    ResponseEntity setOrg(Organization org){
     try {
        service.saveOrg(org);
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return new ResponseEntity<String>("Such an object already exists", HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<String>("Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    @PostMapping("/getAll")
    ResponseEntity getById(String id){
        try {
            Organization organization = service.findOrgById(id);
            return new ResponseEntity<Organization>(organization,HttpStatus.OK);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
            return new ResponseEntity<String>(entityNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/del")
    ResponseEntity deleteById(String id){
        try {
            boolean b = false;
            int n = 0;
            n = service.deleteOrgById(id);
            if(n > 0) b = true;
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
