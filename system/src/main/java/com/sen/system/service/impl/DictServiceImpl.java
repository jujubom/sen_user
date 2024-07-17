package com.sen.system.service.impl;

import com.sen.common.constant.UserConstants;
import com.sen.common.core.domain.entity.Dict;
import com.sen.common.core.domain.entity.DictData;
import com.sen.common.exception.ServiceException;
import com.sen.common.utils.DictUtils;
import com.sen.common.utils.StringUtils;
import com.sen.system.mapper.DictDataMapper;
import com.sen.system.mapper.DictMapper;
import com.sen.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-13
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dict 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<Dict> selectDictTypeList(Dict dict) {
        return dictMapper.selectDictTypeList(dict);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<Dict> selectDictTypeAll() {
        return dictMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dict 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataByType(String dict) {
        List<DictData> dictDatas = DictUtils.getDictCache(dict);
        if (StringUtils.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dict);
        if (StringUtils.isNotEmpty(dictDatas)) {
            DictUtils.setDictCache(dict, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public Dict selectDictTypeById(Long dictId) {
        return dictMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dict 字典类型
     * @return 字典类型
     */
    @Override
    public Dict selectDictTypeByType(String dict) {
        return dictMapper.selectDictTypeByType(dict);
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            Dict dict = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dict.getDictType()) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", dict.getDictName()));
            }
            dictMapper.deleteDictTypeById(dictId);
            DictUtils.removeDictCache(dict.getDictType());
        }
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache() {
        List<Dict> dictList = dictMapper.selectDictTypeAll();
        for (Dict dict : dictList) {
            List<DictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas);
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache() {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(Dict dict) {
        int row = dictMapper.insertDictType(dict);
        if (row > 0) {
            DictUtils.setDictCache(dict.getDictType(), null);
        }
        return row;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(Dict dict) {
        Dict oldDict = dictMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = dictMapper.updateDictType(dict);
        if (row > 0) {
            List<DictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(Dict dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        Dict uniqueDict = dictMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(uniqueDict) && uniqueDict.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
