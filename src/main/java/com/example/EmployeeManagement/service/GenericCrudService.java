package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.entity.BaseDomain;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * @author Odiljon Baxriddinov
 * @since 14/January/2025  13:07
 **/
public interface GenericCrudService<
        ID extends Serializable,
        E extends BaseDomain,
        R extends com.example.EmployeeManagement.dto.marker.Response,
        CR extends Request,
        UP extends Request>
        extends GenericQueryService<ID, E, R> {

    Response<R> create(@NotNull CR dto);

    Response<R> update(ID id, @NotNull UP dto);

    Response<R> delete(@NotNull ID id);
}
