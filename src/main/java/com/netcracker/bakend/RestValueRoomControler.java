package com.netcracker.bakend;

import com.netcracker.DAO.entity.ValueRoom;
import com.netcracker.exception.FatalError;
import com.netcracker.services.ValueRoomService;
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
@RequestMapping("rest/valRoom")
public class RestValueRoomControler {

    @Autowired
    ValueRoomService service;

    @GetMapping("/getAll")
    ResponseEntity getListValueRoom(){
        List<ValueRoom> list = null;
        try {
            list = service.findAllValueRoom();
            return  new ResponseEntity<List<ValueRoom>>(list, HttpStatus.OK);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getById/{stars}/{number}")
    ResponseEntity getValueRoomById(@PathVariable("stars") int stars,@PathVariable("number") int number){

        ValueRoom valueRoom = null;
        try {
            valueRoom = service.findValueRoomById(stars,number);
            return new ResponseEntity<ValueRoom>(valueRoom,HttpStatus.OK);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/add")
    ResponseEntity addValueRoom(@RequestBody ValueRoom valueRoom)
    {
        try {
            service.saveValueRoom(valueRoom);
            return new ResponseEntity<String>("Uploaded", HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
        return new ResponseEntity<String>("Such an object already exists", HttpStatus.NOT_FOUND);
        } catch (Exception ex){
           ex.printStackTrace();
        return new ResponseEntity<String>("Not Added",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/del")
    ResponseEntity deleteById(int stars, int number){
        try {
            boolean b  = service.deleteServiceById(stars, number);
            if (!b) return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (FatalError fatalError) {
            fatalError.printStackTrace();
            return  new ResponseEntity<String>(fatalError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
