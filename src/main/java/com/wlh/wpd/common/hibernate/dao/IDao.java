package com.wlh.wpd.common.hibernate.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.wlh.wpd.common.hibernate.util.QuerySpecification;
import com.wlh.wpd.common.page.Paginator;

public interface IDao<T>
{
    /**
     * 新增实体对象接口
     * @param po 要增加的业务实体对象
     * @return
     * @throws DataAccessException
     */
    void create(T po);

    /**
     * 修改实体对象接口
     * @param po 要修改的业务实体对象
     * @return
     * @throws DataAccessException
     */
    void update(T po) throws DataAccessException;

    /**
     * 删除实体对象接口
     * @param po 要删除的业务实体对象
     * @return
     * @throws DataAccessException
     */
    void delete(T po) throws DataAccessException;

    /**
     * 查询实体对象接口
     * @param id 要查询的业务实体对象的id
     * @return 实体对象
     * @throws DataAccessException
     */
    T getById(Long id) throws DataAccessException;

    /**
     * 查询所有实体对象接口
     * @param
     * @return 实体对象列表
     * @throws DataAccessException
     */
    List<T> findAll() throws DataAccessException;

    /**
     * 查询所有实体对象接口
     * @param
     * @return 实体对象列表
     * @throws DataAccessException
     */
    List<T> findAllOrderById() throws DataAccessException;

    /**
     * 查询所有实体对象接口带分页
     * @param
     * @return 实体对象列表
     * @throws DataAccessException
     * @throws TsServiceException
     */
    List<T> findAllOrderById(Paginator paginator) throws DataAccessException;

    /**
     * 获取实体对象的记录条数
     * @param
     * @return 记录条数
     */
    int getRowCount();

    /**
     * 查询实体对象接口
     * @param name 查询的名称
     * @return 实体对象列表
     * @throws DataAccessException
     */
    List<T> findByName(String name) throws DataAccessException;

    /**
     * 自定义条件查询接口
     * @param querySpec 自定义查询条件
     * @return 实体对象列表
     * @throws DataAccessException
     */
    List<T> queryByCriteria(final QuerySpecification querySpec)
            throws DataAccessException;

    /**
     * 查询实体对象接口 提供分页使用
     * @param name 查询的名称
     * @return 实体对象列表
     * @throws DataAccessException
     */
    List<T> findByName(Paginator paginator, String name)
            throws DataAccessException;
}
