package resources;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.List;

import entities.ResourceError;

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<ResourceException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(ResourceException e) {
    	ResponseBuilder rb = Response.status(e.getStatusCode()).entity(new ResourceError(e.getErrorCode(), e.getMessage()));
        
        List<MediaType> accepts = headers.getAcceptableMediaTypes();
        if (accepts!=null && accepts.size() > 0) {
            //just pick the first one
            MediaType m = accepts.get(0);
            rb = rb.type(m);
        }
        else {
            //if not specified, use the entity type
            rb = rb.type(headers.getMediaType()); // set the response type to the entity type.
        }
        
        return rb.build();
    }
}