package com.netcracker.bakend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import com.holonplatform.jaxrs.swagger.annotations.ApiDefinition;
import org.springframework.stereotype.Component;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 02.02.2018.
 */
//@ApiDefinition(docsPath = "/api/docs", title = "Example API", version = "v1", prettyPrint = true)

@RestController
@RequestMapping("/test")
public class RestTest {

    @GetMapping
    public Response ping() {
        return Response.ok("pong").build();
    }

}