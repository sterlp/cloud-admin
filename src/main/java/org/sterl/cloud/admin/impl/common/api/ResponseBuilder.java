package org.sterl.cloud.admin.impl.common.api;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.sterl.cloud.admin.api.model.HasId;
import org.sterl.cloud.admin.impl.common.converter.Converter;


/**
 * Builder class to build a response using the converters.
 * 
 * @param <BE> model class
 * @param <DO> the result class
 */
public final class ResponseBuilder<BE, DO> {

    private HttpStatus status;
    private final Converter<BE, DO> converter;
    private BE model;
    private Collection<BE> modelList;

    public static ResponseEntity<?> created(HasId<?> model) {
        final Object id = model.getStrongId().getValue();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).header("id", id.toString()).build();
    }
    
    public static ResponseEntity<?> okWithLocation(HasId<?> model) {
        final Object id = model.getStrongId().getValue();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.ok().location(location).header("id", id.toString()).build();
    }

    private ResponseBuilder(final Converter<BE, DO> converter) {
        this.converter = converter;
        this.status = HttpStatus.OK;
    }

    /**
     * Creates a new response builder which includes a converter.
     * 
     * @param <B>
     *            input BE
     * @param <D>
     *            output DO
     * @param converter
     *            the converter from BE to DO
     * @return the ResponseBuilder
     */
    public static <B, D> ResponseBuilder<B, D> builder(final Converter<B, D> converter) {
        return new ResponseBuilder<>(converter);
    }

    /**
     * Adds the entity as result and applies status 200 if not null otherwise 404
     * not found.
     * 
     * @param result
     *            body
     * @return this to chain
     */
    public ResponseBuilder<BE, DO> withResult(final BE result) {
        this.model = result;
        if (model == null) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return this;
    }

    /**
     * Set a collect of result objects, will set the model to null!
     * 
     * Note that in this case null or empty list will be an 200 okay, just nothing
     * found.
     * 
     * @param result
     * @return this to chain
     */
    public ResponseBuilder<BE, DO> withResult(final Collection<BE> result) {
        this.modelList = result;
        this.model = null;
        return this;
    }

    /**
     * Applies the given status no matter what.
     * 
     * @param status the new status
     * @return this to chain
     */
    public ResponseBuilder<BE, DO> status(final HttpStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Builds the response.
     * 
     * @return the Response
     */
    public ResponseEntity<?> build() {
        ResponseEntity<?> result;

        if (model != null) {
            result = new ResponseEntity<DO>(converter.convert(model), status);
        } else if (modelList != null) {
            result = new ResponseEntity<List<DO>>(converter.convert(modelList), status);
        } else {
            result = ResponseEntity.ok().build();
        }

        return result;
    }
}
