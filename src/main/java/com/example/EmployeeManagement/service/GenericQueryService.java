package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.entity.BaseDomain;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author Odiljon Baxriddinov
 * @since 14/January/2025  13:07
 **/
public interface GenericQueryService<
        ID extends Serializable,
        E extends BaseDomain,
        R extends Response>
        extends GenericService {

    com.example.EmployeeManagement.dto.Response<R> findById(@NotNull ID id);

    com.example.EmployeeManagement.dto.Response<List<R>> findAll();
}
