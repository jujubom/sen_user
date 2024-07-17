package com.sen.system.mapper;

import com.sen.common.core.domain.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-13
 */
@Mapper
public interface DictMapper {
    /**
     * 根据条件分页查询字典类型
     *
     * @param dict 字典类型信息
     * @return 字典类型集合信息
     */
    public List<Dict> selectDictTypeList(Dict dict);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    public List<Dict> selectDictTypeAll();

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    public Dict selectDictTypeById(Long dictId);

    /**
     * 根据字典类型查询信息
     *
     * @param dict 字典类型
     * @return 字典类型
     */
    public Dict selectDictTypeByType(String dict);

    /**
     * 通过字典ID删除字典信息
     *
     * @param dictId 字典ID
     * @return 结果
     */
    public int deleteDictTypeById(Long dictId);

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    public int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 新增字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    public int insertDictType(Dict dict);

    /**
     * 修改字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    public int updateDictType(Dict dict);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    public Dict checkDictTypeUnique(String dict);
}
