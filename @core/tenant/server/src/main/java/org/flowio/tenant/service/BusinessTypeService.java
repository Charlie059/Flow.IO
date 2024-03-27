package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.exception.BusinessTypeNotFoundException;

public interface BusinessTypeService extends IService<BusinessType> {
    /**
     * Get {@link BusinessType} by id, or throw exception if not found
     *
     * @param id The id of {@link BusinessType}
     * @return The {@link BusinessType} with the given id
     * @throws BusinessTypeNotFoundException if {@link BusinessType} is not found
     */
    BusinessType getByIdOrThrow(Integer id) throws BusinessTypeNotFoundException;
}
